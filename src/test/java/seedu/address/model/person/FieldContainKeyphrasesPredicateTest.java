package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FieldContainKeyphrasesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeyphraseList = Collections.singletonList("first");
        List<String> secondPredicateKeyphraseList = Arrays.asList("first", "second");
        List<String> thirdPredicateKeyphraseList = Collections.emptyList();
        List<String> fourthPredicateKeyphraseList = Collections.emptyList();

        FieldContainKeyphrasesPredicate firstPredicate = new FieldContainKeyphrasesPredicate(
                firstPredicateKeyphraseList, thirdPredicateKeyphraseList, fourthPredicateKeyphraseList);
        FieldContainKeyphrasesPredicate secondPredicate = new FieldContainKeyphrasesPredicate(
                secondPredicateKeyphraseList, thirdPredicateKeyphraseList, fourthPredicateKeyphraseList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainKeyphrasesPredicate firstPredicateCopy = new FieldContainKeyphrasesPredicate(
                firstPredicateKeyphraseList, thirdPredicateKeyphraseList, fourthPredicateKeyphraseList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containKeyphrases_returnsTrue() {
        // Both zero keyphrase
        FieldContainKeyphrasesPredicate predicate = new FieldContainKeyphrasesPredicate(
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends", "Family").build()));

        // Both one keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Alice"), Collections.singletonList("Friends"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends", "Family").build()));

        // Name one keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Alice Bob"), Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends", "Family").build()));

        // Tag one keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.emptyList(), Collections.singletonList("Friends Family"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends", "Family").build()));

        // Both multiple keyphrases, but only one matches
        predicate = new FieldContainKeyphrasesPredicate(
                Arrays.asList("Alice", "Carol"), Arrays.asList("Friends", "Enemy"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends", "Family").build()));

        // Both mixed-case keyphrase
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("aLIce"), Collections.singletonList("fRIeNDs"), Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends", "Family").build()));
    }

    @Test
    public void test_notContainKeyphrases_returnsFalse() {
        // Non-matching keyphrase
        FieldContainKeyphrasesPredicate predicate = new FieldContainKeyphrasesPredicate(
                Collections.singletonList("Carol"),
                Collections.singletonList("Enemy"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("Friends").build()));

        // Keyphrase match phone, email, address, and tags, but does not match name
        predicate = new FieldContainKeyphrasesPredicate(
                Arrays.asList("12345 alice@email.com Main Street Friends"),
                        Collections.emptyList(),
                        Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keyphrase match phone, email, address, and tags, but does not match tag
        predicate = new FieldContainKeyphrasesPredicate(
                Collections.emptyList(),
                Arrays.asList("Alice 12345 alice@email.com Main Street"),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
