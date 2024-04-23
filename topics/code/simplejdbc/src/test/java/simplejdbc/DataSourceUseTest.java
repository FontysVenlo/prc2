package simplejdbc;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
@ExtendWith( MockitoExtension.class )
public class DataSourceUseTest {

    //@Disabled("Think TDD")
    @DisplayName("Test that my data source is available for the demo")
    @Test
    public void t1GetDatasource() throws IOException {

        var ds = PgJDBCUtils.getDataSource( "jdbc.pg.dev" );

//        fail( "tGetDataSource completed succesfully; you know what to do" );
    }
}
