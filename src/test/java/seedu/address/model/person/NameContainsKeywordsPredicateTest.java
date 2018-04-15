package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.pair.NameContainsKeywordsPredicatePair;
import seedu.address.testutil.PairBuilder;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        //pair checks
        NameContainsKeywordsPredicatePair firstPairPredicate = new
            NameContainsKeywordsPredicatePair(firstPredicateKeywordList);
        NameContainsKeywordsPredicatePair secondPairPredicate = new
            NameContainsKeywordsPredicatePair(secondPredicateKeywordList);
        assertTrue(firstPairPredicate.equals(firstPairPredicate));
        assertFalse(firstPairPredicate.equals(null));
        assertFalse(firstPairPredicate.equals(secondPairPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // person operation
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        //pair operation
        // One keyword
        NameContainsKeywordsPredicatePair predicatePair =
                new NameContainsKeywordsPredicatePair(Collections.singletonList("Elle"));
        assertTrue(predicatePair.test(new PairBuilder().build()));

        // Multiple keywords
        predicatePair = new NameContainsKeywordsPredicatePair(Arrays.asList("Elle", "Fiona"));
        assertTrue(predicatePair.test(new PairBuilder().build()));

        // Only one matching keyword
        predicatePair = new NameContainsKeywordsPredicatePair(Arrays.asList("Bob", "Elle"));
        assertTrue(predicatePair.test(new PairBuilder().build()));

        // Mixed-case keywords
        predicatePair = new NameContainsKeywordsPredicatePair(Arrays.asList("eLlE", "FioNa"));
        assertTrue(predicatePair.test(new PairBuilder().build()));
    }


    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
