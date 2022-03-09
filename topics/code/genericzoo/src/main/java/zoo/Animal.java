package zoo;

public abstract interface Animal<A extends Animal<A>> {

    default A move() {
        System.out.println( "twitch " );
        return self();
    }

    default A brood() {
        System.out.println( "cosy with my offspring " );
        return self();
    }

    // makes object self aware.
    default A self() {
        return (A) this;
    }

}
