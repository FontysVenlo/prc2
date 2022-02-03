/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cabbage;

import org.assertj.core.api.SoftAssertions;

/**
 *
 * @author Pieter van den Hombergh {@code p.vandehombergh@gmail.com}
 */
public class TestUtils {

    /**
     * Helper for equals tests, which are tedious to get completely covered.
     *
     * @param <T> type of class to test
     * @param ref reference value
     * @param equal one that should test equals true
     * @param unEqual list of elements that should test unequal in all cases.
     */
    public static <T> void verifyEqualsAndHashCode( T ref, T equal, T... unEqual ) {
        Object object = "Hello";
        T tnull = null;
        String cname = ref.getClass().getCanonicalName();
        // I got bitten here, assertJ equalTo does not invoke equals on the
        // object when ref and 'other' are same.
        // THAT's why the first one differs from the rest.
        SoftAssertions.assertSoftly( softly -> {
            softly.assertThat( ref.equals( ref ) )
                    .as( cname + ".equals(this): with self should produce true" )
                    .isTrue();
            softly.assertThat( ref.equals( tnull ) )
                    .as( cname + ".equals(null): ref object "
                         + safeToString( ref ) + " and null should produce false"
                    )
                    .isFalse();
            softly.assertThat( ref.equals( object ) )
                    .as( cname + ".equals(new Object()): ref object"
                         + " compared to other type should produce false"
                    )
                    .isFalse();
            softly.assertThat( ref.equals( equal ) )
                    .as( cname + " ref object [" + safeToString( ref )
                         + "] and equal object [" + safeToString( equal )
                         + "] should report equal"
                    )
                    .isTrue();
            for ( int i = 0; i < unEqual.length; i++ ) {
                T ueq = unEqual[ i ];
                softly.assertThat( ref )
                        .as( "testing supposed unequal objects" )
                        .isNotEqualTo( ueq );
            }
            // ref and equal should have same hashCode
            softly.assertThat( ref.hashCode() )
                    .as( cname + " equal objects "
                         + ref.toString() + " and "
                         + equal.toString() + " should have same hashcode"
                    )
                    .isEqualTo( equal.hashCode() );
        } );
    }

    /**
     * ToString that deals with any exceptions that are thrown during its
     * invocation.
     *
     * When x.toString triggers an exception, the returned string contains a
     * message with this information plus class and system hashCode of the
     * object.
     *
     * @param x to turn into string or a meaningful message.
     * @return "null" when x==null, x.toString() when not.
     */
    public static String safeToString( Object x ) {
        if ( x == null ) {
            return "null";
        }
        try {
            return x.toString();
        } catch ( Throwable e ) {
            return "invoking toString on instance "
                   + x.getClass().getCanonicalName() + "@"
                   + Integer.toHexString( System.identityHashCode( x ) )
                   + " causes an exception " + e.toString();
        }
    }
}
