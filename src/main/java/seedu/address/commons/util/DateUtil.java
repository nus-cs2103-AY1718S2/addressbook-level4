package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling Date related operations.
 * Ensures that strings conform to a given Date Format.
 */
//@@author SuxianAlicia
public class DateUtil {
    public static final String DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}"; // format
    public static final String DATE_VALIDATION_FORMAT = "dd-MM-yyyy"; // legal dates
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    /**
     * Returns true if given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }

        return test.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Converts given string to a {@code LocalDate}.
     * @param date
     * @return
     */
    public static LocalDate convertStringToDate(String date) throws DateTimeParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate convertedDate = LocalDate.parse(date, format);

        return convertedDate;
    }

}
