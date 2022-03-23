package nl.fontys.sebivenlo.csvobjectstream;

import java.time.LocalDate;

/**
 * Factory methods to create objects of various kinds with args list. Convention
 * used: arguments are provided and processed in order of definition if the
 * target class. This order is in no way verified.
 *
 * @author Pieter van den Hombergh (p dot vandenhombergh at fontys dot nl)
 */
class Factories {

    /**
     * Create a student from an array of string input
     *
     * @param args the parameters
     * @return a new student
     */
    static Student createStudent( String[] args ) {
        assert args.length >= 4;
        long id = Long.parseLong( args[ 0 ] );
        String name = args[ 1 ];
        LocalDate bd = LocalDate.parse( args[ 2 ] );
        Student.Gender sex = Student.Gender.valueOf( args[ 3 ] );
        return new Student( id, name, bd, sex );
    }

    /**
     * Dump a student fields as array of string
     *
     * @param s the student to use
     * @return array of string representing the
     */
    static String[] studentAsCSVLine( Student s ) {
        String[] attributes = { s.name, Long.toString( s.id ),
            s.birthDate.toString(), s.gender.toString() };
        return attributes;
    }

    /**
     * Check the array of strings to be passed before used in the factory method
     * createStudent.
     * @param constructorArgs to check
     * @return  true if acceptable.
     */
    static boolean checkStringArray(String[] constructorArgs){
        return constructorArgs.length>= 4 
                && constructorArgs[1].matches("^\\d+$ ")
                && constructorArgs[2].matches( "^\\d{4}-\\d{2}-\\d{2}$ ")
                && null!= Student.Gender.valueOf( constructorArgs[ 3 ] );
    }
    
    static class Student {

        enum Gender {
            M, F
        }

        final long id;
        final String name;
        final LocalDate birthDate;
        final Gender gender;

        public Student( long id, String name, LocalDate birthDate, Gender sex ) {
            this.id = id;
            this.name = name;
            this.birthDate = birthDate;
            this.gender = sex;
        }

        @Override
        public String toString() {
            return "Student{" + "id=" + id + ", name=" + name + ", birthDate="
                    + birthDate + "," + gender + '}';
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public Gender getGender() {
            return gender;
        }

    }
}
