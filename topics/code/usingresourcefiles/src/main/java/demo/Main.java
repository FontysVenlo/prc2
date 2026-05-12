package demo;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Richard van den Ham {@code r.vandenham@fontys.nl}
 */
public class Main {

    public static void main(String[] args) {     
        
        // Steps to use resource files:
        // 1 Load Resource bundle, based on Locale
        Locale dutch = Locale.forLanguageTag("nl-NL");
        Locale germany = Locale.GERMANY;
        Locale german = Locale.GERMAN;
        Locale america = Locale.US;
        Locale france = Locale.FRANCE;
        
        Locale chosenLocale = germany;
        
        String baseNamePropertyFiles = "demo.programstrings";
        ResourceBundle bundle = ResourceBundle.getBundle(baseNamePropertyFiles, chosenLocale);
        
        
        // 2 Find key-value pair
        String key = "introductionText";
        String value = bundle.getString(key);
        System.out.println("The value found is: " + value);
        
        
        // 3 In this example case, format the value string
        DateTimeFormatter dateFormatter = 
                DateTimeFormatter.ofLocalizedDate( FormatStyle.FULL ).localizedBy(chosenLocale);
        String formattedDate = dateFormatter.format(LocalDate.of(2026,5,12));
        
        NumberFormat numberformatter = NumberFormat.getCurrencyInstance(chosenLocale);
        String formattedSalary = numberformatter.format(new BigDecimal(250000));
        
        String formattedString = String.format( value, "Alex", formattedDate, formattedSalary);
        System.out.println("formattedString = " + formattedString);
        
        //#####################################################################
        // Applied in the context of this little application
//        System.out.println("\n==== Play with resource files");
//        
//        Person p1 = new Person( "Richard", LocalDate.of( 1985, 1, 1 ), new BigDecimal( 250000 ), Nationality.DUTCH);
//        System.out.println(p1);
//        
//        Person p2 = new Person("Linda", LocalDate.of(1985, 1, 1), new BigDecimal(500000), Nationality.GERMAN);
//        System.out.println(p2);
//        
//        Person p3 = new Person("Donald", LocalDate.of(1946, 6, 14), new BigDecimal(100000), Nationality.AMERICAN);
//        System.out.println(p3);

    }

}
