package zoo;

/**
 *
 * @author Pieter van den Hombergh {@code p.vandehombergh@gmail.com}
 */
public class ThunderBird implements EBird<ThunderBird>{
    
    public static void main( String[] args ) {
        ThunderBird b= new ThunderBird();
        
        b.fly().move().walk().fly().brood().fly().afterBurner();
    }
}
