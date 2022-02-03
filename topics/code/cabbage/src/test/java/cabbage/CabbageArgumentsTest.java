package cabbage;

import static cabbage.Cabbage.Odour.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class CabbageArgumentsTest {

    static final Cabbage SPROUT = new Cabbage( "Brussel Sprouts", 10, 0.2,
                                               STRONG );
    static final Cabbage CAULIFLOWER = new Cabbage( "Cauliflower", 1000, 1.5,
                                                    DISTINCT );
    static final Cabbage KALE = new Cabbage( "Kale", 500, 0.700, WEAK );
    static final Cabbage WHITE = new Cabbage( "White Cabbage", 1200, 1.2,
                                              DISTINCT );
    static final Cabbage CABBAGE = new Cabbage( "Cabbage", 600, 0.480, STRONG );
    static final Cabbage SAVOY = new Cabbage( "Savoy Cabbage", 800, 1.8,
                                              VERY_STRONG );

    static Map<String, Comparator<Cabbage>> comparators
            = Map.of(
                    "byStink", ( a, b ) -> a.compareTo( b ),
                    "byWeight", ( a, b ) -> Double.compare( a.getWeight(), b
                                                            .getWeight() ),
                    "byWeightAlt", Comparator.comparing( Cabbage::getWeight ),
                    "byWeightNat", Cabbage::compareTo, // 'natural' by weight
                    "byVolume", Comparator.comparing( Cabbage::getVolume ),
                    "byWeightNAt2", Comparator.naturalOrder()
            );

    /**
     * Test data table make sure the arguments have the same order as those in
     * the test method.
     */
    static Object[][] testData
            = { // msg, lambda name, result, cab a, cab b
                { "stinks worse", "byStink", 1, SPROUT, CAULIFLOWER },
                { "stinks equal", "byStink", 0, WHITE, CAULIFLOWER },
                { "stinks  less", "byStink", -1, CABBAGE, SAVOY }
            // other left out
            };

    /**
     * There is only one test, run multiple times, once for each row of test
     * data.
     *
     * @param myData test datum
     */
    @Disabled
    @ParameterizedTest
    @ArgumentsSource( TestData.class )
    void testMethod( String message, String comparatorName, int expected,
                     Cabbage a, Cabbage b ) {

        Comparator<Cabbage> comp = comparators.get( comparatorName );

        assertThat( Integer.signum( comp.compare( a, b ) ) )
                .as( message )
                .isEqualTo( expected );
    }

    /**
     * Helper class to pass array data as Arguments Object.
     */
    static class TestData implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments( ExtensionContext ec )
                throws Exception {
            return Arrays.stream( testData ).map( Arguments::of );
        }
    }

}
