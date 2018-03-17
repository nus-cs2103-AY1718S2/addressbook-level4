package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.FilterRange;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

public class ExpectedGraduationYearInKeywordsRangePredicateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpectedGraduationYearInKeywordsRangePredicate(null));
    }

    @Test
    public void equals() {
        FilterRange<ExpectedGraduationYear> firstPredicateFilterRange = new FilterRange<>(
                new ExpectedGraduationYear("2018"), new ExpectedGraduationYear("2020"));
        FilterRange<ExpectedGraduationYear> secondPredicateFilterRange = new FilterRange<>(
                new ExpectedGraduationYear("2021"));

        ExpectedGraduationYearInKeywordsRangePredicate firstPredicate =
                new ExpectedGraduationYearInKeywordsRangePredicate(firstPredicateFilterRange);
        ExpectedGraduationYearInKeywordsRangePredicate secondPredicate =
                new ExpectedGraduationYearInKeywordsRangePredicate(secondPredicateFilterRange);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExpectedGraduationYearInKeywordsRangePredicate firstPredicateCopy =
                new ExpectedGraduationYearInKeywordsRangePredicate(firstPredicateFilterRange);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expectedGraduationYearInKeywordsRange_returnsTrue() {
        // Single keyword
        ExpectedGraduationYearInKeywordsRangePredicate predicate =
                new ExpectedGraduationYearInKeywordsRangePredicate(
                        new FilterRange<ExpectedGraduationYear>(new ExpectedGraduationYear("2021")));
        assertTrue(predicate.test(new PersonBuilder().withExpectedGraduationYear("2021").build()));

        // Ranged keyword
        predicate = new ExpectedGraduationYearInKeywordsRangePredicate(new FilterRange<ExpectedGraduationYear>(
                new ExpectedGraduationYear("2017"), new ExpectedGraduationYear("2020")));
        assertTrue(predicate.test(new PersonBuilder().withExpectedGraduationYear("2020").build()));
    }

    @Test
    public void test_expectedGraduationYearNotInKeywordsRange_returnsFalse() {
        // Non-matching keyword for single predicate
        ExpectedGraduationYearInKeywordsRangePredicate predicate =
                new ExpectedGraduationYearInKeywordsRangePredicate(
                        new FilterRange<ExpectedGraduationYear>(new ExpectedGraduationYear("2025")));
        assertFalse(predicate.test(new PersonBuilder().withName("2020").build()));

        // Not-in-range keyword for ranged predicate
        predicate = new ExpectedGraduationYearInKeywordsRangePredicate(new FilterRange<ExpectedGraduationYear>(
                new ExpectedGraduationYear("2024"), new ExpectedGraduationYear("2025")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020").build()));
    }
}
