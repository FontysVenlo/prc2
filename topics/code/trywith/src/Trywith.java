
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class Trywith {

    public static void main( String[] args ) throws IOException {

        try (
          File f = new File( "data.txt" );
          Scanner scanner = new Scanner(f); //<1>
        ) {
          String s1 = scanner.next();
        } catch ( FileNotFoundException fnfe ) {
            // deal with exceptions.
        } //<2>
    }

    interface Ikk{
        private static void hello(){
            System.out.println( "Hello" );
        }
    }
}
