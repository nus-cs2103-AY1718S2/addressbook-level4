package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author trafalgarandre
/**
 * Represents end date time of an appointment in the address book.
 * Guarantees: is valid as declared in {@link #isValidEndDateTime(String)} }
 */
public class EndDateTime {
    public static final String MESSAGE_END_DATE_TIME_CONSTRAINTS = "Start date time should be a valid local date time";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String END_DATE_TIME_VALIDATION_REGEX =
            "^[1-3][0-9][0-9][0-9]-(1[0-2]|0[1-9])-(0[1-9]|[1-2][0-9]|3[0-1])\\s([0-1][0-9]|2[0-4]):([0-5][0-9])$";

    public final String endDateTime;

    /**
     * Constructs a {@code Name}.
     *
     * @param endDateTime A valid endDateTime.
     */
    public EndDateTime(String endDateTime) {
        requireNonNull(endDateTime);
        checkArgument(isValidEndDateTime(endDateTime), MESSAGE_END_DATE_TIME_CONSTRAINTS);
        this.endDateTime = endDateTime;
    }

    /**
     * Returns true if a given string is a valid endDateTime.
     */
    public static boolean isValidEndDateTime(String test) {
        return test.matches(END_DATE_TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return endDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDateTime // instanceof handles nulls
                && this.endDateTime.equals(((EndDateTime) other).endDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return endDateTime.hashCode();
    }

}
