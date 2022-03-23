package simplejdbc;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 *
 * @author hom
 */
public class DataSourceDemo {

    final DataSource datasource; // <1>
    final PrintStream out;
    public DataSourceDemo( DataSource datasource, PrintStream out ) { // <2>
        this.datasource = datasource;
        this.out = out;
    }

    void demo() throws SQLException {  //<3>
        String query
                = "select state.name as state,p.name as president,state.year_entered \n"
                + " from president p "
                + " join state state on(p.state_id_born=state.id)\n"
                + " where state.name like 'N%'";
        doQuery( query, System.out );

    }

    void doQuery( String query,
                  PrintStream out ) throws SQLException {
        try ( Connection con = datasource.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery( query ) ) {

            new ResultSetPrinter( rs ).printTable( out );

        }
    }
}
