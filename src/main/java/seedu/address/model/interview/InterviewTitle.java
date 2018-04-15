//@@author deeheenguyen
package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Interview's title in the address book.
 * Guarantees: immutable; is valid as declared in {@title #isValidTitle(String)}
 */

public class InterviewTitle {
    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Interview Title should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTitle;

    /**
     * Constructs a {@code JobTitle}.
     *
     * @param title A valid interview title.
     */
    public InterviewTitle(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.fullTitle = title;
    }

    /**
     * Returns true if a given string is a valid job title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.interview.InterviewTitle // instanceof handles nulls
                && this.fullTitle.equals(((InterviewTitle) other).fullTitle)); // state check
    }

    @Override
    public int hashCode() {
        return fullTitle.hashCode();
    }
}
//author@@ deeheenguyen
