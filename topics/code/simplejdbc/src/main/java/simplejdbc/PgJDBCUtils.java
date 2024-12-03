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
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * Some helpers for common tasks.
 *
 * @author hom
 */
public class PgJDBCUtils {

    public static void doQuery(Connection con, String query,
            PrintStream out) throws SQLException {
        try ( Statement st = con.createStatement(); ResultSet rs = st.executeQuery( query ) ) {

            new ResultSetPrinter( rs ).printTable( out );

        }
    }

    private static final Map<String, DataSource> datasourceByName = new HashMap<>();
    private static Logger LOG = Logger.getLogger( PgJDBCUtils.class.getName() );

    public static DataSource getDataSource(final String sourceName) {

        LOG.fine( () -> " get DataSource sourceName = " + sourceName );
        return datasourceByName.computeIfAbsent( sourceName, PgJDBCUtils::configureAndPing );
    }

    /**
     * Make sure that the data source is reachable.
     *
     * @param sourceName name of the resource
     * @return a configureddatasource.
     */
    static DataSource configureAndPing(String sourceName) {

        PgConfig cfg = PgConfig.getConfigForPrefix( sourceName );
        PGSimpleDataSource source = new PGSimpleDataSource();
        source = configureSource( source, cfg );
        String databaseName = source.getDatabaseName();
        String pingQuery = "SELECT current_database(), now()::TIMESTAMP as now;";
        try ( Connection con = source.getConnection(); PreparedStatement pst = con.prepareStatement( pingQuery ); ) {
            try ( ResultSet rs = pst.executeQuery(); ) {
                if ( rs.next() ) {
                    Object db = rs.getObject( "current_database" );
                    Object now = rs.getObject( "now" );
                    LOG.info( () -> "connected to db %s, date/time is %s ".formatted(
                            db.toString(), now.toString() ) );
                }
            }
        } catch ( SQLException ex ) {
            LOG.severe( () -> "cannot connect to " + databaseName + "with configuration " + cfg );
        }
        return source;

    }

    private static PGSimpleDataSource configureSource(PGSimpleDataSource source, PgConfig cfg) {
        source.setServerNames( cfg.hosts() );
        source.setPortNumbers( cfg.ports() );
        source.setUser( cfg.user() );
        source.setDatabaseName( cfg.dbname() );
        source.setPassword( cfg.password() );
        return source;
    }

    static Properties properties(String propFileName) {
        Properties properties = new Properties();
        try (
                FileInputStream fis = new FileInputStream( propFileName ); ) {
            properties.load( fis );
        } catch ( IOException ignored ) {
            LOG.info( ()
                    -> "attempt to read from well known file at well known location failed "
                    + ignored.getMessage() );
        }
        return properties;
    }
}
