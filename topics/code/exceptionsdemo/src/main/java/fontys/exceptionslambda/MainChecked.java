package fontys.exceptionslambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Example from : https://www.baeldung.com/java-lambda-exceptions
 *
 */
public class MainChecked {
//
//    // Example 1
//    public static void main(String[] args){
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(i -> {
//            try {
//                writeToFile(i);
//            } catch (IOException ex) {
//                Logger.getLogger(MainChecked.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//    }
//
    // Other solution? Not a solution... which method declaration should throw IOException?
//    public static void main(String[] args) throws IOException {
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(i -> writeToFile(i));
//    }

//    public static void main(String[] args) throws Exception {
//        ThrowingConsumer<Integer, Exception> tc = i -> writeToFile(i);
//        tc.accept(15);
//    }
    
//    // Solution 2: Rethrow - convert to RunTimeException
//    // ... But lambda looks messy
//    public static void main(String[] args) {
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(i -> {
//            try {
//                writeToFile(i);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
  
    // Solution 3: Rethrow - convert to RunTimeException in wrapper method that expects ThrowingConsumer
//    public static void main(String[] args) {
//
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(throwingConsumerWrapper(i -> writeToFile(i)));
//
//    }
    
    // Solution 4: Handling specific Checked Exception
//    public static void main(String[] args) {
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(handlingConsumerWrapper(
//                i -> writeToFile(i), IOException.class));
//    }

    static void writeToFile(Integer integer) throws IOException {
        // logic to write to file which throws IOException
        System.out.println("Writing to file: " + integer);
        
        //throw new IOException("I can't do it :-(");
        //throw new RuntimeException("I can't do it RunTimeException");
    }

    // Belonging to solution 3
    static <T> Consumer<T> throwingConsumerWrapper(
            ThrowingConsumer<T, Exception> throwingConsumer) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    // Belonging to solution 4
    static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}