begin work;
--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';

-- to enable functions like crosstab
CREATE EXTENSION IF NOT EXISTS tablefunc;

--
-- Name: admin_vpres; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE admin_vpres (
    admin_id integer NOT NULL,
    vice_pres_name character varying(20) NOT NULL
);
COMMENT ON TABLE admin_vpres IS 'President and vice president';

ALTER TABLE ONLY admin_vpres
    ADD CONSTRAINT prim_key_admvp PRIMARY KEY (admin_id, vice_pres_name);

--
-- Name: administration; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE administration (
    id SERIAL,
	admin_nr integer NOT NULL,
    pres_id integer NOT NULL,
    year_inaugurated integer,
    CONSTRAINT check_year_inaug CHECK (((year_inaugurated >= 1600) AND (year_inaugurated <= 2100)))
);

ALTER TABLE ONLY administration
    ADD CONSTRAINT prim_key_adm PRIMARY KEY (id);

COMMENT ON TABLE administration IS 'Name of president, administration number and year of inauguration';

CREATE UNIQUE INDEX iAdminNrpresid ON administration(admin_nr,pres_id);


--
-- Name: election; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--
CREATE TABLE election (
    election_year integer NOT NULL,
    candidate character varying(20) NOT NULL,
    votes integer,
    winner_loser_indic character(1),
    CONSTRAINT check_election_year CHECK (((election_year >= 1600) AND (election_year <= 2100))),
    CONSTRAINT check_votes CHECK ((votes > 0)),
    CONSTRAINT check_winner_loser_indic CHECK (((winner_loser_indic = 'W'::bpchar) OR (winner_loser_indic = 'L'::bpchar)))
);

COMMENT ON TABLE election IS 'Election year, vote count (electoral vote, by delegate), won or lost';

ALTER TABLE ONLY election
    ADD CONSTRAINT prim_key_elec PRIMARY KEY (election_year, candidate);

--
-- Name: pres_hobby; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pres_hobby (
    pres_id integer NOT NULL,
    hobby character varying(20) NOT NULL
);

COMMENT ON TABLE pres_hobby IS 'Hobby of the president';

ALTER TABLE ONLY pres_hobby
    ADD CONSTRAINT prim_key_hobby PRIMARY KEY (pres_id, hobby);



--
-- Name: pres_marriage; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--
CREATE TABLE pres_marriage (
    pres_id integer NOT NULL,
    spouse_name character varying(20) NOT NULL,
    spouse_age smallint,
    nr_children smallint,
    marriage_year integer,
    CONSTRAINT check_marriage_year CHECK (((marriage_year >= 1600) AND (marriage_year <= 2100))),
    CONSTRAINT check_spouse_age CHECK ((spouse_age > 15))
);

COMMENT ON TABLE pres_marriage IS 'Marriage, spouce name, year of marriage, age of man and wife, nr of children.';

ALTER TABLE ONLY pres_marriage
    ADD CONSTRAINT prim_key_presmar PRIMARY KEY (pres_id, spouse_name);


--
-- Name: president; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE president (
    id SERIAL,
	name character varying(20) NOT NULL ,
    birth_year integer,
    years_served smallint,
    death_age smallint,
    party character varying(10),
    state_id_born integer,
    CONSTRAINT check_birth_year CHECK (((birth_year >= 1600) AND (birth_year <= 2100))),
    CONSTRAINT check_death_age CHECK (((death_age > 20) OR (death_age IS NULL)))
);
COMMENT ON TABLE president IS 'President name, birth year etc.';

ALTER TABLE ONLY president
    ADD CONSTRAINT prim_key_pres PRIMARY KEY (id);



--
-- Name: state; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE state (
    id SERIAL,
	name character varying(15) NOT NULL ,
    admin_id integer,
    year_entered integer,
    CONSTRAINT check_year_entered CHECK (((year_entered >= 1600) AND (year_entered <= 2100)))
);

COMMENT ON TABLE state IS 'States, added in year and under which president.';

ALTER TABLE ONLY state
    ADD CONSTRAINT prim_key_state PRIMARY KEY (id);


--
-- INDEX 
--
CREATE INDEX iadmin ON administration USING btree (pres_id, year_inaugurated);
CREATE INDEX ipres ON president USING btree (party);


--
-- FOREIGN KEYS
--

ALTER TABLE ONLY admin_vpres
    ADD CONSTRAINT admin_vpres_fk1 FOREIGN KEY (admin_id) REFERENCES administration(id);	

ALTER TABLE ONLY administration
    ADD CONSTRAINT administration_fk1 FOREIGN KEY (pres_id) REFERENCES president(id);

ALTER TABLE ONLY pres_hobby
    ADD CONSTRAINT pres_hobby_fk1 FOREIGN KEY (pres_id) REFERENCES president(id);

ALTER TABLE ONLY pres_marriage
    ADD CONSTRAINT pres_marriage_fk1 FOREIGN KEY (pres_id) REFERENCES president(id);

ALTER TABLE ONLY president
    ADD CONSTRAINT president_fk1 FOREIGN KEY (state_id_born) REFERENCES state(id);

ALTER TABLE ONLY state
    ADD CONSTRAINT state_fk1 FOREIGN KEY (admin_id) REFERENCES administration(id);

	
	CREATE VIEW recent_presidents AS 
	SELECT p.id, p.name, p.birth_year, p.years_served, p.death_age, p.party, s.name AS state_name 
		FROM president p
		INNER JOIN state s 
		ON p.state_id_born = s.id 
		WHERE p.birth_year > 1880;
	
	COMMENT ON VIEW recent_presidents IS 'Presidents born since 1880.';


--
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;

commit;
