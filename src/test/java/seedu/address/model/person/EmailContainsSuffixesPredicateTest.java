package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("hy@example.com");
        List<String> secondPredicateKeywordList = Arrays.asList("hy@example.com", "yh@example.com");

        EmailContainsPrefixesPredicate firstPredicate =
                new EmailContainsPrefixesPredicate(firstPredicateKeywordList);
        EmailContainsPrefixesPredicate secondPredicate =
                new EmailContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsPrefixesPredicate firstPredicateCopy =
                new EmailContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsPrefixes_returnsTrue() {
        // One prefix
        EmailContainsPrefixesPredicate predicate =
                new EmailContainsPrefixesPredicate(Collections.singletonList("hy"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));

        // Mixed-case prefix
        predicate = new EmailContainsPrefixesPredicate(Arrays.asList("Hy"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        EmailContainsPrefixesPredicate predicate =
                new EmailContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@lol.com").build()));

        // Non-matching prefix
        predicate = new EmailContainsPrefixesPredicate(Arrays.asList("yh"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("hy@example.com").build()));
    }
}
