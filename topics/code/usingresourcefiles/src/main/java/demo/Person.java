package demo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Richard van den Ham {@code r.vandenham@fontys.nl}
 */
public class Person {

    private final String name;
    private final LocalDate birthDate;
    private final BigDecimal salary;
    private final Nationality nationality;


    /**
     * Create Person object.
     *
     * @param name
     * @param birthDate
     */
    Person( String name, LocalDate birthDate, BigDecimal salary, Nationality nationality ) {
        this.name = name;
        this.birthDate = birthDate;
        this.salary = salary;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Nationality getNationality() {
        return nationality;
    }

    
    
    @Override
    public String toString() {
                
        String date = nationality.formatDate( birthDate );
        String sal = nationality.formatAmount( salary );
        
        String introductionText = nationality.getResourceBundle("demo.programstrings").getString( "introductionText");
        
        //return String.format( "Hi, my name is %1s, I'm born on %2s and I earn %3s", name, date, sal );
        return String.format( introductionText, name, date, sal );

    }

}
