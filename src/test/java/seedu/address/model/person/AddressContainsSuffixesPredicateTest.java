package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsSuffixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Kent Ridge Drive");
        List<String> secondPredicateKeywordList = Arrays.asList("Kent Ridge Drive", "Computing Drive");

        AddressContainsSuffixesPredicate firstPredicate =
                new AddressContainsSuffixesPredicate(firstPredicateKeywordList);
        AddressContainsSuffixesPredicate secondPredicate =
                new AddressContainsSuffixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsSuffixesPredicate firstPredicateCopy =
                new AddressContainsSuffixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsSuffixes_returnsTrue() {
        // One suffix
        AddressContainsSuffixesPredicate predicate =
                new AddressContainsSuffixesPredicate(Collections.singletonList("ive"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Mixed-case suffix
        predicate = new AddressContainsSuffixesPredicate(Arrays.asList("IvE"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }

    @Test
    public void test_addressDoesNotContainSuffixes_returnsFalse() {
        // Zero suffix
        AddressContainsSuffixesPredicate predicate =
                new AddressContainsSuffixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Non-matching suffix
        predicate = new AddressContainsSuffixesPredicate(Arrays.asList("road"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }
}
