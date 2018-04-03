package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("999");
        List<String> secondPredicateKeywordList = Arrays.asList("999", "555");

        PhoneContainsSubstringsPredicate firstPredicate =
                new PhoneContainsSubstringsPredicate(firstPredicateKeywordList);
        PhoneContainsSubstringsPredicate secondPredicate =
                new PhoneContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsSubstringsPredicate firstPredicateCopy =
                new PhoneContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsSubstrings_returnsTrue() {
        // One substring
        PhoneContainsSubstringsPredicate predicate =
                new PhoneContainsSubstringsPredicate(Collections.singletonList("95"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));
    }

    @Test
    public void test_phoneDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        PhoneContainsSubstringsPredicate predicate =
                new PhoneContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").build()));

        // Non-matching substring
        predicate = new PhoneContainsSubstringsPredicate(Arrays.asList("555"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999111").build()));
    }
}
