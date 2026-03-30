package fontys.customerdbdemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

/**
 *
 * @author hvd
 */
public class DBProvider {

    static Map<String, DataSource> cache = new HashMap<>();

    static DataSource getDataSource(final String sourceName) {

        return cache.computeIfAbsent(sourceName,
                (s) -> {
                    Properties props = getProperties("db.properties");

                    PGSimpleDataSource dataSource = new PGSimpleDataSource();

                    String prefix = sourceName + ".";

                    String[] serverNames = {props.getProperty(prefix + "dbhost")};
                    dataSource.setServerNames(serverNames);

                    int[] portNumbers = {Integer.parseInt(props.getProperty(prefix + "port"))};
                    dataSource.setPortNumbers(portNumbers);

                    dataSource.setUser(props.getProperty(prefix + "username"));
                    dataSource.setDatabaseName(props.getProperty(prefix + "dbname"));
                    dataSource.setPassword(props.getProperty(prefix + "password"));
                    dataSource.setCurrentSchema(props.getProperty(prefix + "schema"));

                    return dataSource;
                }
        );
    }

    static Properties getProperties(String propertiesFileName){
        
        System.out.println("The user working dir is " + System.getProperty("user.dir"));
        
        // Usage of resource file is preferred way. No issues with working dir.
        // Uses the default location of resources (in src/main/java/resources dir)
        // getClassLoader() is necessary, unless you store your proprty file in a 
        // subfolder according to package name 
        // (src/main/resources/fontys/customerdbdemo in this case).
        
        Properties properties = new Properties();
        try (InputStream dbProperties = DBProvider.class.getClassLoader().getResourceAsStream(propertiesFileName);) {
            properties.load(dbProperties);
        } catch (IOException ex) {
            Logger.getLogger(DBProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}