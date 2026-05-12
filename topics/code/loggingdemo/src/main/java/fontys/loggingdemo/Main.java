/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.loggingdemo;

//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.logging.FileHandler;
//import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hvd
 */
public class Main {

    // generated using logr
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    
    public static void main(String[] args) {

        int x = 3;
        
        System.out.println("x = " + x);
        
        logger.log(Level.INFO, () -> "x = " + x);
        
        logger.log(Level.FINE, () -> "x = " + x);
        
        // generated using own code template -> logv
        logger.log(Level.INFO, () -> "x = " + x);
        
        //logger.setLevel(Level.FINEST); // Does not help, but is also not wat you want in production code
        
        logger.log(Level.INFO,
                () -> String.format("Hello %1$s args length= %2$d ", "world", args.length)
        );
        logger.log(Level.SEVERE,
                () -> "This is a serious logmessage");
        
        logger.log(Level.FINEST,
                () -> "This is the finest logmessage");

//        try {
//            var fileHandler = new FileHandler("demolog.txt");
//            fileHandler.setLevel(Level.FINEST);
//            logger.addHandler(fileHandler);
//
//        } catch (IOException | SecurityException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Log File can't be opened! No file logger attached", ex);
//        }
//
//        logger.log(Level.FINEST,
//                () -> "This is the finest logmessage, should also be logged in file");

//        logger.setLevel(Level.FINEST);  // But don't do this in production code

    }

}
