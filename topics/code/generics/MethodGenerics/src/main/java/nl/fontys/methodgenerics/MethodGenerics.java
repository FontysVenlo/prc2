

package nl.fontys.methodgenerics;

/**
 *
 * @author Richard van den Ham <r.vandenham@fontys.nl>
 */
public class MethodGenerics {

    public static void main(String[] args) {
        
        Student s1 = new Student("Bas");
        Student s2 = new Student("Alex");

        Person chosen = returnRandom(s1, s2);  // Type inference -> Most specific common type of parameters is taken 
        //Person nextChosen = MethodGenerics.<Student>returnRandom(s1, s2);  //Explicit type used
        System.out.println("chosen = " + chosen);
        
    }
    
    static <T> T returnRandom(T t1, T t2){
        return (Math.random() < 0.5 ? t1 : t2);
    }
}
