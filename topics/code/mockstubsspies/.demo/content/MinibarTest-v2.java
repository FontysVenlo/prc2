package myfirstmock;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MinibarTest {

    @Test
    void doesItTakeFromStock() {
        Stock stock = mock(Stock.class);
        Minibar bar = new Minibar(stock);
        bar.serve("beer");
        verify(stock).take(anyString());
    }

    @Mock
    Stock stock;

    Minibar bar;

    @BeforeEach
    void setup() {
        bar = new Minibar(stock);
    }

    @Test
    void doesItServeTheDrinkOrdered() {
        bar.serve("beer");
        verify(stock).take("beer");
    }
}
