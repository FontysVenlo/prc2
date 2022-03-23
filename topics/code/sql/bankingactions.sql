
-- deadlock free bank transfer
-- this is partial code to serve as an example
-- to delegate responsibilities to another service layer,
-- the database in this case.
-- @author Pieter van den Hombergh
-- @initialversion 2016-04-12
-- @lastchange     2018-09-05

begin work;

-- declare a rule do something else than just a simple update.

create or replace rule banking_action_update  as
    ON UPDATE to customer_account_allowed_state_event
    DO instead (
        update account set balance= new.balance where accountid=new.accountid;
        );

-- check if a transaction is allowed
create or replace function checkbankingrules(donor_event account_event,
        transaction_role text) returns void
language plpgsql
SECURITY DEFINER
as $checkbankingrules$
declare
    fromstaterecord record;
begin
    with dasm as (
        select *,donor_event.accountid,donor_event.balance
        from accountstatemachine asm
        where startstate=donor_event.astate and asm.event=donor_event.event
    ), dcsm as (
        select customerid,cstate,event,endstate
        from customer cust
        left join customerstatemachine csm on csm.startstate=cust.cstate
        where customerid=donor_event.customerid
    )
    select acc.accountid, c.customerid,
        acc.astate,
        dasm.event as aevent,
        dasm.endstate as aendstate,
        c.cstate,
        dcsm.event as cevent,
        dcsm.endstate as cendstate,
        acc.balance
    from account acc join customer c using(customerid)
    left join dasm on acc.astate=dasm.startstate and acc.accountid=dasm.accountid
    left join dcsm on dcsm.customerid=c.customerid
    where acc.accountid = donor_event.accountid and c.customerid=dcsm.customerid and dcsm.event=donor_event.event
    into fromstaterecord;

-- raise notice 'staterecord (a,c,as,ae,aes,cs,ce,ces,bal)= %',fromstaterecord;

    if fromstaterecord.aendstate isnull or fromstaterecord.cendstate isnull then
        raise exception 'not obeying banking rules for event "%", role "%" customer (%) in state "%", account (%) in state "%"',
        donor_event.event,transaction_role,fromstaterecord.customerid,fromstaterecord.cstate, fromstaterecord.accountid,donor_event.astate
        using errcode='data_exception';
    end if;

end; $checkbankingrules$;

-- Transfer money between account while avoiding deadlocks.
-- @froma account that provides the money
-- @toa   account that accepts the money
-- @amount sic
-- @reason of the transaction. Will be shown in the bank statement.
CREATE OR REPLACE FUNCTION transferlockfree(froma integer, toa integer, amount  numeric, reason text) RETURNS bigint

LANGUAGE plpgsql
SECURITY DEFINER
AS $transfer$
DECLARE
    fromstaterecord record;--customer_account_allowed_state_event%rowtype;
    tostaterecord   record;-- customer_account_allowed_state_event%rowtype;
    donor_event    account_event;
    receiver_event account_event;
    transid bigint;
BEGIN
    -- check validity of amount
    if amount <= 0 then
        raise exception '% is an illegal amount for a transfer',amount;
    end if;
    -- check conditions for accounts from and to
    -- get the relevant info in a fixed order, set by account_id
    -- starting with lowest number avoids deadlocks.
    if fromA < toA then
        -- from first
        donor_event := getaccount_event(fromA, 'withdraw'::bankevent);
        receiver_event := getaccount_event(toA, 'deposit'::bankevent);
    else
        -- to first
        receiver_event := getaccount_event(toA, 'deposit'::bankevent);
        donor_event := getaccount_event(fromA, 'withdraw'::bankevent);
    end if;

    if donor_event.balance + donor_event.maxdebit - amount < 0 then
        raise EXCEPTION 'from account #% payment rule violation balance = %,maxdebit = %, amount = %',
           froma, donor_event.balance,event.maxdebit , amount;
    end if;

    -- check donor account and customer
    perform checkbankingrules(donor_event,'donor');
    -- check receiver account and customer
    perform checkbankingrules(receiver_event,'receiver');

    -- checks if withdrawal and deposit is allowed is now complete.
    select nextval('transactions_trans_id_seq'::regclass) into transid;

    insert into transactions (trans_id,amount,receiver,donor,description,ts)
        values(transid,amount,toa,froma,reason,now()::timestamp);

    update customer_account_allowed_state_event
        set balance = balance-amount
        where accountid = froma and cevent='withdraw' and aevent='withdraw';

    update customer_account_allowed_state_event
        set balance = balance+amount
        where accountid = toa and cevent='deposit' and aevent='deposit';

    return transid;
END; $transfer$;

-- do the actual transfer
-- This method delegates the work to the method above
-- @froma account that provides the money
-- @toa   account that accepts the money
-- @amount sic
-- @reason of the transaction. Will be shown in the bank statement.
-- @return a transactionid, identifying this transaction
CREATE OR REPLACE FUNCTION transfer(froma integer, toa integer, amount  numeric, reason text, out transid bigint) RETURNS bigint
LANGUAGE plpgsql
SECURITY DEFINER

AS $withdraw$
BEGIN
    transid= transferlockfree(froma, toa,  amount, reason);
END; $withdraw$;

-- withdraw is the get cash at the bank
-- the amount is transferred from the froma account to the bank's account in return for cash.
-- @froma account that provides the money
-- @amount sic
-- @reason of the transaction. Will be shown in the bank statement.
-- @return a transactionid, identifying this transaction

CREATE OR REPLACE FUNCTION withdraw(in froma integer, in amount  numeric, in reason text, out transid bigint ) RETURNS bigint
LANGUAGE plpgsql
SECURITY DEFINER

AS $withdraw$
declare
    bank_account integer;
    transid bigint;
begin
    bank_account=99999999;
    transid=transferlockfree(froma,bank_account,amount,reason);
end; $withdraw$;

-- deposit is put bank in the account. You give money to the bank and the bank transfers the money from it's account to yours.
-- the amount is transferred from the bank's to to toa account for the acceptance of cash.
-- @froma account that provides the money
-- @amount sic
-- @reason of the transaction. Will be shown in the bank statement.
-- @return a transactionid, identifying this transaction
CREATE OR REPLACE FUNCTION deposit(in toa integer, in amount  numeric, in reason text, out transid bigint ) RETURNS bigint
LANGUAGE plpgsql
SECURITY DEFINER

AS $withdraw$
declare
   bank_account integer;
   transid bigint;
begin
    bank_account=99999999;
    transid = transferflockfree(bank_account,toa,amount,reason);
end; $withdraw$;

commit;
