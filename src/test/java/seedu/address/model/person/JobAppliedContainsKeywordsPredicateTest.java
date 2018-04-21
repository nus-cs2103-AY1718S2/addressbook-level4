package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class JobAppliedContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Software Engineer");
        List<String> secondPredicateKeywordList = Arrays.asList("Front-end Developer", "Data Analyst");

        JobAppliedContainsKeywordsPredicate firstPredicate =
                new JobAppliedContainsKeywordsPredicate(firstPredicateKeywordList);
        JobAppliedContainsKeywordsPredicate secondPredicate =
                new JobAppliedContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        JobAppliedContainsKeywordsPredicate firstPredicateCopy =
                new JobAppliedContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_jobAppliedContainsKeywords_returnsTrue() {
        // One keyword
        JobAppliedContainsKeywordsPredicate predicate =
                new JobAppliedContainsKeywordsPredicate(Collections.singletonList("Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Multiple keywords
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("Software", "Engineer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Only one matching keyword
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("Front-end", "Developer"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Developer").build()));

        // Mixed-case keywords
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("soFtWaRe", "eNGIneeR"));
        assertTrue(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));
    }

    @Test
    public void test_jobAppliedDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        JobAppliedContainsKeywordsPredicate predicate =
                new JobAppliedContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Non-matching keyword
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("analyst"));
        assertFalse(predicate.test(new PersonBuilder().withJobApplied("Software Engineer").build()));

        // Keywords match phone, email, expected graduation year and name, but does not match job applied
        predicate = new JobAppliedContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withJobApplied("Software Engineer")
                .withExpectedGraduationYear("2020").build()));
    }
}
