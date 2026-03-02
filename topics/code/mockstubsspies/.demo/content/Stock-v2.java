package myfirstmock;

/**
 * Stock of a minibar. This is the Dependent-On Component (DOC) we will mock.
 */
public interface Stock {

    /**
     * Take a product from stock.
     * @param product to take
     */
    void take(String product);

    /**
     * Check if a product is available in stock.
     * @param product to check
     * @return true if available
     */
    boolean isAvailable(String product);
}
