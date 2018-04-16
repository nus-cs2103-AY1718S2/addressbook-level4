package seedu.address.commons.util;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;


/**
 * A container for Date specific utility functions
 */
public class DateUtil {
    //@@author CYX28
    /**
     * Get today's date in LocalDate format
     * @return today's date in LocalDate format
     */
    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    /**
     * Parse given date into LocalDate format
     * @param dateToParse date must be in String format with hyphens e.g. 2018-05-12
     * @return date in LocalDate format
     */
    public static LocalDate getParsedDate(String dateToParse) {
        return LocalDate.parse(dateToParse);
    }

    /**
     * Parse given dateTime into LocalDate format
     * @param dateTimeToParse date must be in String format with hyphens e.g. 2018-05-12 1800
     * @return date in LocalDate format
     */
    public static LocalDate getParsedDateTime(String dateTimeToParse) {
        return LocalDate.parse(dateTimeToParse.substring(0, 10));
    }

    /**
     * Get the number of days between the start and end date
     * @param startDate a date in LocalDate format
     * @param endDate a date in LocalDate format
     * @return number of days in between the two dates
     */
    public static int getDayCountBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        return (int) DAYS.between(startDate, endDate);
    }

    //@@author jill858
    /**
     * Get the date of the month given the date
     * @param date a date in LocalDate format
     * @return date of the month
     */
    public static int getDateOfMonth(LocalDate date) {
        return date.getDayOfMonth();
    }

    /**
     * Get the month given the date
     * @param date a date in LocalDate format
     * @return the month
     */
    public static String getMonthOfDate(LocalDate date) {
        Locale locale = Locale.getDefault();
        Month month  = date.getMonth();
        return month.getDisplayName(TextStyle.SHORT, locale);
    }

    /**
     * Get the year of the given date
     * @param date a date in LocalDate format
     * @return the year
     */
    public static int getYearOfDate(LocalDate date) {
        return date.getYear();
    }

    /**
     * Format date in terms of dd-MMM-yyy, eg 18-Apr-2018
     * @param date a date in String format
     * @return date in terms of dd-MMM-yyyy
     */
    public static String dateFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
        LocalDate localDate = getParsedDate(date);

        return formatter.format(localDate);
    }

}
