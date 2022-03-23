package simplejdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

/**
 *
 * @author hom
 */
public class PgJDBCUtils {

    static void doQuery( Connection con, String query,
            PrintStream out ) throws SQLException {
        try ( Statement st = con.createStatement();
                ResultSet rs = st.executeQuery( query ) ) {

            new ResultSetPrinter( rs ).printTable( out );

        }
    }

    private static final Map<String, DataSource> datasourceByName = new HashMap<>();

    static DataSource getDataSource( final String sourceName ) {

        return datasourceByName.computeIfAbsent( sourceName,
                ( s ) -> {
                    Properties props = properties( "application.properties" );

                    PGSimpleDataSource source = new PGSimpleDataSource();

                    String prefix = sourceName + ".";
                    String[] serverNames = {
                        props.getProperty( prefix + "dbhost" )
                    };
                    source.setServerNames( serverNames );

                    String user = props.getProperty( prefix + "username" );
                    source.setUser( user );

                    source.setDatabaseName( props.getProperty( prefix + "dbname" ) );
                    source.setPassword( props
                            .getProperty( prefix + "password" ) );
                    String pingQuery = "SELECT current_database(), now()::TIMESTAMP as now;";
                    try ( Connection con = source.getConnection();
                    PreparedStatement pst = con.prepareStatement( pingQuery ); ) {
                        try ( ResultSet rs = pst.executeQuery(); ) {
                            if ( rs.next() ) {
                                Object db = rs.getObject(  "current_database");
                                Object now = rs.getObject(  "now");
                                System.out.println("connected to db "+ db.toString()+ ", date/time is " + now.toString() );
                            }
                        }

                    } catch ( SQLException ex ) {
                        Logger.getLogger( PgJDBCUtils.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                    return source;
                }
        );
    }

    static Properties properties( String propFileName ) {
        Properties properties = new Properties();
        try (
                FileInputStream fis = new FileInputStream( propFileName ); ) {
            properties.load( fis );
        } catch ( IOException ignored ) {
            Logger.getLogger( PgJDBCUtils.class.getName() ).log(
                    Level.INFO,
                    "attempt to read from well file from known location '",
                    ignored );
        }
        return properties;
    }
}
