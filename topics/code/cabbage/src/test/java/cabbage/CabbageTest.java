package cabbage;

import static cabbage.Cabbage.Odour.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author insert your name here.
 */
public class CabbageTest {

    final static Cabbage SPROUT = new Cabbage( "Brussel Sprouts", 10, 0.2, STRONG );
    final static Cabbage CAULIFLOWER = new Cabbage( "Cauliflower", 1000, 1.5, DISTINCT );
    final static Cabbage KALE = new Cabbage( "Kale", 500, 0.700, WEAK );
    final static Cabbage WHITE = new Cabbage( "White Cabbage", 1200, 1.2, DISTINCT );
    final static Cabbage CABBAGE = new Cabbage( "Cabbage", 600, 0.480, STRONG );
    final static Cabbage SAVOY = new Cabbage( "Savoy Cabbage", 800, 1.8, VERY_STRONG );

    static Map<String, Comparator<Cabbage>> comparators = Map.of(
            "byStink", ( a, b ) -> a.compareTo( b ),
            "byWeight", ( a, b ) -> Double.compare( a.getWeight(), b.getWeight() ),
            "byWeightAlt", Comparator.comparing( Cabbage::getWeight ),
            "byVolume", Comparator.comparing( Cabbage::getVolume )
    );

    final static List<TD> MY_TEST_DATA
            = List.of( td( "stinks worse", "byStink", 1, SPROUT, CAULIFLOWER ),
                    td( "stinks equal", "byStink", 0, WHITE, CAULIFLOWER ),
                    td( "stinks  less", "byStink", -1, CABBAGE, SAVOY )
            //  td("test 2", (a,b)-> Integer.signum(a.weight-b.weight), 1,CAULIFLOWER,KALE)
            );

    /**
     * There is only one test, that uses one table of TD (TestData) instance.
     *
     * @param myData test datum
     */
    @ParameterizedTest
    @MethodSource( "testData" )
    void testMethod( final TD myData ) {

        Comparator<Cabbage> comp = comparators.get( myData.compatorName );
        assertThat( Integer.signum( comp.compare( myData.a, myData.b ) ) )
                .as( myData.message )
                .isEqualTo( myData.expected );
    }

    /**
     * Produces the test data.
     *
     * @return stream
     */
    public static Stream<TD> testData() {
        return MY_TEST_DATA.stream();
    }

    /**
     * The class that defines a test data element.
     */
    static class TD {

        final String message;
        final String compatorName;
        final int expected;
        final Cabbage a;
        final Cabbage b;

        public TD( String message, String comparator, int expected, Cabbage a, Cabbage b ) {
            this.message = message;
            this.compatorName = comparator;
            this.expected = expected;
            this.a = a;
            this.b = b;
        }

    }

    static TD td( String message, String compator, int expected, Cabbage a, Cabbage b ) {
        return new TD( message, compator, expected, a, b );
    }
    
    //@Disabled("Think TDD")
    @Test
    public void tEqualsAndHashCode() {
        
        Cabbage cab1 = 
        
        fail( "tEqualsAndHashCode completed succesfully; you know what to do" );
    }
    
}
