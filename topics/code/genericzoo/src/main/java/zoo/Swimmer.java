package zoo;

public interface Swimmer<S extends Swimmer<S>> extends Animal<S> {

    default S swim() {
        System.out.println( "peddle peddle" );
        return self();
    }

}
