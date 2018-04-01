package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.FilterRange;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class GradePointAverageInKeywordsRangePredicateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GradePointAverageInKeywordsRangePredicate(null));
    }

    @Test
    public void equals() {
        FilterRange<GradePointAverage> firstPredicateFilterRange = new FilterRange<>(
                new GradePointAverage("3.20"), new GradePointAverage("5.00"));
        FilterRange<GradePointAverage> secondPredicateFilterRange = new FilterRange<>(
                new GradePointAverage("4.44"));

        GradePointAverageInKeywordsRangePredicate firstPredicate =
                new GradePointAverageInKeywordsRangePredicate(firstPredicateFilterRange);
        GradePointAverageInKeywordsRangePredicate secondPredicate =
                new GradePointAverageInKeywordsRangePredicate(secondPredicateFilterRange);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GradePointAverageInKeywordsRangePredicate firstPredicateCopy =
                new GradePointAverageInKeywordsRangePredicate(firstPredicateFilterRange);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_gradePointAverageInKeywordsRange_returnsTrue() {
        // Single keyword
        GradePointAverageInKeywordsRangePredicate predicate =
                new GradePointAverageInKeywordsRangePredicate(
                        new FilterRange<GradePointAverage>(new GradePointAverage("4.43")));
        assertTrue(predicate.test(new PersonBuilder().withGradePointAverage("4.43").build()));

        // Ranged keyword
        predicate = new GradePointAverageInKeywordsRangePredicate(new FilterRange<GradePointAverage>(
                new GradePointAverage("2.00"), new GradePointAverage("4.50")));
        assertTrue(predicate.test(new PersonBuilder().withGradePointAverage("3.33").build()));
    }

    @Test
    public void test_gradePointAverageNotInKeywordsRange_returnsFalse() {
        // Non-matching keyword for single predicate
        GradePointAverageInKeywordsRangePredicate predicate =
                new GradePointAverageInKeywordsRangePredicate(
                        new FilterRange<GradePointAverage>(new GradePointAverage("5.00")));
        assertFalse(predicate.test(new PersonBuilder().withGradePointAverage("4.99").build()));

        // Not-in-range keyword for ranged predicate
        predicate = new GradePointAverageInKeywordsRangePredicate(new FilterRange<GradePointAverage>(
                new GradePointAverage("1.00"), new GradePointAverage("1.50")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withGradePointAverage("1.51").build()));
    }
}
