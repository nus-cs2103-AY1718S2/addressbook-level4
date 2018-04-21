package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.FilterRange;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;
//@@author mhq199657
public class RatingInKeywordsRangePredicateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RatingInKeywordsRangePredicate(null));
    }

    @Test
    public void equals() {
        FilterRange<Rating> firstPredicateFilterRange = new FilterRange<>(
                new Rating(3.0, 3.0, 3.0, 3.0), new Rating(4.0, 4.0, 4.0, 4.0));
        FilterRange<Rating> secondPredicateFilterRange = new FilterRange<>(
                new Rating(2.97, 2.97, 2.97, 2.97));

        RatingInKeywordsRangePredicate firstPredicate =
                new RatingInKeywordsRangePredicate(firstPredicateFilterRange);
        RatingInKeywordsRangePredicate secondPredicate =
                new RatingInKeywordsRangePredicate(secondPredicateFilterRange);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RatingInKeywordsRangePredicate firstPredicateCopy =
                new RatingInKeywordsRangePredicate(firstPredicateFilterRange);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ratingInKeywordsRange_returnsTrue() {
        // Single keyword
        RatingInKeywordsRangePredicate predicate =
                new RatingInKeywordsRangePredicate(
                        new FilterRange<Rating>(new Rating(2.97, 2.97, 2.97, 2.97)));
        assertTrue(predicate.test(new PersonBuilder().withRating("2.95", "2.99", "2.98", "2.96").build()));

        // Ranged keyword
        predicate = new RatingInKeywordsRangePredicate(new FilterRange<Rating>(
                new Rating(2.0, 2.0, 2.0, 2.0), new Rating(2.1, 2.1, 2.1, 2.1)));
        assertTrue(predicate.test(new PersonBuilder().withRating("2.3", "1.9", "2.0", "1.99").build()));

    }

    @Test
    public void test_ratingNotInKeywordsRange_returnsFalse() {
        // Non-matching keyword for single predicate
        RatingInKeywordsRangePredicate predicate =
                new RatingInKeywordsRangePredicate(
                        new FilterRange<Rating>(new Rating(2.97, 2.97, 2.97, 2.97)));
        assertFalse(predicate.test(new PersonBuilder().withRating("2.95", "3.1", "2.98", "2.96").build()));

        // Not-in-range keyword for ranged predicate
        predicate = new RatingInKeywordsRangePredicate(new FilterRange<Rating>(
                new Rating(2.0, 2.0, 2.0, 2.0), new Rating(2.1, 2.1, 2.1, 2.1)));
        assertFalse(predicate.test(new PersonBuilder().withRating("2.0", "2.2", "2.2", "2.2").build()));
    }

}
