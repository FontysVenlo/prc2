-- Good practices
-- Don't use user 'postgres' as your regular user account, because postgres is 
-- the SUPERUSER. Create your own admin user like this (from the postgres user login):
-- CREATE ROLE <your username> WITH LOGIN PASSWORD '<your password>' SUPERUSER;
-- Now you have your own admin account. You could even not give this account SUPERUSER
-- priviliges, but only CREATEDB and CREATEROLE:
-- CREATE ROLE <your username> WITH LOGIN PASSWORD '<your password>' CREATEDB CREATEROLE;
-- Create a low-priviliged user for your application:
-- CREATE ROLE <your application user> WITH LOGIN PASSWORD '<your password>';
-- Afterwards, create a database for your application:
-- CREATE DATABASE <dbname> owner <owning user>;
-- Afterwards login with the application user (psql -d <dbname> -U <username>)
-- Now create a schema (like namespace or package) in your database:
-- CREATE SCHEMA <schema name>;
--
-- Postgress is very well documented online, so read the documentation!
-- https://www.postgresql.org/docs/current/


-- First the necessary DDL code:

CREATE TABLE customers (
id SERIAL PRIMARY KEY,
firstname text,
lastname text,
dob date
);

create view customersview as ( select * from customers);

insert into customers (firstname, lastname, dob) values ('Richard', 'van den Ham', '1990-02-01');
insert into customers (firstname, lastname, dob) values ('Pieter', 'van den Hombergh', '1980-01-01');
insert into customers (firstname, lastname, dob) values ('Martijn', 'Bonajo', '01-01-1991');



-- Some example code used in the lesson:

start transaction;
delete from customers;
delete from customers where firstname = 'Lisa';
rollback;

select * from customersview;

select * from customers;

drop table customers;

show search_path;

set search_path to ais, public;

select * from pg_tables;
select * from information_schema.columns where table_name = 'customers';

-- Resulting query of the SQL-injection example
SELECT * FROM CUSTOMERS WHERE firstname = '' or firstname like '%%';