package fontys.exceptionslambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author hvd Example from : https://www.baeldung.com/java-lambda-exceptions
 *
 */
public class MainUnchecked {

    public static void main(String[] args) {

        // Example 1
//        List<Integer> integers = Arrays.asList(3, 9, 7, 6, 3, 10, 20);
//        integers.forEach(i -> System.out.println(50 / i));

//        // Example 2
//        List<Integer> integers = Arrays.asList(3, 9, 7, 6, 0, 10, 20);
//        integers.forEach(i -> System.out.println(50 / i));
//
//        // Example 3
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(i -> {
//            try {
//                System.out.println(50 / i);
//            } catch (ArithmeticException e) {
//                System.err.println(
//                        "Arithmetic Exception occured : " + e.getMessage());
//            }
//        });
//
//        // Example 4
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(lambdaWrapper(i -> System.out.println(50 / i)));
//
//        // Example 5
//        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
//        integers.forEach(
//                consumerWrapper(
//                        i -> System.out.println(50 / i),
//                        ArithmeticException.class));
//        
//        // Example 5 Usage outside of forEach
//        consumerWrapper( (Integer i) -> System.out.println(i/3), ArithmeticException.class).accept(15);
        
        // Or don't infer type, but make types explicit (not necessary and also bad practice)
        //MainUnchecked.<Integer, ArithmeticException>consumerWrapper( i -> System.out.println(i/3), ArithmeticException.class).accept(15);

    }

    // Belonging to example 4
    static Consumer<Integer> lambdaWrapper(Consumer<Integer> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (ArithmeticException e) {
                System.err.println(
                        "Arithmetic Exception occured : " + e.getMessage());
            }
        };
    }

    // Belonging to example 5 
    static <T, E extends Exception> Consumer<T>
            consumerWrapper(Consumer<T> consumer, Class<E> clazz) {

        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw ex;
                }
            }
        };
    }

}
