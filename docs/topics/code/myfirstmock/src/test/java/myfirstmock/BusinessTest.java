package myfirstmock;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
@ExtendWith( MockitoExtension.class )
public class BusinessTest {

    /**
     * Let us see the busines use the printer
     */
    //@Disabled
    @Test
    public void doesItPrint() {
        Printer pr = mock( Printer.class );  //<1>

        Business b = new Business( pr );     //<2>

        b.work();                                 //<3>

        verify( pr ).println( anyString() ); //<4>

    }

    @Mock
    Printer printer; // <1>

    Business business;

    @BeforeEach
    void setup() {
        business = new Business( printer );  // <2>
    }

    @Test
    void doesItPrintBusiness() {
        ArgumentCaptor<String> lineCaptor = ArgumentCaptor.forClass( String.class ); //<3>

        business.work( "Linda" ); // <4>

        verify( printer ).println( lineCaptor.capture() ); //<5>
        assertThat( lineCaptor.getAllValues() ).contains( "Hello Linda" ); // <6>
        fail("test does It print ended. You know what to do.");
    }
}
