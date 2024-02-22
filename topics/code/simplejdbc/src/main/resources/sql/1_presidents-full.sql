--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2 (Ubuntu 12.2-4)
-- Dumped by pg_dump version 12.2 (Ubuntu 12.2-4)
\c presidents
begin work;
set role exam;
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;



SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_vpres; Type: TABLE; Schema: public; Owner: exam
--

CREATE TABLE public.admin_vpres (
    admin_id integer NOT NULL,
    vice_pres_name character varying(20) NOT NULL
);


ALTER TABLE public.admin_vpres OWNER TO exam;

--
-- Name: TABLE admin_vpres; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON TABLE public.admin_vpres IS 'President and vice president';


--
-- Name: administration; Type: TABLE; Schema: public; Owner: exam
--

CREATE TABLE public.administration (
    id integer NOT NULL,
    admin_nr integer NOT NULL,
    pres_id integer NOT NULL,
    year_inaugurated integer,
    CONSTRAINT check_year_inaug CHECK (((year_inaugurated >= 1600) AND (year_inaugurated <= 2100)))
);


ALTER TABLE public.administration OWNER TO exam;

--
-- Name: TABLE administration; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON TABLE public.administration IS 'Name of president, administration number and year of inauguration';


--
-- Name: administration_id_seq; Type: SEQUENCE; Schema: public; Owner: exam
--

CREATE SEQUENCE public.administration_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.administration_id_seq OWNER TO exam;

--
-- Name: administration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: exam
--

ALTER SEQUENCE public.administration_id_seq OWNED BY public.administration.id;


--
-- Name: election; Type: TABLE; Schema: public; Owner: exam
--

CREATE TABLE public.election (
    election_year integer NOT NULL,
    candidate character varying(20) NOT NULL,
    votes integer,
    winner_loser_indic character(1),
    CONSTRAINT check_election_year CHECK (((election_year >= 1600) AND (election_year <= 2100))),
    CONSTRAINT check_votes CHECK ((votes > 0)),
    CONSTRAINT check_winner_loser_indic CHECK (((winner_loser_indic = 'W'::bpchar) OR (winner_loser_indic = 'L'::bpchar)))
);


ALTER TABLE public.election OWNER TO exam;

--
-- Name: TABLE election; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON TABLE public.election IS 'Election year, vote count (electoral vote, by delegate), won or lost';


--
-- Name: pres_hobby; Type: TABLE; Schema: public; Owner: exam
--

CREATE TABLE public.pres_hobby (
    pres_id integer NOT NULL,
    hobby character varying(20) NOT NULL
);


ALTER TABLE public.pres_hobby OWNER TO exam;

--
-- Name: TABLE pres_hobby; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON TABLE public.pres_hobby IS 'Hobby of the president';


--
-- Name: pres_marriage; Type: TABLE; Schema: public; Owner: exam
--

CREATE TABLE public.pres_marriage (
    pres_id integer NOT NULL,
    spouse_name character varying(20) NOT NULL,
    spouse_age smallint,
    nr_children smallint,
    marriage_year integer,
    CONSTRAINT check_marriage_year CHECK (((marriage_year >= 1600) AND (marriage_year <= 2100))),
    CONSTRAINT check_spouse_age CHECK ((spouse_age > 15))
);


ALTER TABLE public.pres_marriage OWNER TO exam;

--
-- Name: TABLE pres_marriage; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON TABLE public.pres_marriage IS 'Marriage, spouce name, year of marriage, age of man and wife, nr of children.';


--
-- Name: president; Type: TABLE; Schema: public; Owner: exam
--

CREATE TABLE public.president (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    birth_year integer,
    years_served smallint,
    death_age smallint,
    party character varying(10),
    state_id_born integer,
    CONSTRAINT check_birth_year CHECK (((birth_year >= 1600) AND (birth_year <= 2100))),
    CONSTRAINT check_death_age CHECK (((death_age > 20) OR (death_age IS NULL)))
);


ALTER TABLE public.president OWNER TO exam;

--
-- Name: TABLE president; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON TABLE public.president IS 'President name, birth year etc.';


--
-- Name: president_id_seq; Type: SEQUENCE; Schema: public; Owner: exam
--

CREATE SEQUENCE public.president_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.president_id_seq OWNER TO exam;

--
-- Name: president_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: exam
--

ALTER SEQUENCE public.president_id_seq OWNED BY public.president.id;


--
-- Name: state; Type: TABLE; Schema: public; Owner: exam
--

CREATE TABLE public.state (
    id integer NOT NULL,
    name character varying(15) NOT NULL,
    admin_id integer,
    year_entered integer,
    CONSTRAINT check_year_entered CHECK (((year_entered >= 1600) AND (year_entered <= 2100)))
);


ALTER TABLE public.state OWNER TO exam;

--
-- Name: TABLE state; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON TABLE public.state IS 'States, added in year and under which president.';


--
-- Name: recent_presidents; Type: VIEW; Schema: public; Owner: exam
--

CREATE VIEW public.recent_presidents AS
 SELECT p.id,
    p.name,
    p.birth_year,
    p.years_served,
    p.death_age,
    p.party,
    s.name AS state_name
   FROM (public.president p
     JOIN public.state s ON ((p.state_id_born = s.id)))
  WHERE (p.birth_year > 1880);


ALTER TABLE public.recent_presidents OWNER TO exam;

--
-- Name: VIEW recent_presidents; Type: COMMENT; Schema: public; Owner: exam
--

COMMENT ON VIEW public.recent_presidents IS 'Presidents born since 1880.';


--
-- Name: state_id_seq; Type: SEQUENCE; Schema: public; Owner: exam
--

CREATE SEQUENCE public.state_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.state_id_seq OWNER TO exam;

--
-- Name: state_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: exam
--

ALTER SEQUENCE public.state_id_seq OWNED BY public.state.id;


--
-- Name: administration id; Type: DEFAULT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.administration ALTER COLUMN id SET DEFAULT nextval('public.administration_id_seq'::regclass);


--
-- Name: president id; Type: DEFAULT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.president ALTER COLUMN id SET DEFAULT nextval('public.president_id_seq'::regclass);


--
-- Name: state id; Type: DEFAULT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.state ALTER COLUMN id SET DEFAULT nextval('public.state_id_seq'::regclass);


--
-- Data for Name: admin_vpres; Type: TABLE DATA; Schema: public; Owner: exam
--

INSERT INTO public.admin_vpres VALUES (1, 'ADAMS J');
INSERT INTO public.admin_vpres VALUES (2, 'ADAMS J');
INSERT INTO public.admin_vpres VALUES (3, 'JEFFERSON T');
INSERT INTO public.admin_vpres VALUES (4, 'BURR A');
INSERT INTO public.admin_vpres VALUES (5, 'CLINTON G');
INSERT INTO public.admin_vpres VALUES (6, 'CLINTON G');
INSERT INTO public.admin_vpres VALUES (7, 'GERRRY E');
INSERT INTO public.admin_vpres VALUES (8, 'TOMPKINS D');
INSERT INTO public.admin_vpres VALUES (9, 'TOMPKINS D');
INSERT INTO public.admin_vpres VALUES (10, 'CALHOUN J');
INSERT INTO public.admin_vpres VALUES (11, 'CALHOUN J');
INSERT INTO public.admin_vpres VALUES (12, 'VAN BUREN M');
INSERT INTO public.admin_vpres VALUES (13, 'JOHNSON R M');
INSERT INTO public.admin_vpres VALUES (14, 'TYLER J');
INSERT INTO public.admin_vpres VALUES (16, 'DALLAS J M');
INSERT INTO public.admin_vpres VALUES (17, 'FILLMORE M');
INSERT INTO public.admin_vpres VALUES (19, 'DE VANE KING W R');
INSERT INTO public.admin_vpres VALUES (20, 'BRECKINRIDGE J C');
INSERT INTO public.admin_vpres VALUES (21, 'HAMLIN H');
INSERT INTO public.admin_vpres VALUES (22, 'JOHNSON A');
INSERT INTO public.admin_vpres VALUES (24, 'COLFAX S');
INSERT INTO public.admin_vpres VALUES (25, 'WILSON H');
INSERT INTO public.admin_vpres VALUES (26, 'WHEELER W');
INSERT INTO public.admin_vpres VALUES (27, 'ARTHUR C A');
INSERT INTO public.admin_vpres VALUES (29, 'HENDRICKS T A');
INSERT INTO public.admin_vpres VALUES (30, 'MORTON L P');
INSERT INTO public.admin_vpres VALUES (31, 'STEVENSON A E');
INSERT INTO public.admin_vpres VALUES (32, 'HOBART G A');
INSERT INTO public.admin_vpres VALUES (33, 'ROOSEVELT T');
INSERT INTO public.admin_vpres VALUES (35, 'FAIRBANKS C W');
INSERT INTO public.admin_vpres VALUES (36, 'SHERMAN J S');
INSERT INTO public.admin_vpres VALUES (37, 'MARSHALL T R');
INSERT INTO public.admin_vpres VALUES (38, 'MARSHALL T R');
INSERT INTO public.admin_vpres VALUES (39, 'COOLIDGE C');
INSERT INTO public.admin_vpres VALUES (41, 'DAWES C G');
INSERT INTO public.admin_vpres VALUES (42, 'CURTIS C');
INSERT INTO public.admin_vpres VALUES (43, 'GARNER J N');
INSERT INTO public.admin_vpres VALUES (44, 'GARNER J N');
INSERT INTO public.admin_vpres VALUES (45, 'WALLACE H A');
INSERT INTO public.admin_vpres VALUES (46, 'TRUMAN H S');
INSERT INTO public.admin_vpres VALUES (48, 'BARKLEY A W');
INSERT INTO public.admin_vpres VALUES (49, 'NIXON R M');
INSERT INTO public.admin_vpres VALUES (50, 'NIXON R M');
INSERT INTO public.admin_vpres VALUES (51, 'JOHNSON L B');
INSERT INTO public.admin_vpres VALUES (53, 'HUMPHREY H H');
INSERT INTO public.admin_vpres VALUES (54, 'AGNEW S T');
INSERT INTO public.admin_vpres VALUES (55, 'AGNEW S T');
INSERT INTO public.admin_vpres VALUES (55, 'FORD G R');
INSERT INTO public.admin_vpres VALUES (56, 'ROCKEFELLER N A');
INSERT INTO public.admin_vpres VALUES (57, 'MONDALE W F');
INSERT INTO public.admin_vpres VALUES (58, 'BUSH G H W');
INSERT INTO public.admin_vpres VALUES (59, 'BUSH G H W');
INSERT INTO public.admin_vpres VALUES (60, 'QUAYLE D');
INSERT INTO public.admin_vpres VALUES (61, 'GORE A');
INSERT INTO public.admin_vpres VALUES (62, 'GORE A');
INSERT INTO public.admin_vpres VALUES (63, 'CHENEY D');
INSERT INTO public.admin_vpres VALUES (64, 'CHENEY D');
INSERT INTO public.admin_vpres VALUES (65, 'BIDEN J');
INSERT INTO public.admin_vpres VALUES (66, 'BIDEN J');
INSERT INTO public.admin_vpres VALUES (67, 'PENCE M R');


--
-- Data for Name: administration; Type: TABLE DATA; Schema: public; Owner: exam
--

INSERT INTO public.administration VALUES (0, 0, 1, 1789);
INSERT INTO public.administration VALUES (1, 1, 1, 1789);
INSERT INTO public.administration VALUES (2, 2, 1, 1793);
INSERT INTO public.administration VALUES (3, 3, 2, 1797);
INSERT INTO public.administration VALUES (4, 4, 3, 1801);
INSERT INTO public.administration VALUES (5, 5, 3, 1805);
INSERT INTO public.administration VALUES (6, 6, 4, 1809);
INSERT INTO public.administration VALUES (7, 7, 4, 1813);
INSERT INTO public.administration VALUES (8, 8, 5, 1817);
INSERT INTO public.administration VALUES (9, 9, 5, 1821);
INSERT INTO public.administration VALUES (10, 10, 6, 1825);
INSERT INTO public.administration VALUES (11, 11, 7, 1829);
INSERT INTO public.administration VALUES (12, 12, 7, 1833);
INSERT INTO public.administration VALUES (13, 13, 8, 1837);
INSERT INTO public.administration VALUES (14, 14, 9, 1841);
INSERT INTO public.administration VALUES (15, 14, 10, 1841);
INSERT INTO public.administration VALUES (16, 15, 11, 1845);
INSERT INTO public.administration VALUES (17, 16, 12, 1849);
INSERT INTO public.administration VALUES (18, 16, 13, 1850);
INSERT INTO public.administration VALUES (19, 17, 14, 1853);
INSERT INTO public.administration VALUES (20, 18, 15, 1857);
INSERT INTO public.administration VALUES (21, 19, 16, 1861);
INSERT INTO public.administration VALUES (22, 20, 16, 1865);
INSERT INTO public.administration VALUES (23, 20, 17, 1865);
INSERT INTO public.administration VALUES (24, 21, 18, 1869);
INSERT INTO public.administration VALUES (25, 22, 18, 1873);
INSERT INTO public.administration VALUES (26, 23, 19, 1877);
INSERT INTO public.administration VALUES (27, 24, 20, 1881);
INSERT INTO public.administration VALUES (28, 24, 21, 1881);
INSERT INTO public.administration VALUES (29, 25, 22, 1885);
INSERT INTO public.administration VALUES (30, 26, 23, 1889);
INSERT INTO public.administration VALUES (31, 27, 22, 1893);
INSERT INTO public.administration VALUES (32, 28, 24, 1897);
INSERT INTO public.administration VALUES (33, 29, 24, 1901);
INSERT INTO public.administration VALUES (34, 29, 25, 1901);
INSERT INTO public.administration VALUES (35, 30, 25, 1905);
INSERT INTO public.administration VALUES (36, 31, 26, 1909);
INSERT INTO public.administration VALUES (37, 32, 27, 1913);
INSERT INTO public.administration VALUES (38, 33, 27, 1917);
INSERT INTO public.administration VALUES (39, 34, 28, 1921);
INSERT INTO public.administration VALUES (40, 34, 29, 1923);
INSERT INTO public.administration VALUES (41, 35, 29, 1925);
INSERT INTO public.administration VALUES (42, 36, 30, 1929);
INSERT INTO public.administration VALUES (43, 37, 31, 1933);
INSERT INTO public.administration VALUES (44, 38, 31, 1937);
INSERT INTO public.administration VALUES (45, 39, 31, 1941);
INSERT INTO public.administration VALUES (46, 40, 31, 1945);
INSERT INTO public.administration VALUES (47, 40, 32, 1945);
INSERT INTO public.administration VALUES (48, 41, 32, 1949);
INSERT INTO public.administration VALUES (49, 42, 33, 1953);
INSERT INTO public.administration VALUES (50, 43, 33, 1957);
INSERT INTO public.administration VALUES (51, 44, 34, 1961);
INSERT INTO public.administration VALUES (52, 44, 35, 1963);
INSERT INTO public.administration VALUES (53, 45, 35, 1965);
INSERT INTO public.administration VALUES (54, 46, 36, 1969);
INSERT INTO public.administration VALUES (55, 47, 36, 1973);
INSERT INTO public.administration VALUES (56, 47, 37, 1974);
INSERT INTO public.administration VALUES (57, 48, 38, 1977);
INSERT INTO public.administration VALUES (58, 49, 40, 1981);
INSERT INTO public.administration VALUES (59, 50, 40, 1985);
INSERT INTO public.administration VALUES (60, 51, 39, 1989);
INSERT INTO public.administration VALUES (61, 52, 41, 1993);
INSERT INTO public.administration VALUES (62, 53, 41, 1997);
INSERT INTO public.administration VALUES (63, 54, 42, 2001);
INSERT INTO public.administration VALUES (64, 55, 42, 2005);
INSERT INTO public.administration VALUES (65, 56, 43, 2009);
INSERT INTO public.administration VALUES (66, 57, 43, 2013);
INSERT INTO public.administration VALUES (67, 58, 44, 2017);


--
-- Data for Name: election; Type: TABLE DATA; Schema: public; Owner: exam
--

INSERT INTO public.election VALUES (1789, 'WASHINGTON G', 69, 'W');
INSERT INTO public.election VALUES (1789, 'ADAMS J', 34, 'L');
INSERT INTO public.election VALUES (1789, 'JAY J', 9, 'L');
INSERT INTO public.election VALUES (1789, 'HARRISON R H', 6, 'L');
INSERT INTO public.election VALUES (1789, 'RUTLEDGE J', 6, 'L');
INSERT INTO public.election VALUES (1789, 'HANCOCK J', 4, 'L');
INSERT INTO public.election VALUES (1789, 'CLINTON G', 3, 'L');
INSERT INTO public.election VALUES (1789, 'HUNTINGTON S', 2, 'L');
INSERT INTO public.election VALUES (1789, 'MILTON J', 2, 'L');
INSERT INTO public.election VALUES (1789, 'ARMSTRONG', 1, 'L');
INSERT INTO public.election VALUES (1789, 'LINCOLN B', 1, 'L');
INSERT INTO public.election VALUES (1789, 'TOLFAIR I', 1, 'L');
INSERT INTO public.election VALUES (1792, 'ADAMS J', 77, 'L');
INSERT INTO public.election VALUES (1792, 'CLINTON G', 50, 'L');
INSERT INTO public.election VALUES (1792, 'JEFFERSON T', 4, 'L');
INSERT INTO public.election VALUES (1796, 'PINCKNEY T', 59, 'L');
INSERT INTO public.election VALUES (1792, 'BURR A', 1, 'L');
INSERT INTO public.election VALUES (1796, 'ADAMS J', 71, 'W');
INSERT INTO public.election VALUES (1796, 'JEFFERSON T', 68, 'L');
INSERT INTO public.election VALUES (1796, 'BURR A', 30, 'L');
INSERT INTO public.election VALUES (1796, 'CLINTON G', 7, 'L');
INSERT INTO public.election VALUES (1796, 'JAY J', 5, 'L');
INSERT INTO public.election VALUES (1796, 'IREDELL J', 3, 'L');
INSERT INTO public.election VALUES (1796, 'HENRY J', 2, 'L');
INSERT INTO public.election VALUES (1796, 'JOHNSON S', 2, 'L');
INSERT INTO public.election VALUES (1796, 'WASHINGTON G', 2, 'L');
INSERT INTO public.election VALUES (1796, 'PINCKNEY C C', 1, 'L');
INSERT INTO public.election VALUES (1792, 'WASHINGTON G', 132, 'W');
INSERT INTO public.election VALUES (1796, 'ADAMS S', 15, 'L');
INSERT INTO public.election VALUES (1796, 'ELLSWORTH O', 11, 'L');
INSERT INTO public.election VALUES (1800, 'JEFFERSON T', 73, 'W');
INSERT INTO public.election VALUES (1800, 'BURR A', 73, 'L');
INSERT INTO public.election VALUES (1800, 'ADAMS J', 65, 'L');
INSERT INTO public.election VALUES (1800, 'JAY J', 1, 'L');
INSERT INTO public.election VALUES (1804, 'JEFFERSON T', 162, 'W');
INSERT INTO public.election VALUES (1804, 'PINCKNEY C C', 14, 'L');
INSERT INTO public.election VALUES (1800, 'PINCKNEY C C', 64, 'L');
INSERT INTO public.election VALUES (1808, 'MADISON J', 122, 'W');
INSERT INTO public.election VALUES (1808, 'PINCKNEY C C', 47, 'L');
INSERT INTO public.election VALUES (1808, 'CLINTON G', 6, 'L');
INSERT INTO public.election VALUES (1812, 'MADISON J', 128, 'W');
INSERT INTO public.election VALUES (1812, 'CLINTON G', 89, 'L');
INSERT INTO public.election VALUES (1816, 'MONROE J', 183, 'W');
INSERT INTO public.election VALUES (1816, 'KING R', 34, 'L');
INSERT INTO public.election VALUES (1820, 'MONROE J', 231, 'W');
INSERT INTO public.election VALUES (1820, 'ADAMS J Q', 1, 'L');
INSERT INTO public.election VALUES (1824, 'ADAMS J Q', 99, 'W');
INSERT INTO public.election VALUES (1824, 'JACKSON A', 84, 'L');
INSERT INTO public.election VALUES (1824, 'CRAWFORD W H', 41, 'L');
INSERT INTO public.election VALUES (1824, 'CLAY H', 37, 'L');
INSERT INTO public.election VALUES (1828, 'JACKSON A', 178, 'W');
INSERT INTO public.election VALUES (1828, 'ADAMS J', 83, 'L');
INSERT INTO public.election VALUES (1832, 'JACKSON A', 219, 'W');
INSERT INTO public.election VALUES (1832, 'CLAY H', 49, 'L');
INSERT INTO public.election VALUES (1832, 'FLOYD J', 11, 'L');
INSERT INTO public.election VALUES (1832, 'WIRT W', 7, 'L');
INSERT INTO public.election VALUES (1836, 'VAN BUREN M', 170, 'W');
INSERT INTO public.election VALUES (1836, 'HARRISON W H', 73, 'L');
INSERT INTO public.election VALUES (1836, 'WHITE H L', 26, 'L');
INSERT INTO public.election VALUES (1836, 'WEBSTER D', 14, 'L');
INSERT INTO public.election VALUES (1836, 'MANGUM W P', 11, 'L');
INSERT INTO public.election VALUES (1840, 'HARRISON W H', 234, 'W');
INSERT INTO public.election VALUES (1840, 'VAN BUREN M', 60, 'L');
INSERT INTO public.election VALUES (1844, 'POLK J K', 170, 'W');
INSERT INTO public.election VALUES (1844, 'CLAY H', 105, 'L');
INSERT INTO public.election VALUES (1848, 'TAYLOR Z', 163, 'W');
INSERT INTO public.election VALUES (1848, 'CASS L', 126, 'L');
INSERT INTO public.election VALUES (1852, 'PIERCE F', 254, 'W');
INSERT INTO public.election VALUES (1852, 'SCOTT W', 42, 'L');
INSERT INTO public.election VALUES (1856, 'BUCHANAN J', 174, 'W');
INSERT INTO public.election VALUES (1856, 'FREMONT J C', 114, 'L');
INSERT INTO public.election VALUES (1856, 'FILLMORE M', 8, 'L');
INSERT INTO public.election VALUES (1860, 'LINCOLN A', 180, 'W');
INSERT INTO public.election VALUES (1860, 'BRECKINRIDGE J C', 72, 'L');
INSERT INTO public.election VALUES (1860, 'BELL J', 39, 'L');
INSERT INTO public.election VALUES (1860, 'DOUGLAS S', 12, 'L');
INSERT INTO public.election VALUES (1864, 'LINCOLN A', 212, 'W');
INSERT INTO public.election VALUES (1864, 'MCCLELLAN G B', 21, 'L');
INSERT INTO public.election VALUES (1868, 'GRANT U S', 214, 'W');
INSERT INTO public.election VALUES (1868, 'SEYMOUR H', 80, 'L');
INSERT INTO public.election VALUES (1872, 'GRANT U S', 286, 'W');
INSERT INTO public.election VALUES (1872, 'HENDRICKS T A', 42, 'L');
INSERT INTO public.election VALUES (1872, 'BROWN B G', 18, 'L');
INSERT INTO public.election VALUES (1872, 'JENKINS C J', 2, 'L');
INSERT INTO public.election VALUES (1872, 'DAVIS D', 1, 'L');
INSERT INTO public.election VALUES (1876, 'HAYES R B', 185, 'W');
INSERT INTO public.election VALUES (1876, 'TILDEN R B', 184, 'L');
INSERT INTO public.election VALUES (1880, 'GARFIELD J A', 214, 'W');
INSERT INTO public.election VALUES (1880, 'HANCOCK W S', 155, 'L');
INSERT INTO public.election VALUES (1884, 'CLEVELAND G', 219, 'W');
INSERT INTO public.election VALUES (1884, 'BLAINE J G', 182, 'L');
INSERT INTO public.election VALUES (1888, 'HARRISON B', 233, 'W');
INSERT INTO public.election VALUES (1888, 'CLEVELAND G', 168, 'L');
INSERT INTO public.election VALUES (1892, 'CLEVELAND G', 277, 'W');
INSERT INTO public.election VALUES (1892, 'HARRISON B', 145, 'L');
INSERT INTO public.election VALUES (1892, 'WEAVER J B', 22, 'L');
INSERT INTO public.election VALUES (1896, 'MCKINLEY W', 271, 'W');
INSERT INTO public.election VALUES (1896, 'BRYAN W J', 176, 'L');
INSERT INTO public.election VALUES (1900, 'MCKINLEY W', 292, 'W');
INSERT INTO public.election VALUES (1900, 'BRYAN W J', 155, 'L');
INSERT INTO public.election VALUES (1904, 'ROOSEVELT T', 336, 'W');
INSERT INTO public.election VALUES (1904, 'PARKER E B', 140, 'L');
INSERT INTO public.election VALUES (1908, 'TAFT W H', 321, 'W');
INSERT INTO public.election VALUES (1908, 'BRYAN W J', 162, 'L');
INSERT INTO public.election VALUES (1912, 'WILSON W', 435, 'W');
INSERT INTO public.election VALUES (1912, 'ROOSEVELT T', 88, 'L');
INSERT INTO public.election VALUES (1912, 'TAFT W H', 8, 'L');
INSERT INTO public.election VALUES (1916, 'WILSON W', 277, 'W');
INSERT INTO public.election VALUES (1916, 'HUGHES C E', 254, 'L');
INSERT INTO public.election VALUES (1920, 'HARDING W G', 404, 'W');
INSERT INTO public.election VALUES (1920, 'COX W W', 127, 'L');
INSERT INTO public.election VALUES (1924, 'COOLIDGE C', 382, 'W');
INSERT INTO public.election VALUES (1924, 'DAVIS J W', 136, 'L');
INSERT INTO public.election VALUES (1924, 'LA FOLLETTE R M', 13, 'L');
INSERT INTO public.election VALUES (1928, 'HOOVER H C', 444, 'W');
INSERT INTO public.election VALUES (1928, 'SMITH A E', 87, 'L');
INSERT INTO public.election VALUES (1932, 'ROOSEVELT F D', 472, 'W');
INSERT INTO public.election VALUES (1932, 'HOOVER H C', 49, 'L');
INSERT INTO public.election VALUES (1936, 'ROOSEVELT F D', 523, 'W');
INSERT INTO public.election VALUES (1936, 'LANDON A M', 8, 'L');
INSERT INTO public.election VALUES (1940, 'ROOSEVELT F D', 449, 'W');
INSERT INTO public.election VALUES (1940, 'WILKIE W L', 82, 'L');
INSERT INTO public.election VALUES (1944, 'ROOSEVELT F D', 432, 'W');
INSERT INTO public.election VALUES (1944, 'DEWEY T E', 99, 'L');
INSERT INTO public.election VALUES (1948, 'TRUMAN H S', 303, 'W');
INSERT INTO public.election VALUES (1948, 'DEWEY T E', 189, 'L');
INSERT INTO public.election VALUES (1948, 'THURMOND J S', 39, 'L');
INSERT INTO public.election VALUES (1952, 'EISENHOWER D D', 442, 'W');
INSERT INTO public.election VALUES (1952, 'STEVENSON A E', 89, 'L');
INSERT INTO public.election VALUES (1956, 'EISENHOWER D D', 457, 'W');
INSERT INTO public.election VALUES (1956, 'STEVENSON A E', 73, 'L');
INSERT INTO public.election VALUES (1956, 'JONES W B', 1, 'L');
INSERT INTO public.election VALUES (1960, 'KENNEDY J F', 303, 'W');
INSERT INTO public.election VALUES (1960, 'NIXON R M', 219, 'L');
INSERT INTO public.election VALUES (1960, 'BYRD', 15, 'L');
INSERT INTO public.election VALUES (1964, 'JOHNSON L B', 486, 'W');
INSERT INTO public.election VALUES (1964, 'GOLDWATER B', 52, 'L');
INSERT INTO public.election VALUES (1968, 'NIXON R M', 301, 'W');
INSERT INTO public.election VALUES (1968, 'HUMPHREY H H', 191, 'L');
INSERT INTO public.election VALUES (1968, 'WALLACE G C', 46, 'L');
INSERT INTO public.election VALUES (1972, 'NIXON R M', 520, 'W');
INSERT INTO public.election VALUES (1972, 'MCGOVERN G S', 17, 'L');
INSERT INTO public.election VALUES (1972, 'HOSPERS J', 1, 'L');
INSERT INTO public.election VALUES (1976, 'CARTER J M', 297, 'W');
INSERT INTO public.election VALUES (1976, 'FORD G R', 240, 'L');
INSERT INTO public.election VALUES (1980, 'CARTER J M', 49, 'L');
INSERT INTO public.election VALUES (1980, 'REAGAN R', 489, 'W');
INSERT INTO public.election VALUES (1988, 'BUSH G H W', 426, 'W');
INSERT INTO public.election VALUES (1988, 'DUKAKIS M', 111, 'L');
INSERT INTO public.election VALUES (1992, 'CLINTON W J', 370, 'W');
INSERT INTO public.election VALUES (1992, 'BUSH G H W', 168, 'L');
INSERT INTO public.election VALUES (1996, 'CLINTON W J', 379, 'W');
INSERT INTO public.election VALUES (1996, 'DOLE B', 159, 'L');
INSERT INTO public.election VALUES (2000, 'BUSH G W', 271, 'W');
INSERT INTO public.election VALUES (2000, 'GORE A', 266, 'L');
INSERT INTO public.election VALUES (2004, 'BUSH G W', 286, 'W');
INSERT INTO public.election VALUES (2004, 'KERRY J', 251, 'L');
INSERT INTO public.election VALUES (2008, 'OBAMA B', 365, 'W');
INSERT INTO public.election VALUES (2008, 'MCCAIN J', 173, 'L');
INSERT INTO public.election VALUES (2012, 'OBAMA B', 332, 'W');
INSERT INTO public.election VALUES (2012, 'ROMNEY M', 206, 'L');
INSERT INTO public.election VALUES (2016, 'TRUMP D J', 304, 'W');
INSERT INTO public.election VALUES (2016, 'CLINTON H D R', 227, 'L');


--
-- Data for Name: pres_hobby; Type: TABLE DATA; Schema: public; Owner: exam
--

INSERT INTO public.pres_hobby VALUES (6, 'BILLIARDS');
INSERT INTO public.pres_hobby VALUES (6, 'SWIMMING');
INSERT INTO public.pres_hobby VALUES (21, 'FISHING');
INSERT INTO public.pres_hobby VALUES (22, 'FISHING');
INSERT INTO public.pres_hobby VALUES (29, 'FISHING');
INSERT INTO public.pres_hobby VALUES (29, 'GOLF');
INSERT INTO public.pres_hobby VALUES (29, 'INDIAN CLUBS');
INSERT INTO public.pres_hobby VALUES (29, 'MECH. HORS');
INSERT INTO public.pres_hobby VALUES (29, 'PITCHING HAY');
INSERT INTO public.pres_hobby VALUES (33, 'BRIDGE');
INSERT INTO public.pres_hobby VALUES (33, 'GOLF');
INSERT INTO public.pres_hobby VALUES (33, 'HUNTING');
INSERT INTO public.pres_hobby VALUES (33, 'PAINTING');
INSERT INTO public.pres_hobby VALUES (33, 'FISHING');
INSERT INTO public.pres_hobby VALUES (20, 'BILLIARDS');
INSERT INTO public.pres_hobby VALUES (28, 'GOLF');
INSERT INTO public.pres_hobby VALUES (28, 'POKER');
INSERT INTO public.pres_hobby VALUES (28, 'RIDING');
INSERT INTO public.pres_hobby VALUES (23, 'HUNTING');
INSERT INTO public.pres_hobby VALUES (19, 'CROQUET');
INSERT INTO public.pres_hobby VALUES (19, 'DRIVING');
INSERT INTO public.pres_hobby VALUES (19, 'SHOOTING');
INSERT INTO public.pres_hobby VALUES (30, 'FISHING');
INSERT INTO public.pres_hobby VALUES (30, 'MEDICINE BALL');
INSERT INTO public.pres_hobby VALUES (7, 'RIDING');
INSERT INTO public.pres_hobby VALUES (3, 'FISHING');
INSERT INTO public.pres_hobby VALUES (3, 'RIDING');
INSERT INTO public.pres_hobby VALUES (35, 'RIDING');
INSERT INTO public.pres_hobby VALUES (34, 'SAILING');
INSERT INTO public.pres_hobby VALUES (34, 'SWIMMING');
INSERT INTO public.pres_hobby VALUES (34, 'TOUCH FOOTBALL');
INSERT INTO public.pres_hobby VALUES (16, 'WALKING');
INSERT INTO public.pres_hobby VALUES (24, 'RIDING');
INSERT INTO public.pres_hobby VALUES (24, 'SWIMMING');
INSERT INTO public.pres_hobby VALUES (24, 'WALKING');
INSERT INTO public.pres_hobby VALUES (36, 'GOLF');
INSERT INTO public.pres_hobby VALUES (31, 'FISHING');
INSERT INTO public.pres_hobby VALUES (31, 'SAILING');
INSERT INTO public.pres_hobby VALUES (31, 'SWIMMING');
INSERT INTO public.pres_hobby VALUES (25, 'BOXING');
INSERT INTO public.pres_hobby VALUES (25, 'HUNTING');
INSERT INTO public.pres_hobby VALUES (25, 'JUJITSU');
INSERT INTO public.pres_hobby VALUES (25, 'RIDING');
INSERT INTO public.pres_hobby VALUES (25, 'SHOOTING');
INSERT INTO public.pres_hobby VALUES (25, 'TENNIS');
INSERT INTO public.pres_hobby VALUES (25, 'WRESTLING');
INSERT INTO public.pres_hobby VALUES (26, 'GOLF');
INSERT INTO public.pres_hobby VALUES (26, 'RIDING');
INSERT INTO public.pres_hobby VALUES (12, 'RIDING');
INSERT INTO public.pres_hobby VALUES (32, 'FISHING');
INSERT INTO public.pres_hobby VALUES (32, 'POKER');
INSERT INTO public.pres_hobby VALUES (32, 'WALKING');
INSERT INTO public.pres_hobby VALUES (8, 'RIDING');
INSERT INTO public.pres_hobby VALUES (1, 'FISHING');
INSERT INTO public.pres_hobby VALUES (1, 'RIDING');
INSERT INTO public.pres_hobby VALUES (27, 'GOLF');
INSERT INTO public.pres_hobby VALUES (27, 'RIDING');
INSERT INTO public.pres_hobby VALUES (27, 'WALKING');
INSERT INTO public.pres_hobby VALUES (6, 'WALKING');
INSERT INTO public.pres_hobby VALUES (40, 'GOLF');
INSERT INTO public.pres_hobby VALUES (43, 'COIKING');
INSERT INTO public.pres_hobby VALUES (43, 'BASKETBALL');
INSERT INTO public.pres_hobby VALUES (43, 'DANCING');
INSERT INTO public.pres_hobby VALUES (41, 'PLAYING SAXOPHONE');
INSERT INTO public.pres_hobby VALUES (42, 'FISHING');
INSERT INTO public.pres_hobby VALUES (42, 'JOGGING');
INSERT INTO public.pres_hobby VALUES (39, 'FISHING');
INSERT INTO public.pres_hobby VALUES (44, 'GOLF');


--
-- Data for Name: pres_marriage; Type: TABLE DATA; Schema: public; Owner: exam
--

INSERT INTO public.pres_marriage VALUES (1, 'CUSTIS M D', 27, 0, 1759);
INSERT INTO public.pres_marriage VALUES (2, 'SMITH A', 19, 5, 1764);
INSERT INTO public.pres_marriage VALUES (3, 'SKELTON M W', 23, 6, 1772);
INSERT INTO public.pres_marriage VALUES (4, 'TODD D D P', 26, 0, 1794);
INSERT INTO public.pres_marriage VALUES (5, 'KORTRIGHT E', 17, 3, 1786);
INSERT INTO public.pres_marriage VALUES (6, 'JOHNSON L C', 22, 4, 1797);
INSERT INTO public.pres_marriage VALUES (7, 'ROBARDS R D', 26, 0, 1794);
INSERT INTO public.pres_marriage VALUES (8, 'HOOS H', 23, 4, 1807);
INSERT INTO public.pres_marriage VALUES (9, 'SYMMES A T', 20, 10, 1795);
INSERT INTO public.pres_marriage VALUES (10, 'CHRISTIAN L', 22, 8, 1813);
INSERT INTO public.pres_marriage VALUES (10, 'GARDINER J', 24, 7, 1844);
INSERT INTO public.pres_marriage VALUES (11, 'CHILDRESS S', 20, 0, 1824);
INSERT INTO public.pres_marriage VALUES (12, 'SMITH M M', 21, 6, 1810);
INSERT INTO public.pres_marriage VALUES (13, 'POWERS A', 27, 2, 1826);
INSERT INTO public.pres_marriage VALUES (13, 'MCINTOSH C C', 44, 0, 1858);
INSERT INTO public.pres_marriage VALUES (14, 'APPLETON J M', 28, 3, 1834);
INSERT INTO public.pres_marriage VALUES (16, 'TODD M', 23, 4, 1842);
INSERT INTO public.pres_marriage VALUES (17, 'MCCARDLE E', 16, 5, 1827);
INSERT INTO public.pres_marriage VALUES (18, 'DENT J B', 22, 4, 1848);
INSERT INTO public.pres_marriage VALUES (19, 'WEBB L W', 21, 8, 1852);
INSERT INTO public.pres_marriage VALUES (20, 'RUDOLPH L', 26, 7, 1858);
INSERT INTO public.pres_marriage VALUES (21, 'HERNDON E L', 22, 3, 1859);
INSERT INTO public.pres_marriage VALUES (22, 'FOLSON F', 21, 5, 1886);
INSERT INTO public.pres_marriage VALUES (23, 'SCOTT C L', 31, 2, 1853);
INSERT INTO public.pres_marriage VALUES (23, 'DIMMICK M S L', 37, 1, 1896);
INSERT INTO public.pres_marriage VALUES (24, 'SAXTON I', 23, 2, 1871);
INSERT INTO public.pres_marriage VALUES (25, 'LEE A H', 19, 1, 1880);
INSERT INTO public.pres_marriage VALUES (25, 'CARROW E K', 25, 5, 1886);
INSERT INTO public.pres_marriage VALUES (26, 'HERRON H', 25, 3, 1886);
INSERT INTO public.pres_marriage VALUES (27, 'AXSON E L', 25, 3, 1885);
INSERT INTO public.pres_marriage VALUES (27, 'GALT E B', 43, 0, 1915);
INSERT INTO public.pres_marriage VALUES (28, 'DE WOLFE F K', 30, 0, 1891);
INSERT INTO public.pres_marriage VALUES (29, 'GOODHUE G A', 26, 2, 1905);
INSERT INTO public.pres_marriage VALUES (30, 'HENRY L', 23, 2, 1899);
INSERT INTO public.pres_marriage VALUES (31, 'ROOSEVELT A E', 20, 6, 1905);
INSERT INTO public.pres_marriage VALUES (32, 'WALLACE E V', 34, 1, 1919);
INSERT INTO public.pres_marriage VALUES (33, 'DOUD G', 19, 2, 1916);
INSERT INTO public.pres_marriage VALUES (34, 'BOUVIER J L', 24, 3, 1953);
INSERT INTO public.pres_marriage VALUES (35, 'TAYLOR C A', 21, 2, 1934);
INSERT INTO public.pres_marriage VALUES (36, 'RYAN T C', 28, 2, 1940);
INSERT INTO public.pres_marriage VALUES (37, 'WARREN E B', 30, 4, 1948);
INSERT INTO public.pres_marriage VALUES (38, 'SMITH R', 18, 4, 1946);
INSERT INTO public.pres_marriage VALUES (40, 'WYMAN J', 25, 2, 1940);
INSERT INTO public.pres_marriage VALUES (40, 'DAVIS N', 28, 2, 1952);
INSERT INTO public.pres_marriage VALUES (39, 'PIERCE B', 20, 6, 1945);
INSERT INTO public.pres_marriage VALUES (42, 'WELCH L L', 31, 2, 1977);
INSERT INTO public.pres_marriage VALUES (43, 'ROBINSON M', 28, 2, 1992);
INSERT INTO public.pres_marriage VALUES (41, 'RODHAM H', 28, 1, 1975);
INSERT INTO public.pres_marriage VALUES (44, 'ZELNICKOVA I', 28, 3, 1977);
INSERT INTO public.pres_marriage VALUES (44, 'MAPLES M', 30, 1, 1993);
INSERT INTO public.pres_marriage VALUES (44, 'KNAUSS M', 34, 1, 2005);


--
-- Data for Name: president; Type: TABLE DATA; Schema: public; Owner: exam
--

INSERT INTO public.president VALUES (1, 'WASHINGTON G', 1732, 7, 67, 'FEDERALIST', 35);
INSERT INTO public.president VALUES (2, 'ADAMS J', 1735, 4, 90, 'FEDERALIST', 38);
INSERT INTO public.president VALUES (3, 'JEFFERSON T', 1743, 8, 83, 'DEMO-REP', 35);
INSERT INTO public.president VALUES (4, 'MADISON J', 1751, 8, 85, 'DEMO-REP', 35);
INSERT INTO public.president VALUES (5, 'MONROE J', 1758, 8, 73, 'DEMO-REP', 35);
INSERT INTO public.president VALUES (6, 'ADAMS J Q', 1767, 4, 80, 'DEMO-REP', 38);
INSERT INTO public.president VALUES (7, 'JACKSON A', 1767, 8, 78, 'DEMOCRATIC', 40);
INSERT INTO public.president VALUES (8, 'VAN BUREN M', 1782, 4, 79, 'DEMOCRATIC', 46);
INSERT INTO public.president VALUES (9, 'HARRISON W H', 1773, 0, 68, 'WHIG', 35);
INSERT INTO public.president VALUES (10, 'TYLER J', 1790, 3, 71, 'WHIG', 35);
INSERT INTO public.president VALUES (11, 'POLK J K', 1795, 4, 53, 'DEMOCRATIC', 47);
INSERT INTO public.president VALUES (12, 'TAYLOR Z', 1784, 1, 65, 'WHIG', 35);
INSERT INTO public.president VALUES (13, 'FILLMORE M', 1800, 2, 74, 'WHIG', 46);
INSERT INTO public.president VALUES (14, 'PIERCE F', 1804, 4, 64, 'DEMOCRATIC', 44);
INSERT INTO public.president VALUES (15, 'BUCHANAN J', 1791, 4, 77, 'DEMOCRATIC', 37);
INSERT INTO public.president VALUES (16, 'LINCOLN A', 1809, 4, 56, 'REPUBLICAN', 49);
INSERT INTO public.president VALUES (17, 'JOHNSON A', 1808, 3, 66, 'DEMOCRATIC', 37);
INSERT INTO public.president VALUES (18, 'GRANT U S', 1822, 8, 63, 'REPUBLICAN', 1);
INSERT INTO public.president VALUES (19, 'HAYES R B', 1822, 4, 70, 'REPUBLICAN', 1);
INSERT INTO public.president VALUES (20, 'GARFIELD J A', 1831, 0, 49, 'REPUBLICAN', 1);
INSERT INTO public.president VALUES (21, 'ARTHUR C A', 1830, 3, 56, 'REPUBLICAN', 36);
INSERT INTO public.president VALUES (22, 'CLEVELAND G', 1837, 8, 71, 'DEMOCRATIC', 42);
INSERT INTO public.president VALUES (23, 'HARRISON B', 1833, 4, 67, 'REPUBLICAN', 1);
INSERT INTO public.president VALUES (24, 'MCKINLEY W', 1843, 4, 58, 'REPUBLICAN', 1);
INSERT INTO public.president VALUES (25, 'ROOSEVELT T', 1858, 7, 60, 'REPUBLICAN', 46);
INSERT INTO public.president VALUES (26, 'TAFT W H', 1857, 4, 72, 'REPUBLICAN', 1);
INSERT INTO public.president VALUES (27, 'WILSON W', 1856, 8, 67, 'DEMOCRATIC', 35);
INSERT INTO public.president VALUES (28, 'HARDING W G', 1865, 2, 57, 'REPUBLICAN', 1);
INSERT INTO public.president VALUES (29, 'COOLIDGE C', 1872, 5, 60, 'REPUBLICAN', 36);
INSERT INTO public.president VALUES (30, 'HOOVER H C', 1874, 4, 90, 'REPUBLICAN', 13);
INSERT INTO public.president VALUES (31, 'ROOSEVELT F D', 1882, 12, 63, 'DEMOCRATIC', 46);
INSERT INTO public.president VALUES (32, 'TRUMAN H S', 1884, 7, 88, 'DEMOCRATIC', 8);
INSERT INTO public.president VALUES (33, 'EISENHOWER D D', 1890, 8, 79, 'REPUBLICAN', 12);
INSERT INTO public.president VALUES (34, 'KENNEDY J F', 1917, 2, 46, 'DEMOCRATIC', 38);
INSERT INTO public.president VALUES (35, 'JOHNSON L B', 1908, 5, 65, 'DEMOCRATIC', 12);
INSERT INTO public.president VALUES (36, 'NIXON R M', 1913, 5, 81, 'REPUBLICAN', 15);
INSERT INTO public.president VALUES (37, 'FORD G R', 1913, 2, 93, 'REPUBLICAN', 21);
INSERT INTO public.president VALUES (38, 'CARTER J M', 1924, 4, NULL, 'DEMOCRATIC', 43);
INSERT INTO public.president VALUES (39, 'BUSH G H W', 1924, 4, NULL, 'REPUBLICAN', 38);
INSERT INTO public.president VALUES (40, 'REAGAN R', 1911, 8, 93, 'REPUBLICAN', 5);
INSERT INTO public.president VALUES (41, 'CLINTON W J', 1946, 8, NULL, 'DEMOCRATIC', 9);
INSERT INTO public.president VALUES (42, 'BUSH G W', 1946, 8, NULL, 'REPUBLICAN', 39);
INSERT INTO public.president VALUES (43, 'OBAMA B', 1961, 8, NULL, 'DEMOCRATIC', 34);
INSERT INTO public.president VALUES (44, 'TRUMP D J', 1946, 0, NULL, 'REPUBLICAN', 46);


--
-- Data for Name: state; Type: TABLE DATA; Schema: public; Owner: exam
--

INSERT INTO public.state VALUES (1, 'OHIO', 4, 1803);
INSERT INTO public.state VALUES (2, 'LOUISIANNA', 6, 1812);
INSERT INTO public.state VALUES (3, 'INDIANA', 7, 1816);
INSERT INTO public.state VALUES (4, 'MISSISSIPI', 8, 1817);
INSERT INTO public.state VALUES (5, 'ILLINOIS', 8, 1818);
INSERT INTO public.state VALUES (6, 'ALABAMA', 8, 1819);
INSERT INTO public.state VALUES (7, 'MAINE', 8, 1820);
INSERT INTO public.state VALUES (8, 'MISSOURI', 9, 1821);
INSERT INTO public.state VALUES (9, 'ARKANSAS', 12, 1836);
INSERT INTO public.state VALUES (10, 'MICHIGAN', 12, 1837);
INSERT INTO public.state VALUES (11, 'FLORIDA', 15, 1845);
INSERT INTO public.state VALUES (12, 'TEXAS', 16, 1845);
INSERT INTO public.state VALUES (13, 'IOWA', 16, 1846);
INSERT INTO public.state VALUES (14, 'WISCONSIN', 16, 1848);
INSERT INTO public.state VALUES (15, 'CALIFORNIA', 18, 1850);
INSERT INTO public.state VALUES (16, 'MINNESOTA', 20, 1858);
INSERT INTO public.state VALUES (17, 'OREGON', 20, 1859);
INSERT INTO public.state VALUES (18, 'KANSAS', 20, 1861);
INSERT INTO public.state VALUES (19, 'WEST VIRGINIA', 21, 1863);
INSERT INTO public.state VALUES (20, 'NEVADA', 21, 1864);
INSERT INTO public.state VALUES (21, 'NEBRASKA', 22, 1867);
INSERT INTO public.state VALUES (22, 'COLORADO', 25, 1876);
INSERT INTO public.state VALUES (23, 'NORTH DAKOTA', 30, 1889);
INSERT INTO public.state VALUES (24, 'SOUTH DAKOTA', 30, 1889);
INSERT INTO public.state VALUES (25, 'MONTANA', 30, 1889);
INSERT INTO public.state VALUES (26, 'WASHINGTON', 30, 1889);
INSERT INTO public.state VALUES (27, 'IDAHO', 30, 1890);
INSERT INTO public.state VALUES (28, 'WYOMING', 30, 1890);
INSERT INTO public.state VALUES (29, 'UTAH', 31, 1896);
INSERT INTO public.state VALUES (30, 'OKLAEXAMA', 35, 1907);
INSERT INTO public.state VALUES (31, 'NEW MEXICO', 36, 1912);
INSERT INTO public.state VALUES (32, 'ARIZONA', 36, 1912);
INSERT INTO public.state VALUES (33, 'ALASKA', 50, 1959);
INSERT INTO public.state VALUES (34, 'HAWAII', 50, 1959);
INSERT INTO public.state VALUES (35, 'VIRGINIA', 0, 1776);
INSERT INTO public.state VALUES (36, 'VERMONT', 1, 1791);
INSERT INTO public.state VALUES (37, 'PENNSYLVANIA', 0, 1776);
INSERT INTO public.state VALUES (38, 'MASSACHUSETTS', 0, 1776);
INSERT INTO public.state VALUES (39, 'CONNECTICUT', 0, 1776);
INSERT INTO public.state VALUES (40, 'SOUTH CAROLINA', 0, 1776);
INSERT INTO public.state VALUES (41, 'MARYLAND', 0, 1776);
INSERT INTO public.state VALUES (42, 'NEW JERSEY', 0, 1776);
INSERT INTO public.state VALUES (43, 'GEORGIA', 0, 1776);
INSERT INTO public.state VALUES (44, 'NEW HAMPSHIRE', 0, 1776);
INSERT INTO public.state VALUES (45, 'DELAWARE', 0, 1776);
INSERT INTO public.state VALUES (46, 'NEW YORK', 0, 1776);
INSERT INTO public.state VALUES (47, 'NORTH CAROLINA', 0, 1776);
INSERT INTO public.state VALUES (48, 'RHODE ISLAND', 0, 1776);
INSERT INTO public.state VALUES (49, 'KENTUCKY', 1, 1792);
INSERT INTO public.state VALUES (50, 'TENNESSEE', 2, 1796);


--
-- Name: administration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: exam
--

SELECT pg_catalog.setval('public.administration_id_seq', 67, true);


--
-- Name: president_id_seq; Type: SEQUENCE SET; Schema: public; Owner: exam
--

SELECT pg_catalog.setval('public.president_id_seq', 44, true);


--
-- Name: state_id_seq; Type: SEQUENCE SET; Schema: public; Owner: exam
--

SELECT pg_catalog.setval('public.state_id_seq', 50, true);


--
-- Name: administration prim_key_adm; Type: CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.administration
    ADD CONSTRAINT prim_key_adm PRIMARY KEY (id);


--
-- Name: admin_vpres prim_key_admvp; Type: CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.admin_vpres
    ADD CONSTRAINT prim_key_admvp PRIMARY KEY (admin_id, vice_pres_name);


--
-- Name: election prim_key_elec; Type: CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.election
    ADD CONSTRAINT prim_key_elec PRIMARY KEY (election_year, candidate);


--
-- Name: pres_hobby prim_key_hobby; Type: CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.pres_hobby
    ADD CONSTRAINT prim_key_hobby PRIMARY KEY (pres_id, hobby);


--
-- Name: president prim_key_pres; Type: CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.president
    ADD CONSTRAINT prim_key_pres PRIMARY KEY (id);


--
-- Name: pres_marriage prim_key_presmar; Type: CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.pres_marriage
    ADD CONSTRAINT prim_key_presmar PRIMARY KEY (pres_id, spouse_name);


--
-- Name: state prim_key_state; Type: CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.state
    ADD CONSTRAINT prim_key_state PRIMARY KEY (id);


--
-- Name: iadmin; Type: INDEX; Schema: public; Owner: exam
--

CREATE INDEX iadmin ON public.administration USING btree (pres_id, year_inaugurated);


--
-- Name: iadminnrpresid; Type: INDEX; Schema: public; Owner: exam
--

CREATE UNIQUE INDEX iadminnrpresid ON public.administration USING btree (admin_nr, pres_id);


--
-- Name: ipres; Type: INDEX; Schema: public; Owner: exam
--

CREATE INDEX ipres ON public.president USING btree (party);


--
-- Name: admin_vpres admin_vpres_fk1; Type: FK CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.admin_vpres
    ADD CONSTRAINT admin_vpres_fk1 FOREIGN KEY (admin_id) REFERENCES public.administration(id);


--
-- Name: administration administration_fk1; Type: FK CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.administration
    ADD CONSTRAINT administration_fk1 FOREIGN KEY (pres_id) REFERENCES public.president(id);


--
-- Name: pres_hobby pres_hobby_fk1; Type: FK CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.pres_hobby
    ADD CONSTRAINT pres_hobby_fk1 FOREIGN KEY (pres_id) REFERENCES public.president(id);


--
-- Name: pres_marriage pres_marriage_fk1; Type: FK CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.pres_marriage
    ADD CONSTRAINT pres_marriage_fk1 FOREIGN KEY (pres_id) REFERENCES public.president(id);


--
-- Name: president president_fk1; Type: FK CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.president
    ADD CONSTRAINT president_fk1 FOREIGN KEY (state_id_born) REFERENCES public.state(id);


--
-- Name: state state_fk1; Type: FK CONSTRAINT; Schema: public; Owner: exam
--

ALTER TABLE ONLY public.state
    ADD CONSTRAINT state_fk1 FOREIGN KEY (admin_id) REFERENCES public.administration(id);


--
-- PostgreSQL database dump complete
--

commit;