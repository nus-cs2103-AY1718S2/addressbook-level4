package seedu.address.model.student.miscellaneousinfo;

//@@author samuelloh

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's remarks component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemarks(String)}
 */
public class Remarks {

    public static final String MESSAGE_REMARKS_CONSTRAINTS =
            "Student remarks can take any values, and it should not be blank";
    /*
     * The first character of the remarks must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARKS_VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Construct a {@code Remarks} instance with initial default value.
     */
    public Remarks() {
        value = "No remarks";
    }

    /**
     * Constructs a {@code Remarks} instance .
     *
     * @param remarks A valid string of remarks.
     */
    public Remarks(String remarks) {
        requireNonNull(remarks);
        checkArgument(isValidRemarks(remarks), MESSAGE_REMARKS_CONSTRAINTS);
        this.value = remarks;
    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidRemarks(String test) {
        return test.matches(REMARKS_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remarks // instanceof handles nulls
                && this.value.equals(((Remarks) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}
//@@author
