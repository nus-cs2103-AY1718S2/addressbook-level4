package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//@@author Kyomian
/**
 * Represents an Activity's datetime in the desk board.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String DEFAULT_DATETIME_FORMAT = "d/M/y H:m";
    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and Time should be in the format of " + DEFAULT_DATETIME_FORMAT;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);

    private final LocalDateTime dateTime;
    private final String dateTimeString;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param value A valid datetime.
     */
    public DateTime(String value) {
        requireNonNull(value);
        checkArgument(isValidDateTime(value), MESSAGE_DATETIME_CONSTRAINTS);
        this.dateTime = LocalDateTime.parse(value, formatter);
        this.dateTimeString = value;
    }

    /**
     * Returns true if a given string is a valid datetime.
     */
    public static boolean isValidDateTime(String value) {
        try {
            LocalDateTime.parse(value, formatter);
            return true;
        } catch (DateTimeParseException dtpe) {
            dtpe.printStackTrace();
            return false;
        }
    }

    public LocalDateTime getLocalDateTime() {
        return this.dateTime;
    }

    @Override
    public String toString() {
        return this.dateTimeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
