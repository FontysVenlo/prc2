package fontys.exceptionslambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hvd
 */
public class CheckedUncheckedDemo {

    public static void main(String[] args) {

        CheckedUncheckedDemo demo = new CheckedUncheckedDemo();
        
        try {
            demo.printFileContents("testdqad.txt");
        } catch (IOException ex) {
            System.out.println("File not found");
        }
    }

    void printFileContents(String fileName) throws IOException {

        try {
            Path filePath = Path.of(fileName);
            Files.lines(filePath).forEach(l -> System.out.println(l));
        } catch (IOException ex) {
            System.getLogger(CheckedUncheckedDemo.class.getName()).log(System.Logger.Level.OFF, (String) null, ex);
            throw ex;
        }
    }

}
