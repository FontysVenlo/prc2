package simplejdbc;

import java.sql.SQLException;
import javax.sql.DataSource;
import static simplejdbc.PgJDBCUtils.getDataSource;

/**
 *
 * @author Pieter van den Hombergh {@code p.vandehombergh@gmail.com}
 */
public class Main {

    public static void main( String[] args ) throws SQLException {

        DataSource source = getDataSource("jdbc.pg.presidents");
        DataSourceDemo demo = new DataSourceDemo( source, System.out );

        demo.demo();

    }

}
