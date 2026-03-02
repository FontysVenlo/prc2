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
}
