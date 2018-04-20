//@@author Kyholmes
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Appointment DateTime in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class DateTime {
    public static  final String MESSAGE_DATE_TIME_CONSTRAINTS = "Appointment date time should be in this format: \n"
            + "D/M/YYYY HHMM (24-hour clock)";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
    private String dateString;
    private String timeString;

    public DateTime(String appointmentDateTime) {
        requireNonNull(appointmentDateTime);
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
            compareDate = LocalDate.parse(dateTimeKeys[0], dateFormatter);
        } catch (DateTimeParseException e) {
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
            compareDate = LocalDate.parse(dateTimeKeys[0], dateFormatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage());
        }

        return (compareDate.isAfter(LocalDate.now()) || compareDate.isEqual(LocalDate.now()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateTime
                && this.dateString.equals(((DateTime) other).dateString)
                && this.timeString.equals(((DateTime) other).timeString));
    }

    public LocalDate getLocalDate() {
        return LocalDate.parse(dateString, dateFormatter);
    }

    public LocalTime getLocalTime() {
        return LocalTime.parse(timeString, timeFormatter);
    }

    public LocalTime getEndLocalTime(int minutes) {
        return getLocalTime().plusMinutes(minutes);
    }

    /**
     * Returns true if a given string is a valid date time string.
     */
    public static boolean isValidDateTime(String dateTimeString) {
        String trimmedArgs = dateTimeString.trim();
        String[] dateTimeKeys = trimmedArgs.split("\\s");

        try {
            LocalDate.parse(dateTimeKeys[0], dateFormatter);
            LocalTime.parse(dateTimeKeys[1], timeFormatter);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }
}
