package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's review in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidReview(String)}
 */
public class Review {
    public static final String MESSAGE_REVIEW_CONSTRAINTS =
            "Person addresses can take any values, and it should not be blank";
    public static final String REVIEW_VALIDATION_REGEX = "[^\\s].*";
    private static final String DEFAULT_INVALID_REVIEW = "-";

    public final String value;

    /**
     * Constructs a {@code Review} for a new person who hasn't been assigned a review.
     */
    public Review() {
        value = DEFAULT_INVALID_REVIEW;
    }

    /**
     * Constructs a {@code Review}.
     *
     * @param review A valid review.
     */
    public Review(String review) {
        checkArgument(isValidReview(review), MESSAGE_REVIEW_CONSTRAINTS);
        value = review.trim();
    }

    /**
     * Returns true if a given string is a valid person review.
     */
    public static boolean isValidReview(String test) {
        return test.matches(REVIEW_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Review // instanceof handles nulls
                && this.value.equals(((Review) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
