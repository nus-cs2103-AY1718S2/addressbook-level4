package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author tanhengyeow
public class NameContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsSubstringsPredicate firstPredicate =
                new NameContainsSubstringsPredicate(firstPredicateKeywordList);
        NameContainsSubstringsPredicate secondPredicate =
                new NameContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsSubstringsPredicate firstPredicateCopy =
                new NameContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsSubstrings_returnsTrue() {
        // One substring
        NameContainsSubstringsPredicate predicate =
                new NameContainsSubstringsPredicate(Collections.singletonList("ce Bo"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case substring
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("Ce BO"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        NameContainsSubstringsPredicate predicate = new NameContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching substring
        predicate = new NameContainsSubstringsPredicate(Arrays.asList("harle"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }
}
