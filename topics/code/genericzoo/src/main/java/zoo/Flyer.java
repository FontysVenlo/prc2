package zoo;

public interface Flyer<F extends Flyer<F>> extends Animal<F> {

    default F fly() {
        System.out.println( "swoosh" );
        return self();
    }
}
