package simplejdbc;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import nl.fontys.sebivenlo.csvobjectstream.CSVObjectStream;

/**
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
public class Factories {

    public static Student studentFromArray(Object[] in) {

        Integer snummer = (Integer) in[ 0 ];
        String firstname = (String) in[ 1 ];
        String lastname = (String) in[ 2 ];
        LocalDate dob = ( (java.sql.Date) in[ 3 ] ).toLocalDate();
        Integer cohort = (Integer) in[ 4 ];
        String email = (String) in[ 5 ];
        String gender = (String) in[ 6 ];
        String student_class = (String) in[ 7 ];
        Boolean active = (Boolean) in[ 8 ];
        return new Student( snummer, firstname, lastname, dob, cohort, email, gender, student_class, active );

    }

    public static Object[] studentToArray(Student s) {
        return new Object[]{
            s.snummer(), s.firstname(), s.lastname(), s.dob(),
            s.cohort(), s.email(), s.gender(), s.student_class(), s.active()
        };
    }

    public static Student studentFromStrings(String[] in) {

        Integer snummer = Integer.valueOf( in[ 0 ] );
        String firstname = (String) in[ 1 ];
        String lastname = (String) in[ 2 ];
        LocalDate dob = LocalDate.parse( in[ 3 ] );
        Integer cohort = Integer.valueOf( in[ 4 ] );
        String email = (String) in[ 5 ];
        String gender = (String) in[ 6 ];
        String student_class = (String) in[ 7 ];
        Boolean active = Boolean.valueOf( in[ 8 ] );
        return new Student( snummer, firstname, lastname, dob, cohort, email, gender, student_class, active );

    }

    public static List<Student> studentsFromFile(String fileName) throws IOException {
        Function<String[], Student> fact = Factories::studentFromStrings;
        Predicate<String[]> filter = a -> a.length == 9 && a[ 0 ].matches( "\\d+" );
        return new CSVObjectStream<Student>( Path.of( fileName ), "," )
                .asList( fact, filter );
    }

}
