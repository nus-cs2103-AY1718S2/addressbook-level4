package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the link to a Person's TimeTable.
 * Guarantees: immutable; is valid as declared in {@link #isValidLink(String)}
 */
public class TimeTableLink {

    public static final String MESSAGE_TIMETABLE_LINK_CONSTRAINTS = "Timetable Links should "
            + "adhere to the following constraints:\n"
            + "1. Begin with \"http://modsn.us/\". \n"
            + "2. This is followed by a string of alphanumeric characters. ";
    // alphanumeric and special characters
    private static final String SHORT_URL_FROINT_REGEX = "http://modsn\\.us/";
    private static final String SHORT_URL_TRAIL_REGEX = "[a-zA-Z0-9\\-]+"; // alphanumeric and hyphen
    public static final String SHORT_URL_VALIDATION_REGEX = SHORT_URL_FROINT_REGEX + SHORT_URL_TRAIL_REGEX;

    public final String value;

    /**
     * Constructs an {@code TimeTableLink}.
     *
     * @param link A valid email address.
     */
    public TimeTableLink(String link) {
        requireNonNull(link);
        checkArgument(isValidLink(link), MESSAGE_TIMETABLE_LINK_CONSTRAINTS);
        this.value = link;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidLink(String test) {
        return test.matches(SHORT_URL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeTableLink // instanceof handles nulls
                && this.value.equals(((TimeTableLink) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
