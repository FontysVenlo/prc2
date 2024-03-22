package simplejdbc;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
class StudentDemo {

    private final DataSource ds;
    private final PrintStream out;

    public StudentDemo(DataSource ds, PrintStream out) {
        this.ds = ds;
        this.out = out;
    }

    private static String query = "select * from students";

    public List<Student> listAll() {
        var result = new ArrayList<Student>();
<<<<<<< Updated upstream
        try ( Connection con = ds.getConnection(); 
                PreparedStatement pst = con.prepareStatement( query ); 
                ResultSet rs = pst.executeQuery(); ) {
=======
        try ( Connection con = ds.getConnection(); PreparedStatement pst = con.prepareStatement( query ); ResultSet rs = pst.executeQuery(); ) {
>>>>>>> Stashed changes
            while ( rs.next() ) {
                Integer snummer = rs.getInt( 1 );
                String firstname = rs.getString( 2 );
                String lastname = rs.getString( 3 );
                LocalDate dob = rs.getDate( 4 ).toLocalDate();
                Integer cohort = rs.getInt( 5 );
                String email = rs.getString( 6 );
                String gender = rs.getString( 7 );
                String student_class = rs.getString( 8 );
                Boolean active = rs.getBoolean( 9 );

<<<<<<< Updated upstream
                Student student = new Student( snummer, firstname, lastname, 
                        dob, cohort, email, gender, student_class, active );
=======
                Student student = new Student( snummer, firstname, lastname, dob, cohort, email, gender, student_class, active );
>>>>>>> Stashed changes
                result.add( student );
            }
        } catch ( Throwable ex ) {
            Logger.getLogger( StudentDemo.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return result;
    }

<<<<<<< Updated upstream
    private static final String insertSql = 
        """
        insert into students (student_id,firstname,lastname,dob,cohort,email,gender,student_grp,active)
        values(                         ?,       ?,       ?,  ?,     ?,    ?,     ?,          ?,     ?)
        """;
=======
    private static final String insertSql = """
                     insert into students (student_id,firstname,lastname,dob,cohort,email,gender,student_grp,active)
                     values(                         ?,       ?,       ?,  ?,     ?,    ?,     ?,          ?,     ?)
                     """;
>>>>>>> Stashed changes

    public void insertStudents(List<Student> students) {
        try ( Connection con = ds.getConnection(); PreparedStatement pst = con.prepareStatement( insertSql ); ) {
            con.setAutoCommit( false );

            for ( Student s : students ) {
                pst.setInt( 1, s.snummer() );
                pst.setString( 2, s.firstname() );
                pst.setString( 3, s.lastname() );
                pst.setDate( 4, Date.valueOf( s.dob() ) );
                pst.setInt( 5, s.cohort() );
                pst.setString( 6, s.email() );
                pst.setString( 7, s.gender() );
                pst.setString( 8, s.student_class() );
                pst.setBoolean( 9, s.active() );
                pst.addBatch();
            }

            var eb = pst.executeBatch();
            System.out.println( "inserted " + eb.length );
            con.commit();
        } catch ( SQLException ex ) {
            LOG.log( Level.SEVERE, null, ex );
            throw new RuntimeException( ex.getMessage(), ex );
        }

    }
    private static final Logger LOG = Logger.getLogger( StudentDemo.class.getName() );

    public static void main(String[] args) throws IOException {
        var dataSource = PgJDBCUtils.getDataSource( "jdbc.pg.dev" );
        var demo = new StudentDemo( dataSource, System.out );

        System.out.println( "getting all available students" );
        var myClass = demo.listAll();

        myClass.stream().forEach( System.out::println );

        if ( myClass.size() == 0 ) {
            System.out.println( "Mh, none found. Where are they??" );

            System.out.println( "new balls please" );
            List<Student> newStudents = Factories.studentsFromFile( "students.csv" );
            System.out.println( "new students" );
            newStudents.forEach( System.out::println);
            demo.insertStudents( newStudents );

            myClass = demo.listAll();

            System.out.println( "there they are " );
            myClass.stream().forEach( System.out::println );
        }
    }
}
