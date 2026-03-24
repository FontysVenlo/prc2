package arraylistdemo;

/**
 *
 * @author hvd
 */
public class Main {
    
    public static void main(String[] args) {
        
        // Using java.util ArrayList, instead of ArrayList in arraylistdemo package
        java.util.ArrayList<String> al;

        
        
        // Using RAW type ... bad practice!
        java.util.ArrayList list = new java.util.ArrayList();
        
        list.add("Hello");
        list.add( new Car());
        
        for (Object object : list) {
            System.out.println("object = " + object);
        }
        
        java.util.ArrayList<String> sl = new java.util.ArrayList();
        // sl.add(new Car());
        
    }
    
}
