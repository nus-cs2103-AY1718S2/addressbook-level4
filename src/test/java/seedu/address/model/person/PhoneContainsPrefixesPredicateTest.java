package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("999");
        List<String> secondPredicateKeywordList = Arrays.asList("999", "555");

        PhoneContainsPrefixesPredicate firstPredicate =
                new PhoneContainsPrefixesPredicate(firstPredicateKeywordList);
        PhoneContainsPrefixesPredicate secondPredicate
                = new PhoneContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsPrefixesPredicate firstPredicateCopy =
                new PhoneContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsPrefixes_returnsTrue() {
        // One prefix
        PhoneContainsPrefixesPredicate predicate =
                new PhoneContainsPrefixesPredicate(Collections.singletonList("99"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("999555").build()));
    }

    @Test
    public void test_phoneDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        PhoneContainsPrefixesPredicate predicate =
                new PhoneContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("999").build()));

        // Non-matching prefix
        predicate = new PhoneContainsPrefixesPredicate(Arrays.asList("555"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("999111").build()));
    }
}
