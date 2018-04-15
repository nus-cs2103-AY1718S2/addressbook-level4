package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

//@@author alexawangzi
/**
 * Represents a Person's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String[] STATUS_VALUES = new String[] { "matched", "m", "not matched", "nm", ""};
    public static final HashSet<String> SET_ALL_STATUS = new HashSet<>(Arrays.asList(STATUS_VALUES));

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status should be one of: \n"
            + SET_ALL_STATUS.toString()
            + "\n";
    public static final Status DEFAULT_STATUS = new Status ("Not Matched");

    public final String value;

    /**
     * Constructs an {@code Status}.
     *
     * @param status A valid statust description.
     */
    public Status(String status) {
        requireNonNull(status);
        status = validateStatus(status);
        this.value = formatStatus(status);
    }


    /**
     * format the input into proper case
     * @param status
     * @return
     */
    private String formatStatus(String status) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(status);
    }


    /**
     * check validity of the status string supplied
     * @param status
     * @return string representing a valid status
     */
    private String validateStatus(String status) {
        status.toLowerCase();
        checkArgument(isValidStatus(status), MESSAGE_STATUS_CONSTRAINTS);
        status = convertToFullStatus(status);
        return status;
    }


    /**
     * Convert a shortcut to full status name
     */
    public String convertToFullStatus(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("nm")) {
            cur = "not matched";
        } else if (cur.equals("m")) {
            cur = "matched";
        }
        return cur;
    }


    /**
     * Returns if a given string is a valid status description.
     */
    public static boolean isValidStatus(String test) {
        test = test.toLowerCase();
        return SET_ALL_STATUS.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
