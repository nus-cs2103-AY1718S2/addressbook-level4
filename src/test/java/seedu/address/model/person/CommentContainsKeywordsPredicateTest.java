package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class CommentContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("He is decent");
        List<String> secondPredicateKeywordList = Arrays.asList("He is decent", "She is decent");

        CommentContainsKeywordsPredicate firstPredicate =
                new CommentContainsKeywordsPredicate(firstPredicateKeywordList);
        CommentContainsKeywordsPredicate secondPredicate =
                new CommentContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CommentContainsKeywordsPredicate firstPredicateCopy =
                new CommentContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_commentContainsKeywords_returnsTrue() {
        // One keyword
        CommentContainsKeywordsPredicate predicate =
                new CommentContainsKeywordsPredicate(Collections.singletonList("decent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Multiple keywords
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("is", "decent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Only one matching keyword
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("not", "decent"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Mixed-case keywords
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("iS", "dEcEnt"));
        assertTrue(predicate.test(new PersonBuilder().withComment("He is decent").build()));
    }

    @Test
    public void test_commentDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CommentContainsKeywordsPredicate predicate =
                new CommentContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Non-matching keyword
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("outstanding"));
        assertFalse(predicate.test(new PersonBuilder().withComment("He is decent").build()));

        // Keywords match phone, email, expected graduation year and name, but does not match comment
        predicate = new CommentContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice", "2020"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withComment("He is decent").withExpectedGraduationYear("2020").build()));
    }
}
