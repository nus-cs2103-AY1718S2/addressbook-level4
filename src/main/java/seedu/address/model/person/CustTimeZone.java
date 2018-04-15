package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * Represents a person's timezone in the address book.
 */
//@@author glorialaw
public class CustTimeZone {

    public static final String MESSAGE_TIMEZONE_CONSTRAINTS =
            "Time zones should be have the standard time zone abbreviations, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //will convert from string to TimeZone when calendar is implemented
    public final String timeZone;

    public CustTimeZone(String timeZone) {
        requireNonNull(timeZone);
        checkArgument(isValidTimeZone(timeZone), MESSAGE_TIMEZONE_CONSTRAINTS);
        this.timeZone = timeZone;
    }

    /**
     * Returns true if a given string is a valid time zone. Some short abbreviations are not supported such as SGT.
     * Corrected with the if statement.
     */
    public static boolean isValidTimeZone(String test) {
        if (test.toUpperCase().equals("SGT")) {
            test = "Asia/Singapore";
        }
        return Arrays.asList(TimeZone.getAvailableIDs()).contains(test);
    }

    @Override
    public String toString() {
        return timeZone; }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CustTimeZone
                && this.timeZone.equals(((CustTimeZone) other).timeZone));
    }

    @Override
    public int hashCode() {
        return timeZone.hashCode(); }

}
