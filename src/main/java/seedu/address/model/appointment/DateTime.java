package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Appointment DateTime in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class DateTime {

    public static final int APPOINTMENT_DURATION = 30;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private String dateString;
    private String timeString;

    public DateTime(String appointmentDateTime) {
        String trimmedArgs = appointmentDateTime.trim();

        String[] dateTimeKeys = trimmedArgs.split("\\s");
        this.dateString = dateTimeKeys[0];
        this.timeString = dateTimeKeys[1];
    }

    public String toString() {
        return dateString + " " + timeString;
    }

    /**
     * Check if the date is past compared with today
     * @param dateTimeString
     * @return true if the date is already past
     */
    public static boolean isBefore(String dateTimeString) throws ParseException {
        String trimmedArgs = dateTimeString.trim();

        String[] dateTimeKeys = trimmedArgs.split("\\s");

        LocalDate compareDate;

        try {
            compareDate = LocalDate.parse(dateTimeKeys[0], formatter);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
        return compareDate.isBefore(LocalDate.now());
    }

    /**
     * Check if the date is ahead compared with today
     * @param dateTimeString
     * @return true if the date is ahead
     */
    public static boolean isAfterOrEqual(String dateTimeString) throws ParseException {
        String trimmedArgs = dateTimeString.trim();

        String[] dateTimeKeys = trimmedArgs.split("\\s");

        LocalDate compareDate;

        try {
            compareDate = LocalDate.parse(dateTimeKeys[0], formatter);
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }

        return (compareDate.isAfter(LocalDate.now()) || compareDate.isEqual(LocalDate.now()));
    }
}
