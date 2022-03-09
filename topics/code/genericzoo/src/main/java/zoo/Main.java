package zoo;

public class Main {

    public static void main( String[] args ) {
        Bird pino = new Bird();

        pino.fly().walk().fly()
                .move();

        Duck donald = new Duck();

        Duck stillDonald = donald.fly()
                .swim()
                .walk()
                .move();

        Penguin tux = new Penguin().walk().swim().walk();
        
    }
}
