package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling strings representing Time.
 * Ensures that strings conform to a given Time Format.
 */
//@@author SuxianAlicia
public class TimeUtil {
    public static final String TIME_VALIDATION_REGEX = "\\d{2}:\\d{2}"; // format
    public static final String TIME_VALIDATION_FORMAT = "HH:mm"; // legal dates
    public static final String TIME_PATTERN = "HH:mm";
    /**
     * Returns true if given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        requireNonNull(test);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }

        return test.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Converts given string to a {@code LocalTime}.
     */
    public static LocalTime convertStringToTime(String time) throws DateTimeParseException {
        requireNonNull(time);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalTime convertedTime = LocalTime.parse(time, format);

        return convertedTime;
    }
}
