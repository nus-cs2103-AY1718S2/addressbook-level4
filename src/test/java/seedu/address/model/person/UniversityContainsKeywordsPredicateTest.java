package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class UniversityContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("NUS");
        List<String> secondPredicateKeywordList = Arrays.asList("NUS", "NTU");

        UniversityContainsKeywordsPredicate firstPredicate =
                new UniversityContainsKeywordsPredicate(firstPredicateKeywordList);
        UniversityContainsKeywordsPredicate secondPredicate =
                new UniversityContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UniversityContainsKeywordsPredicate firstPredicateCopy =
                new UniversityContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_universityContainsKeywords_returnsTrue() {
        // One keyword
        UniversityContainsKeywordsPredicate predicate =
                new UniversityContainsKeywordsPredicate(Collections.singletonList("NUS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Multiple keywords
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("Yale", "NUS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Only one matching keyword
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("Cool", "NUS"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));

        // Mixed-case keywords
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("yaLe", "nUs"));
        assertTrue(predicate.test(new PersonBuilder().withUniversity("Yale NUS").build()));
    }

    @Test
    public void test_universityDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        UniversityContainsKeywordsPredicate predicate =
                new UniversityContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Non-matching keyword
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("SMU"));
        assertFalse(predicate.test(new PersonBuilder().withUniversity("NUS").build()));

        // Keywords match phone, email, expected graduation year and name, but does not match university
        predicate = new UniversityContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withUniversity("NUS").withExpectedGraduationYear("2020").build()));
    }
}
