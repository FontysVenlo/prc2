package nl.fontys.genericsanimaldemo;

public class Elephant implements Animal<Elephant>{
    
    public Elephant eat(){
        System.out.println("I am an eating Elephant");
        return this;
    }
    
    public Elephant drink(){
        System.out.println("I am a drinking Elephant");
        return this;
    }    
}
