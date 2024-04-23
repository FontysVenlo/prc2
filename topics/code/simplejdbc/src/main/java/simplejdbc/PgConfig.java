package simplejdbc;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import static simplejdbc.PgJDBCUtils.properties;

/**
 * Simple configuration record, containing hosts, ports, database name, user and
 * password.
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
public record PgConfig(String[] hosts, int[] ports, String dbname, String user, String password) {

    /**
     * Ensure all required parameters are set.
     * @param hosts hosts for the data source
     * @param ports used
     * @param dbname sic
     * @param user username
     * @param password sic
     */
    public PgConfig     {
        Objects.requireNonNull( hosts );
        Objects.requireNonNull( ports );
        Objects.requireNonNull( dbname );
        Objects.requireNonNull( user );
        Objects.requireNonNull( password );
    }

    @Override
    public String toString() {
        return "PGConfiguration{"
                + "\n\thosts = " + Arrays.toString( hosts )
                + "\n\tports = " + Arrays.toString( ports )
                + "\n\tdbname = " + dbname
                + "\n\tuser = " + user
                + "\n}";
    }

    /**
     * Create the configuration from a well known properties file
     * application.properties.
     *
     * @param prefix of the data source, e.g. jdbc.pg.dev.
     * @return the configuration data as record
     */
    public static PgConfig getConfigForPrefix(String prefix) {
        Properties props = properties( "application.properties" );
        System.out.println( "prefix = " + prefix );
        final int substringstart = prefix.endsWith( "." ) ? prefix.length() : prefix.length() + 1;
        Map.Entry[] entries = props.entrySet().stream()
                .filter( e -> ( (String) e.getKey() ).startsWith( prefix ) )
                .map( e -> trimEntry( substringstart, e ) )
                .toArray( Map.Entry[]::new );

        if ( entries.length == 0 ) {
            throw new RuntimeException( "properties with prefix '" + prefix + "' not found" );
        }
        Map<String, String> map = Map.ofEntries( entries );
        var ports = Arrays.stream( map.getOrDefault( "ports", "5432" ).split( "\\s?,\\s?" ) )
                .mapToInt( Integer::valueOf )
                .toArray();
        var hosts = Arrays.stream( map.getOrDefault( "hosts", "localhost" ).split( "\\s?,\\s?" ) )
                .toArray( String[]::new );
        return new PgConfig(
                hosts,
                ports,
                map.get( "dbname" ),
                map.get( "user" ),
                map.get( "password" )
        );
    }

    static Map.Entry<String, String> trimEntry(int trimLength, Map.Entry<Object, Object> e) {
        return Map.entry( ( (String) e.getKey() ).substring( trimLength ), (String) e.getValue() );
    }
}
