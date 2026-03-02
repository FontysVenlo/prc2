package myfirstmock;

public class Minibar {

    private final Stock stock;

    Minibar(Stock stock) {
        this.stock = stock;
    }

    void serve(String drink) {
        if (!stock.isAvailable(drink)) {
            throw new IllegalStateException(drink + " is out of stock");
        }
        stock.take(drink);
    }
}
