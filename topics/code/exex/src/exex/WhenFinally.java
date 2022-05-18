package exex;

/**
 * Show when the finally block is called
 *
 * @author hom
 */
public class WhenFinally {

    public static void main( String[] args ) {
        try {
            caller();
        } catch ( Exception ex ) {
            System.out.println(ex.getMessage() + " in main" );
        }

    }

    static void caller() throws Exception {
        try {
            System.out.println( "in caller" );
            thrower();
        } finally {
            System.out.println( "this must be done always" );
        }

    }

    public static void thrower() throws Exception {
        System.out.println( "in thower" );
        throw new RuntimeException( "Howdy, did I get your attention" );
    }
}
