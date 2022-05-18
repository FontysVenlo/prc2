package exex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TryScanner {

    public static void main( String[] args ) {
        File f = new File( "data.csv" );
        String z = "";
        try ( Scanner input = new Scanner( f ); ) {
            while ( input.hasNext() ) {
                try {
                    z = input.next();
                    int number = Integer.parseInt( z );
                    System.out.println( "number = " + number );
                } catch ( NumberFormatException nfe ) {
                    Logger.getLogger( TryScanner.class.getName() ).log( 
                            Level.SEVERE,
                            ( "" + nfe.getMessage() + " is not a number" ), nfe );
                }
            }
        } catch ( FileNotFoundException ex ) {
            Logger.getLogger( TryScanner.class.getName() ).log( Level.SEVERE,
                    null, ex );
        }

    }

    void method() throws FileNotFoundException {
        try(Scanner resource=new Scanner(new File("test.txt"))){
            
        }
    }
}
