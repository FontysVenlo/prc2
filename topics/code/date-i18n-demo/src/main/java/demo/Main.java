package demo;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;

/**
 *
 * @author Richard van den Ham {@code r.vandenham@fontys.nl}
 */
public class Main {

    public static void main(String[] args) {

        // Play with Instants
        System.out.println("==== Play with Instant");
        Instant nowInstant = Instant.now();
        System.out.println("nowInstant = " + nowInstant);

        Instant tomorrowSameTime = nowInstant.plus(1, ChronoUnit.DAYS);
        System.out.println("tomorrowSameTime = " + tomorrowSameTime);
        
        Duration sinceEpoch = Duration.between(Instant.EPOCH, nowInstant);
        System.out.println("sinceEpoch = " + sinceEpoch);
        
        long daysSinceEpoch = ChronoUnit.DAYS.between( Instant.EPOCH, nowInstant);
        System.out.println("daysSinceEpoch = " + daysSinceEpoch);

        
        // Play with LocalDate and days since epoch
        System.out.println("\n==== Play with LocalDate");
        LocalDate nowLocalDate = LocalDate.now();
            
        long currentEpochDay = nowLocalDate.toEpochDay();
        System.out.println("currentEpochDay = " + currentEpochDay);
        
        // The other way around...
        LocalDate localDateOfEpochDay = LocalDate.ofEpochDay(currentEpochDay);
        System.out.println("localDateOfEpochDay = " + localDateOfEpochDay);


        
        
        // Number of days till holiday - 17 July
        System.out.println("\n==== How long till holidays?");
        LocalDate holidays = LocalDate.of(2026, Month.JULY, 17);
        Period periodUntilHolidays = nowLocalDate.until( holidays);
        System.out.println("periodUntilHolidays = " + periodUntilHolidays);
        
        
        long numberDaysUntilHolidays = nowLocalDate.until(holidays, ChronoUnit.DAYS);
        System.out.println("numberDaysUntilHolidays = " + numberDaysUntilHolidays);
        
        
        Stream<LocalDate> localDatesUntilHolidays = 
                nowLocalDate.datesUntil(holidays.plusDays(1));
        
        localDatesUntilHolidays
                .filter(d -> d.getDayOfWeek().getValue() <= 5)
                .forEach( d -> System.out.println(d));
        
        
        System.out.println("\n==== First Saturday of every month");
        
        // TemporalAdjusters
        // First saturday of every month
        LocalDate firstDayOfMonth = nowLocalDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("firstDayOfMonth = " + firstDayOfMonth);
        
        // Find the date of the 1st Saturday in the current month
        LocalDate firstSaturdayOfMonth = nowLocalDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        System.out.println("firstSaturdayOfMonth = " + firstSaturdayOfMonth);
        
        // Find the dates of all first Saturdays of the months of next year
        LocalDate beginningNextYear = LocalDate.of(2027, Month.JANUARY, 1);
        LocalDate endNextYear = LocalDate.of(2027, Month.DECEMBER, 31);
        
        // Dates of the 1st day of each month
        Stream<LocalDate> datesUntil = beginningNextYear.datesUntil( endNextYear, Period.ofMonths(1));
        
        datesUntil
                .map( ld -> ld.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.SATURDAY)))
                .forEach( ld -> System.out.println( ld ));
        
        
        
        // Play with zoned Date Time
        System.out.println("\n==== Play with zoned date time");
        
        LocalDateTime now = LocalDateTime.now();  // Returns LocalDateTime, so TimeZone independent
        System.out.println("now = " + now);
        
        ZonedDateTime current = LocalDateTime.now().atZone(ZoneId.of("Europe/Amsterdam"));
        System.out.println("current Amsterdam = " + current);

        ZonedDateTime currentUTC = current.toInstant().atZone(ZoneId.of("UTC"));
        System.out.println("currentUTC = " + currentUTC);

        ZonedDateTime currentLondon = current.toInstant().atZone(ZoneId.of("Europe/London"));
        System.out.println("currentLondon = " + currentLondon);

        String currentFormatted = DateTimeFormatter.ISO_INSTANT.format(current);
        System.out.println("currentFormatted = " + currentFormatted);

        // Use default Locale
        Locale defaultLocale = Locale.getDefault();
        System.out.println("defaultLocale = " + defaultLocale);

        DateTimeFormatter localDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        String currentFormattedDefaultLocale = localDTF.format(current);
        System.out.println("localDTF = " + currentFormattedDefaultLocale);

        String formatGerman = localDTF.withLocale(Locale.GERMAN).format(current);
        System.out.println("formatGerman = " + formatGerman);

        // Take care: Dutch Locale nl-NL not supported...
        Locale dutchLocale = Locale.forLanguageTag("nl");
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("E dd-MM-yyyy");
        String customFormat = customFormatter.withLocale(dutchLocale).format(current);
        System.out.println("customFormat = " + customFormat);

        
        System.out.println("\n==== Play with MessageFormatters");
        
        // Use MessageFormat to replace placeholders (uses default locale):
        String msg = MessageFormat.format("On {2}, a {0}, destroyed {1} houses and caused {3, number, currency} of damage",
                "hurricane", 99, LocalTime.now(),10.0E8);
        System.out.println("msgEN = " + msg);
        
        // Localized MessageFormat
        Date dateTime = Date.from(LocalDate.now().atStartOfDay(ZoneId.of("Europe/Amsterdam")).toInstant());
        MessageFormat msgFormat = new MessageFormat("Heute ist der {0, date, long}",
                Locale.GERMAN);
        msg = msgFormat.format( new Object[] {dateTime} );
        System.out.println("msgDE = " + msg);
                
    }

}
