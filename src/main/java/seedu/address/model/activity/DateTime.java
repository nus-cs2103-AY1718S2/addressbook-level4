package seedu.address.model.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Activity's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateAndTime(String)}
 */
public class DateTime {

    public static final String DATE_FORMAT = "mm/dd/yyyy HH:MM:SS\n";
    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and  Time numbers should be a date and should be in the format of " +
            DATE_FORMAT;
    public static final String DATETIME_VALIDATION_REGEX =
            "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20) ([0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d)";
    private final String value;
    private Date date;
    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateAndTime A valid phone number.
     */
    public DateTime(String dateAndTime) {
        requireNonNull(dateAndTime);
        checkArgument(isValidDateAndTime(dateAndTime), MESSAGE_DATETIME_CONSTRAINTS);
        try {
            date = sdf.parse(dateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.value = dateAndTime;
    }

    /**
     * Returns true if a given string is a valid activity phone number.
     */
    public static boolean isValidDateAndTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return DateFormat.getDateInstance().format(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
