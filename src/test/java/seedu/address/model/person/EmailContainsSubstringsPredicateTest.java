package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("hy@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("hy@example.com", "yh@example.com");

        EmailContainsSubstringsPredicate firstPredicate =
                new EmailContainsSubstringsPredicate(firstPredicateKeywordList);
        EmailContainsSubstringsPredicate secondPredicate =
                new EmailContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsSubstringsPredicate firstPredicateCopy =
                new EmailContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsSubstrings_returnsTrue() {
        // One substring
        EmailContainsSubstringsPredicate predicate =
                new EmailContainsSubstringsPredicate(Collections.singletonList("y@exa"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));

        // Mixed-case substring
        predicate = new EmailContainsSubstringsPredicate(Arrays.asList("Y@eXa"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        EmailContainsSubstringsPredicate predicate =
                new EmailContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@lol.com").build()));

        // Non-matching substring
        predicate = new EmailContainsSubstringsPredicate(Arrays.asList("yh@e"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }
}
