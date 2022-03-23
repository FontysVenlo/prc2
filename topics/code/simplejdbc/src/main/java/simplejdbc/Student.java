package simplejdbc;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Simple student with LocalDate birthday.
 *
 * @author Pieter van den Hombergh {@code pieter.van.den.hombergh@gmail.com}
 */
public class Student implements Serializable {

    private Integer snummer;
    private String firstname;
    private String lastname;
    private LocalDate dob;
    private int cohort;
    private String email;
    private String gender;
    private String student_class;
    private boolean active;

    public Student( Integer snummer, String firstname, String lastname, LocalDate dob, int cohort, String email, String gender, String student_class, boolean active ) {
        this.snummer = snummer;
        this.lastname = lastname;
        this.firstname = firstname;
        this.dob = dob;
        this.cohort = cohort;
        this.email = email;
        this.gender = gender;
        this.student_class = student_class;
        this.active = active;
    }

    public Integer getSnummer() {
        return snummer;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public int getCohort() {
        return cohort;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getStudent_class() {
        return student_class;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Student{" + "snummer=" + snummer + ", lastname=" + lastname + ", firstname=" + firstname + ", dob=" + dob + ", cohort=" + cohort + ", email=" + email + ", gender=" + gender + ", student_class=" + student_class + ", active=" + active + '}';
    }

    String csvRecord() {
        return "" + snummer + "," + lastname + "," //+ tussenvoegsel + ","
                + firstname + "," + dob + "," + cohort + "," + email + ","
                + gender + "," + student_class;

    }

    static Student fromArray( String[] params ) {
        if ( params.length < 9 ) {
            return null;
        }
        return new Student(
                Integer.parseInt( params[0].trim() ),
                params[1].trim(),
                params[2].trim(),
                LocalDate.parse( params[3].trim() ),
                Integer.parseInt( params[4].trim() ),
                params[5].trim(),
                params[6].trim(),
                params[7].trim(),
                Boolean.parseBoolean( params[8] )
        );
    }

    static Student fromArray( Object[] params ) {
        if ( params.length < 9 ) {
            return null;
        }
        Student result=
        new Student(
                (Integer) params[0],
                (String) params[1],
                (String) params[2],
                (LocalDate) params[3],
                (Integer) params[4],
                (String) params[5],
                (String) params[6],
                (String) params[7],
                (Boolean) params[8] );
        System.out.println( "result = " + result );
        return result;
    }

    Object[] asArray() {
        return new Object[]{
            snummer, firstname, lastname, dob, cohort, email, gender, student_class, active};
    }
}
