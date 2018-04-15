//@@author emer7
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeyphrasesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondPredicateKeyphraseList = Arrays.asList("first", "second");

        TagContainsKeyphrasesPredicate firstPredicate = new TagContainsKeyphrasesPredicate(
                firstPredicateKeyphraseList);
        TagContainsKeyphrasesPredicate secondPredicate = new TagContainsKeyphrasesPredicate(
                secondPredicateKeyphraseList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeyphrasesPredicate firstPredicateCopy = new TagContainsKeyphrasesPredicate(
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
    public void test_tagContainsKeyphrases_returnsTrue() {
        // Zero keyphrase
        TagContainsKeyphrasesPredicate predicate = new TagContainsKeyphrasesPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // One keyphrase
        predicate = new TagContainsKeyphrasesPredicate(Collections.singletonList("Friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Family").build()));
        predicate = new TagContainsKeyphrasesPredicate(Collections.singletonList("Friends Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends", "Family").build()));

        // Multiple keyphrases
        predicate = new TagContainsKeyphrasesPredicate(Arrays.asList("Friends", "Colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));

        // Mixed-case keyphrase
        predicate = new TagContainsKeyphrasesPredicate(Arrays.asList("fRiends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friends").build()));
    }

    @Test
    public void test_tagDoesNotContainKeyphrases_returnsFalse() {
        // Non-matching keyphrase
        TagContainsKeyphrasesPredicate predicate = new TagContainsKeyphrasesPredicate(
                Collections.singletonList("Friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Enemy", "Nemesis").build()));

        // Keyphrase match name, phone, email, and address, but does not match tag
        predicate = new TagContainsKeyphrasesPredicate(
                Arrays.asList("Alice 12345 alice@email.com Main Street 3"));
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
