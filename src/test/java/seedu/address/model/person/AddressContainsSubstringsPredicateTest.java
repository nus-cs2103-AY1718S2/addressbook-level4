package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsSubstringsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Kent Ridge Drive");
        List<String> secondPredicateKeywordList = Arrays.asList("Kent Ridge Drive", "Computing Drive");

        AddressContainsSubstringsPredicate firstPredicate =
                new AddressContainsSubstringsPredicate(firstPredicateKeywordList);
        AddressContainsSubstringsPredicate secondPredicate =
                new AddressContainsSubstringsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsSubstringsPredicate firstPredicateCopy =
                new AddressContainsSubstringsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsSubstrings_returnsTrue() {
        // One substring
        AddressContainsSubstringsPredicate predicate =
                new AddressContainsSubstringsPredicate(Collections.singletonList("ing Dr"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Mixed-case substring
        predicate = new AddressContainsSubstringsPredicate(Arrays.asList("tIng Dri"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }

    @Test
    public void test_addressDoesNotContainSubstrings_returnsFalse() {
        // Zero substring
        AddressContainsSubstringsPredicate predicate =
                new AddressContainsSubstringsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));

        // Non-matching substring
        predicate = new AddressContainsSubstringsPredicate(Arrays.asList("olland Dr"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Computing Drive").build()));
    }
}
