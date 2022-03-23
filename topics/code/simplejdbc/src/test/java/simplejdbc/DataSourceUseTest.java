package simplejdbc;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.sql.DataSource;
import nl.fontys.sebivenlo.csvobjectstream.CSVObjectStream;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
@ExtendWith( MockitoExtension.class )
public class DataSourceUseTest {
    
    @Mock
    PrintStream out;

    //@Disabled("Think TDD")
    @Test
    public void tInstertStudents() throws IOException {
        
        Stream<Student> studs = new CSVObjectStream<Student>( Path.of( "students.csv" ) )
                .stream( Student::fromArray, a -> a[0].matches( "\\d+" ) );
        String sql = "insert into students (student_id,firstname,lastname,dob,cohort,email,gender,student_grp,active)\n"
                + "values(                           ?,       ?,       ?,  ?,     ?,    ?,     ?,          ?,     ?)";
        
        DataSource ds = PgJDBCUtils.getDataSource( "jdbc.pg.fontys" );
        
        try ( Connection con = ds.getConnection();
                PreparedStatement pst = con.prepareStatement( sql ); ) {
            con.setAutoCommit( false );
            studs.forEach( s -> {
                int col = 1;
                for ( Object object : s.asArray() ) {
                    setObject( pst, col, object );
                }
            } );
            
            int[] executeBatch = pst.executeBatch();
            System.out.println( "executeBatch = " + Arrays.toString( executeBatch ));
            con.commit();
        } catch ( SQLException ex ) {
            Logger.getLogger( DataSourceUseTest.class.getName() ).log( Level.SEVERE, null, ex );
        }
        
        StudentDemo sd = new StudentDemo( ds, out );
        
        List<Student> list = sd.listAll();
        assertThat( list ).hasSize( 100 );
        
        fail( "tInstertStudents completed succesfully; you know what to do" );
    }
    
    void setObject( final PreparedStatement pst, int col, Object object ) {
        try {
            pst.setObject( col++, object );
            pst.addBatch();
        } catch ( SQLException ex ) {
            Logger.getLogger( DataSourceUseTest.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }
}
