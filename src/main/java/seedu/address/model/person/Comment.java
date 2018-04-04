package seedu.address.model.person;

import static java.util.Objects.isNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

//@@author Ang-YC
/**
 * Represents a Person's comment in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidComment(String)}
 */
public class Comment {

    public static final String MESSAGE_COMMENT_CONSTRAINTS = "Person comment can take any values";
    public static final String COMMENT_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Constructs a {@code Comment}.
     *
     * @param comment A valid comment.
     */
    public Comment(String comment) {
        if (isNull(comment)) {
            this.value = null;
        } else {
            checkArgument(isValidComment(comment), MESSAGE_COMMENT_CONSTRAINTS);
            this.value = comment;
        }
    }

    /**
     * Returns true if a given string is a valid comment.
     * By default any string are valid
     */
    public static boolean isValidComment(String test) {
        return test.matches(COMMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && Objects.equals(this.value, ((Comment) other).value)); // State check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
