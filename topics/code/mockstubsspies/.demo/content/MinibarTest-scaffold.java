package myfirstmock;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MinibarTest {

    @Test
    void placeholder() {
        assertThat(1).isEqualTo(1);
    }
}
