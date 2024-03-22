/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package simplejdbc;

//import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
public class InsertTest {

    public InsertTest() {
    }

    //@Disabled("think TDD")
    @DisplayName("make sure we can insert a cohort full of students")
    @Test
    public void insertTest() {
        var ds = PgJDBCUtils.getDataSource( "jdbc.pg.dev" );
        StudentDemo demo = new StudentDemo( ds, System.out );

        ThrowingCallable code = () -> {
            try ( Connection conn = ds.getConnection(); ) {
                List<Student> students = Factories.studentsFromFile( "students.csv" );
                demo.insertStudents( students );
                var stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery( "select count(1) from students" );
                rs.next();
                int count = rs.getInt( 1 );
                System.out.println( "count = " + count );
                assertThat( count ).isGreaterThan( 1 );

            }
        };

        assertThatCode( code ).doesNotThrowAnyException();
//        fail( "method insert reached end. You know what to do." );
    }

    @AfterEach
    public void cleanup() throws SQLException, IOException {
        System.out.println( "cleaning up afterwards ");
        var ds = PgJDBCUtils.getDataSource( "jdbc.pg.dev" );
        try ( Connection conn = ds.getConnection();  ) {
            conn.setAutoCommit( false);
            conn.createStatement().execute( "truncate students" );
            conn.commit();
        }
    }

}
