package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class MajorContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Computer");
        List<String> secondPredicateKeywordList = Arrays.asList("Computer", "Science");

        MajorContainsKeywordsPredicate firstPredicate =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        MajorContainsKeywordsPredicate secondPredicate =
                new MajorContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MajorContainsKeywordsPredicate firstPredicateCopy =
                new MajorContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_majorContainsKeywords_returnsTrue() {
        // One keyword
        MajorContainsKeywordsPredicate predicate =
                new MajorContainsKeywordsPredicate(Collections.singletonList("Computer"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Multiple keywords
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Computer", "Science"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Only one matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Computer", "Engineering"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Mixed-case keywords
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("ComPutEr", "SciEncE"));
        assertTrue(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));
    }

    @Test
    public void test_majorDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MajorContainsKeywordsPredicate predicate = new MajorContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Non-matching keyword
        predicate = new MajorContainsKeywordsPredicate(Arrays.asList("Security"));
        assertFalse(predicate.test(new PersonBuilder().withMajor("Computer Science").build()));

        // Keywords match phone, email, expected graduation year and address, but does not match major
        predicate = new MajorContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street", "2020", "Information", "Security"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withExpectedGraduationYear("2020")
                .withMajor("Computer Science").build()));
    }
}
