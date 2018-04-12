package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

//@@author jlks96
/**
 * Represents a time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time input should be in the format: HH:mm (24 hour format)";
    /*
     * The first character of the time must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIME_VALIDATION_REGEX = "\\d{2}:\\d{2}";

    public final String time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time in the 24 hour format HH:mm.
     */
    public static boolean isValidTime(String test) {

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        timeFormatter.setLenient(false);

        try {
            timeFormatter.parse(test); //attempt to parse time
        } catch (ParseException e) { //if fail return false
            return false;
        }
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}
