package fontys.customerdbdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author hvd
 */
public class Main {
    
    public static void main(String[] args) {

        // This example assumes:
        // A Postgres database running, with a view named 'customersview'.
        // Have a look at CustomerDemo.sql in the root of this project.
        
        example1_statement();
        
        example2_SQLInjection();
        
        example3_selectWithPreparedStatement();
        
        example4_insertWithPreparedStatement();
    }
    
    public static void example1_statement(){
        
        DataSource db = DBProvider.getDataSource("aisdb");
        
        System.out.println("\nExample 1 - Read data from database using Statement\n");

        try ( Connection con = db.getConnection();   // Try-with-resource: con and stm are AutoClosable 
              Statement stm = con.createStatement();) {

            ResultSet result = stm.executeQuery("SELECT * FROM customersview");

            while (result.next()) {
                int id = result.getInt(1);
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                LocalDate dob = result.getObject("dob", LocalDate.class);

                System.out.println("Customer with id: " + id + ", " + firstName + ", " + lastName + ", " + dob);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void example2_SQLInjection(){
        
        DataSource db = DBProvider.getDataSource("aisdb");
        
        System.out.println("\nExample 2 - Vulnerability of using Statement - SQL Injection\n");

        // Assume that a user can enter a firstname in the GUI
        // If the user does what we expect, then it will indeed be a regular string:
        
        //String filter = "Richard";
        
        // But if the user enters:  ' or firstname like '%%    like below...
         String filter = "' or firstname like '%%";
        // String filter = "' UNION select 1, viewowner, null, null from pg_views where viewowner like '%";
        
        // The resulting query will look like this:
        // SELECT * FROM CUSTOMERS WHERE firstname = '' or firstname like '%%'
        // Which returns all records in the database...
        
        // Or someone could inject sql code to drop parts of your database...
        
        String query = "SELECT * FROM customersview WHERE firstname = '" + filter + "'";

        // The free format of the filter String makes you sensible for SQL injection!!
        
        try ( Connection con = db.getConnection();  
              Statement stm = con.createStatement()) {

            ResultSet result = stm.executeQuery(query);

            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                LocalDate dob = result.getObject("dob", LocalDate.class);

                System.out.println("Customer with id: " + id + ", " + firstName + ", " + lastName + ", " + dob);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void example3_selectWithPreparedStatement(){
        
        DataSource db = DBProvider.getDataSource("aisdb");
        
        System.out.println("\nExample 3 - Improve performance and avoid SQL Injections -> Use PreparedStatement\n");

        
        String filter = "Richard";
        //String filter = "' or firstname like '%%";  //With a prepared statement, this injection has no chance anymore
        String query = "SELECT * FROM customersview WHERE firstname = ?";

        // The question mark needs to be replaced by a value of a certain type
        // The DBMS knows that the replacement of the ? should be ONE value
        // and the complete parameter is interpreted as text.

        try ( Connection con = db.getConnection();  
              PreparedStatement pstm = con.prepareStatement(query)) {

            // In the line above, we now use a PreparedStatement instead of a Statement. 
         
            pstm.setString(1, filter);    // We tell the prepared statement that the value of 'filter' is a String;
                                          // As a result, the content is considered as a String value that is connected to
                                          // the first question mark in your query (so as a filter on firstname only!)
                                          
            ResultSet result = pstm.executeQuery(); // No parameter needed anymore, since statement is prepared and set already!

            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                LocalDate dob = result.getObject("dob", LocalDate.class);

                System.out.println("Customer with id: " + id + ", " + firstName + ", " + lastName + ", " + dob);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void example4_insertWithPreparedStatement(){
        
        DataSource db = DBProvider.getDataSource("aisdb");
        
        System.out.println("\nExample 4 - Insert a new record using prepared statement\n");

        String fname = "Lisa";
        String lname = "Anderson";
        LocalDate dob = LocalDate.of(2000, 1, 1);

        String query = "INSERT INTO customersview (firstname, lastname, dob) values (?, ?, ?) returning *";

        // returning *   at the end of the sql statement makes, even in case of an insert
        // or update or delete statement, that a ResultSet is returned. Normally, such a query
        // only returns the number of effected rows. Now, it returns the inserted, updated, or
        // deleted rows respectively in a ResultSet. Especially in the insert case, this is convenient
        // to also get the generated key back.
        // Programming against views is good practice. Complex views might not always be updatable however.
        // Be aware that you can still do 'things' at the database side in stored procedures when necessary.
        // As the book also states, if you can do it at the database-level then DO IT at the database
        // level.
        try ( Connection con = db.getConnection();  
              PreparedStatement pstm = con.prepareStatement(query)) {
            
            pstm.setString(1, fname);
            pstm.setString(2, lname);
            pstm.setDate(3, java.sql.Date.valueOf(dob));

            ResultSet result = pstm.executeQuery();

            System.out.println("JUST INSERTED: ");
            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                LocalDate dobres = result.getObject("dob", LocalDate.class);

                System.out.println("Customer with id: " + id + ", " + firstName + ", " + lastName + ", " + dobres);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
