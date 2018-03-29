package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeyphrasesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondPredicateKeyphraseList = Arrays.asList("first", "second");

        NameContainsKeyphrasesPredicate firstPredicate = new NameContainsKeyphrasesPredicate(
                firstPredicateKeyphraseList);
        NameContainsKeyphrasesPredicate secondPredicate = new NameContainsKeyphrasesPredicate(
                secondPredicateKeyphraseList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeyphrasesPredicate firstPredicateCopy = new NameContainsKeyphrasesPredicate(
                firstPredicateKeyphraseList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeyphrases_returnsTrue() {
        // Zero keyphrase
        NameContainsKeyphrasesPredicate predicate = new NameContainsKeyphrasesPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // One keyphrase
        predicate = new NameContainsKeyphrasesPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
        predicate = new NameContainsKeyphrasesPredicate(Collections.singletonList("Alice Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keyphrases
        predicate = new NameContainsKeyphrasesPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Mixed-case keyphrase
        predicate = new NameContainsKeyphrasesPredicate(Arrays.asList("aLIce bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeyphrases_returnsFalse() {
        // Non-matching keyphrase
        NameContainsKeyphrasesPredicate predicate = new NameContainsKeyphrasesPredicate(
                Collections.singletonList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keyphrase match phone, email, address, tag, and rating, but does not match name
        predicate = new NameContainsKeyphrasesPredicate(
                Arrays.asList("12345 alice@email.com Main Street Friends 3"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withEmail("alice@email.com")
                .withAddress("Main Street")
                .withTags("Friends")
                .withRating("3")
                .build()));
    }
}
