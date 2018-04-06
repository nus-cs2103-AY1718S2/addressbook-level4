package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author trafalgarandre
/**
 * Represents start date time of an appointment in the address book.
 * Guarantees: is valid as declared in {@link #isValidStartDateTime(String)} }
 */
public class StartDateTime {
    public static final String MESSAGE_START_DATE_TIME_CONSTRAINTS =
            "Start date time should be a valid local date time";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String START_DATE_TIME_VALIDATION_REGEX =
            "^[1-3][0-9][0-9][0-9]-(1[0-2]|0[1-9])-(0[1-9]|[1-2][0-9]|3[0-1])\\s([0-1][0-9]|2[0-4]):([0-5][0-9])$";

    public final String startDateTime;

    /**
     * Constructs a {@code Name}.
     *
     * @param startDateTime A valid startDateTime.
     */
    public StartDateTime(String startDateTime) {
        requireNonNull(startDateTime);
        checkArgument(isValidStartDateTime(startDateTime), MESSAGE_START_DATE_TIME_CONSTRAINTS);
        this.startDateTime = startDateTime;
    }

    /**
     * Returns true if a given string is a valid startDateTime.
     */
    public static boolean isValidStartDateTime(String test) {
        return test.matches(START_DATE_TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return startDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDateTime // instanceof handles nulls
                && this.startDateTime.equals(((StartDateTime) other).startDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return startDateTime.hashCode();
    }

}
