package myfirstmock;

public class Minibar {

    private final Stock stock;

    Minibar(Stock stock) {
        this.stock = stock;
    }

    void serve(String drink) {
        stock.take(drink);
    }
}
