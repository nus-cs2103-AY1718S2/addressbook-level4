package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ExpectedGraduationYearContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("2020");
        List<String> secondPredicateKeywordList = Arrays.asList("2020", "2019");

        ExpectedGraduationYearContainsKeywordsPredicate firstPredicate =
                new ExpectedGraduationYearContainsKeywordsPredicate(firstPredicateKeywordList);
        ExpectedGraduationYearContainsKeywordsPredicate secondPredicate =
                new ExpectedGraduationYearContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExpectedGraduationYearContainsKeywordsPredicate firstPredicateCopy =
                new ExpectedGraduationYearContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expectedGraduationYearContainsKeywords_returnsTrue() {
        // One keyword
        ExpectedGraduationYearContainsKeywordsPredicate predicate =
                new ExpectedGraduationYearContainsKeywordsPredicate(Collections.singletonList("2020"));
        assertTrue(predicate.test(new PersonBuilder().withExpectedGraduationYear("2020").build()));

        // Multiple keywords
        predicate = new ExpectedGraduationYearContainsKeywordsPredicate(Arrays.asList("2020", "2019"));
        assertTrue(predicate.test(new PersonBuilder().withExpectedGraduationYear("2020").build()));
    }

    @Test
    public void test_expectedGraduationYearDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ExpectedGraduationYearContainsKeywordsPredicate predicate =
                new ExpectedGraduationYearContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withExpectedGraduationYear("2020").build()));

        // Non-matching keyword
        predicate = new ExpectedGraduationYearContainsKeywordsPredicate(Arrays.asList("2025"));
        assertFalse(predicate.test(new PersonBuilder().withName("2020").build()));

        // Keywords match phone, email, name and address, but does not match expected graduation year
        predicate = new ExpectedGraduationYearContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020").build()));
    }
}
