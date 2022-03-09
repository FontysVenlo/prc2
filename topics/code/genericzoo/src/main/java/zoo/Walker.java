package zoo;

interface Walker<W extends Walker<W>> extends Animal<W> {

    default W walk() {
        System.out.println( "step step" );
        return self();

    }
}
