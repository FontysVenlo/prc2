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
public enum Nationality {
    DUTCH( "nl-NL"), 
    GERMAN( Locale.GERMANY),
    AMERICAN(Locale.US); 
    
    private Locale locale;

    private Nationality( Locale locale ) {
        this.locale = locale;
    }
    
    private Nationality(String lang){
        this( Locale.forLanguageTag(lang));
    }

    public Locale getLocale() {
        return locale;
    }
    
    public String formatDate( LocalDate date){
       
        DateTimeFormatter dateFormatter = 
                DateTimeFormatter.ofLocalizedDate( FormatStyle.FULL ).localizedBy( locale );
        
        return dateFormatter.format( date );
    }
    
    public String formatAmount( BigDecimal amount){
        
        NumberFormat numberformatter = NumberFormat.getCurrencyInstance( locale );
        return numberformatter.format( amount );
    }
    
    ResourceBundle getResourceBundle( String resourceName){
        return ResourceBundle.getBundle( resourceName, locale );
    }
    
    
    
}
