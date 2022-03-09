package zoo;

public interface EBird<EB extends EBird<EB>>  extends Flyer<EB>, Walker<EB>{

    default EB afterBurner() {
        System.out.println( "VROOOAAR" );
        return self();
    }
}
