package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

/**
 * Represents a Player's rating in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {

    public static final String MESSAGE_RATING_CONSTRAINTS =
            "Player's rating should be an integer from 0 - 5.";

    public static final String RATING_VALIDATION_REGEX = "[0-5]";

    public final String value;
    private boolean isPrivate;

    /**
     * Constructs an {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        this.value = rating;
    }

    public Rating(String rating, boolean isPrivate) {
        this(rating);
        this.setPrivate(isPrivate);
    }

    /**
     * Returns true if a given string is a valid player's rating.
     */
    public static boolean isValidRating(String test) {
        return test.matches(RATING_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        if (isPrivate) {
            return "<Private Rating>";
        }
        return value;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void togglePrivacy() {
        this.isPrivate = isPrivate ? false : true;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && this.value.equals(((Rating) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
