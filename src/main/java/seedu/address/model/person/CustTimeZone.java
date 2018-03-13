package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import java.util.TimeZone;
import static seedu.address.commons.util.AppUtil.checkArgument;


public class CustTimeZone {

    public static final String MESSAGE_TIMEZONE_CONSTRAINTS =
            "Time zones should be have the standard time zone abbreviations, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TIMEZONE_VALIDATION_REGEX = "[^\\s].*";

    //will convert from string to TimeZone when calendar is implemented
    public final String timeZone;

    public CustTimeZone(String timeZone) {
        requireNonNull(timeZone);
        checkArgument(isValidTimeZone(timeZone), MESSAGE_TIMEZONE_CONSTRAINTS);
        this.timeZone = timeZone;
    }

    /**
     * Returns true if a given string is a valid time zone.
     */
    public static boolean isValidTimeZone(String test) {
        return test.matches(TIMEZONE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CustTimeZone);
    }

    @Override
    public int hashCode() { return timeZone.hashCode();}

}
