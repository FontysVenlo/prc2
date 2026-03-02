package myfirstmock;

import java.util.ArrayList;
import java.util.List;

public class SimpleStock implements Stock {

    private final List<String> products = new ArrayList<>();

    public void add(String product) {
        products.add(product);
    }

    @Override
    public void take(String product) {
        products.remove(product);
    }

    @Override
    public boolean isAvailable(String product) {
        return products.contains(product);
    }
}
