package simplejdbc;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
class StudentDemo {

    final DataSource ds;
    final PrintStream out;

    public StudentDemo( DataSource ds, PrintStream out ) {
        this.ds = ds;
        this.out = out;
    }

    List<Student> listAll() {
        var result = new ArrayList<Student>();
        String query = "select * from students";
        try ( Connection con = ds.getConnection();
                PreparedStatement pst = con.prepareStatement( query );
                ResultSet rs = pst.executeQuery(); ) {
            while ( rs.next() ) {
                Object[] params = new Object[rs.getMetaData().getColumnCount()];
                for ( int i = 0; i < rs.getMetaData().getColumnCount(); i++ ) {
                    Object x=rs.getObject( 1 + i );
                    System.out.println( "x = " + x );
                    params[i] = rs.getObject( 1 + i );

                }
                result.add( Student.fromArray( params ) );
            }
        } catch ( Throwable ex ) {
            Logger.getLogger( StudentDemo.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return result;
//        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

}
