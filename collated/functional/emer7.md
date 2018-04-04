# emer7
###### \java\seedu\address\model\review\Review.java
``` java

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
```
###### \java\seedu\address\model\review\UniqueReviewList.java
``` java

package seedu.address.model.review;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of reviews that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Review#equals(Object)
 */
public class UniqueReviewList implements Iterable<Review> {

    private final ObservableList<Review> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ReviewList.
     */
    public UniqueReviewList() {}

    /**
     * Creates a UniqueReviewList using given reviews.
     * Enforces no nulls.
     */
    public UniqueReviewList(Set<Review> reviews) {
        requireAllNonNull(reviews);
        internalList.addAll(reviews);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all reviews in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Review> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Reviews in this list with those in the argument review list.
     */
    public void setReviews(Set<Review> reviews) {
        requireAllNonNull(reviews);
        internalList.setAll(reviews);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every review in the argument list exists in this object.
     */
    public void mergeFrom(UniqueReviewList from) {
        final Set<Review> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(review -> !alreadyInside.contains(review))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Review as the given argument.
     */
    public boolean contains(Review toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Review to the list.
     *
     * @throws DuplicateReviewException if the Review to add is a duplicate of an existing Review in the list.
     */
    public void add(Review toAdd) throws DuplicateReviewException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReviewException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Review> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Review> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueReviewList // instanceof handles nulls
                && this.internalList.equals(((UniqueReviewList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueReviewList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateReviewException extends DuplicateDataException {
        protected DuplicateReviewException() {
            super("Operation would result in duplicate reviews");
        }
    }

}
```
