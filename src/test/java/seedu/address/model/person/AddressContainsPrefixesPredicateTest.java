package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsPrefixesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Kent Ridge Drive");
        List<String> secondPredicateKeywordList = Arrays.asList("Kent Ridge Drive", "Computing Drive");

        AddressContainsPrefixesPredicate firstPredicate =
                new AddressContainsPrefixesPredicate(firstPredicateKeywordList);
        AddressContainsPrefixesPredicate secondPredicate =
                new AddressContainsPrefixesPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsPrefixesPredicate firstPredicateCopy =
                new AddressContainsPrefixesPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsPrefixes_returnsTrue() {
        // One prefix
        AddressContainsPrefixesPredicate predicate =
                new AddressContainsPrefixesPredicate(Collections.singletonList("Com"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Mixed-case prefix
        predicate = new AddressContainsPrefixesPredicate(Arrays.asList("cOm"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }

    @Test
    public void test_addressDoesNotContainPrefixes_returnsFalse() {
        // Zero prefix
        AddressContainsPrefixesPredicate predicate =
                new AddressContainsPrefixesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Non-matching prefix
        predicate = new AddressContainsPrefixesPredicate(Arrays.asList("Sci"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }
}
