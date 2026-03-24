package nl.fontys.genericsanimaldemo;


// We want to restrict the actual type (as replacement of the formal type 
// parameter A to be only classes that implement the interface, so 
// <A extends Animal<A>> instead of simply <A>
public interface Animal<A extends Animal<A>> {
   
    default A move() {
        System.out.println("I am moving");
        return (A)this;
    }
}
