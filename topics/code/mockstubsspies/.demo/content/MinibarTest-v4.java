package myfirstmock;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MinibarTest {

    @Test
    void doesItTakeFromStock() {
        Stock stock = mock(Stock.class);
        when(stock.isAvailable("beer")).thenReturn(true);
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
        when(stock.isAvailable("beer")).thenReturn(true);
        bar.serve("beer");
        verify(stock).take("beer");
    }

    @Test
    void refusesWhenOutOfStock() {
        when(stock.isAvailable("beer")).thenReturn(false);
        assertThatThrownBy(() -> bar.serve("beer"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("out of stock");
    }

    @Test
    void spyCallsRealMethods() {
        SimpleStock realStock = new SimpleStock();
        realStock.add("beer");
        Stock spied = spy(realStock);

        Minibar bar = new Minibar(spied);
        bar.serve("beer");

        // real method executed — beer was removed
        assertThat(realStock.isAvailable("beer")).isFalse();
        // but we can still verify like a mock
        verify(spied).take("beer");
    }
}
