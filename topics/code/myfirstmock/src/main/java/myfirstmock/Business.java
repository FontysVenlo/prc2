/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myfirstmock;

/**
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class Business {

    private final Printer printer;

    Business( Printer printer ) {
        this.printer = printer;
    }

    void work() {
        printer.println( "Hello");
    }

    /**
     * Do some work with input.
     * @param input to work with
     */
    void work( String input ) {
        printer.println( "Hello "+input);
    }

}
