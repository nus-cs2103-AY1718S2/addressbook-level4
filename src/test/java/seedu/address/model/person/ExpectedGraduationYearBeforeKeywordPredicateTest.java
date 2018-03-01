package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ExpectedGraduationYearBeforeKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "2020";
        String secondPredicateKeyword = "2024";

        ExpectedGraduationYearBeforeKeywordPredicate firstPredicate =
                new ExpectedGraduationYearBeforeKeywordPredicate(firstPredicateKeyword);
        ExpectedGraduationYearBeforeKeywordPredicate secondPredicate =
                new ExpectedGraduationYearBeforeKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExpectedGraduationYearBeforeKeywordPredicate firstPredicateCopy =
                new ExpectedGraduationYearBeforeKeywordPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(2020));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expectedGraduationYearBeforeOrEqualKeyword_returnsTrue() {
        // Before keyword

        ExpectedGraduationYearBeforeKeywordPredicate predicate =
                new ExpectedGraduationYearBeforeKeywordPredicate("2020");
        assertTrue(predicate.test(new PersonBuilder().withExpectedGraduationYear("2019").build()));

        // Equals keyword
        assertTrue(predicate.test(new PersonBuilder().withExpectedGraduationYear("2020").build()));
    }

    @Test
    public void test_expectedGraduationYearAfterKeyword_returnsFalse() {
        // After keyword
        ExpectedGraduationYearBeforeKeywordPredicate predicate =
                new ExpectedGraduationYearBeforeKeywordPredicate("2018");
        assertFalse(predicate.test(new PersonBuilder().withExpectedGraduationYear("2020").build()));

    }
}
