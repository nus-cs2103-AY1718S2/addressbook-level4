package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("999");
        List<String> secondPredicateKeywordList = Arrays.asList("999", "555");

        PhoneContainsSuffixesPredicate firstPredicate =
                new PhoneContainsSuffixesPredicate(firstPredicateKeywordList);
        PhoneContainsSuffixesPredicate secondPredicate
                = new PhoneContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsSuffixesPredicate firstPredicateCopy =
                new PhoneContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsSuffixes_returnsTrue() {
        // One suffix
        PhoneContainsSuffixesPredicate predicate =
                new PhoneContainsSuffixesPredicate(Collections.singletonList("55"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));
    }

    @Test
    public void test_phoneDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        PhoneContainsSuffixesPredicate predicate =
                new PhoneContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").build()));

        // Non-matching suffix
        predicate = new PhoneContainsSuffixesPredicate(Arrays.asList("55"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999111").build()));
    }
}
