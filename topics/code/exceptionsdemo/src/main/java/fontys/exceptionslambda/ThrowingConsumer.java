package fontys.exceptionslambda;

/**
 *
 * @author hvd
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}