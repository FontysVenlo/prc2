/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package simplejdbc;

import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
/**
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
public class PgConfigTest {

    public PgConfigTest() {
    }
//@Disabled("think TDD")

    @Test
    public void testConfig() {

        PgConfig cfg = PgConfig.getConfigForPrefix( "jdbc.pg.dev" );
        cfg = PgConfig.getConfigForPrefix( "jdbc.pg.dev" );

        assertThat( cfg.dbname() ).isNotNull();
        assertThat( cfg.user() ).isNotNull();
        assertThat( cfg.ports() ).isNotNull();
        assertThat( cfg.password() ).isNotNull();
//        fail( "method method reached end. You know what to do." );
    }

    @Test
    public void testConfigPres() {

        PgConfig cfg = PgConfig.getConfigForPrefix( "jdbc.pg.presidents" );
        cfg = PgConfig.getConfigForPrefix( "jdbc.pg.presidents" );

        assertThat( cfg.dbname() ).isNotNull();
        assertThat( cfg.user() ).isNotNull();
        assertThat( cfg.ports() ).isNotNull();
        assertThat( cfg.password() ).isNotNull();
//        fail( "method method reached end. You know what to do." );
    }

    @Test
    public void testConfigProd() {

        PgConfig cfg = PgConfig.getConfigForPrefix( "jdbc.pg.prod" );

        assertThat( cfg.dbname() ).isNotNull();
        assertThat( cfg.user() ).isNotNull();
        assertThat( cfg.ports() ).isNotNull();
        assertThat( cfg.password() ).isNotNull();
//        fail( "method method reached end. You know what to do." );
    }

    
    //@Disabled("think TDD")
    @Test
    @DisplayName("when a configuration parameter is not found, crash")
    public void ifPrefixNotFoundCrash() {
        
        ThrowingCallable code =()->{
            PgConfig.getConfigForPrefix( "nix");
        };
        assertThatCode(code ).isExactlyInstanceOf( RuntimeException.class).hasMessageContaining( "not found");
//        fail( "method ifPrefixNotFoundCrash reached end. You know what to do." );
    }
}
