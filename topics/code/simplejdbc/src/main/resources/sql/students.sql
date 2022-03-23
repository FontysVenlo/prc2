-- students table
begin work;
drop table if exists students;
CREATE TABLE students (
       student_id SERIAL PRIMARY KEY,
       firstname TEXT NOT NULL,
       lastname TEXT NOT NULL,
       dob DATE NOT NULL,
       cohort integer NOT  NULL,
       email TEXT NOT NULL,
       gender CHAR(1) NOT NULL,
       student_grp VARCHAR(10) NOT NULL,
       active boolean NOT NULL DEFAULT FALSE
);
commit;