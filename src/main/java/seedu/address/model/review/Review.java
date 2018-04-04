//@@author emer7

package seedu.address.model.review;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's review in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCombined(String)}
 */
public class Review {
    public static final String MESSAGE_REVIEW_CONSTRAINTS =
            "Person reviewer and review can take any values, and they should not be blank.";
    public static final String REVIEWER_VALIDATION_REGEX = "[^\\s].*";
    public static final String REVIEW_VALIDATION_REGEX = "[^\\s].*";
    private static final String DEFAULT_REVIEWER = "-";
    private static final String DEFAULT_REVIEW = "-";

    public final String reviewer;
    public final String value;

    /**
     * Constructs a {@code Review} for a new person who hasn't been assigned a review.
     */
    public Review() {
        reviewer = DEFAULT_REVIEWER;
        value = DEFAULT_REVIEW;
    }

    /**
     * Constructs a {@code Review}.
     *
     * @param combined A valid combined reviewer and review.
     */
    public Review(String combined) {
        checkArgument(isValidCombined(combined), MESSAGE_REVIEW_CONSTRAINTS);
        this.reviewer = combined.split("[\\r\\n]+")[0].trim();
        this.value = combined.split("[\\r\\n]+")[1].trim();
    }

    /**
     * Returns true if a given string is a valid reviewer.
     */
    public static boolean isValidReviewer(String test) {
        return test.matches(REVIEWER_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid review.
     */
    public static boolean isValidReview(String test) {
        return test.matches(REVIEW_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid combined reviewer and review.
     */
    public static boolean isValidCombined(String test) {
        return test.split("[\\r\\n]+").length == 2 && (
                isValidReviewer(test.split("[\\r\\n]+")[0].trim())
                && isValidReview(test.split("[\\r\\n]+")[1].trim()));
    }

    @Override
    public String toString() {
        return reviewer + "\n" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Review // instanceof handles nulls
                && this.reviewer.equals(((Review) other).reviewer)
                && this.value.equals(((Review) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
