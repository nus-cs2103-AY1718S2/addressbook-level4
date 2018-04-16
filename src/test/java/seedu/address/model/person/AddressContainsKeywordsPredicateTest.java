package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author jill858
public class AddressContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Clementi Avenue");
        List<String> secondPredicateKeywordList = Arrays.asList("Clementi Avenue", "Dover Road");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("clementi"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clementi", "avenue"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clementi", "road"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // One mixed-case keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("ClEMenti"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // Multiple mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("ClEMenti", "AvEnUe"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // One partial keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clem"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // Multiple partial keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clem", "ave"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // One partial mixed-case keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("ClEMe"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // Multiple partial mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("ClEM", "AvE"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("clementi avenue").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("clementi", "avenue"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("dover road").build()));


        // address not match
        predicate = new AddressContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Alice", "Loop", "Avenue", "classmates"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("friends").build()));
    }
}
