package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.EventBuilder;

//@@author x3tsunayh

public class TitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("firstEvent");
        List<String> secondPredicateKeywordList = Arrays.asList("firstEvent", "firstEvent");

        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(firstPredicateKeywordList);
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleContainsKeywordsPredicate firstPredicateCopy =
                new TitleContainsKeywordsPredicate(firstPredicateKeywordList);

        // different types -> returns false
        assertFalse(firstPredicate.equals(5));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventNameContainsKeywords_returnsTrue() {
        // One keyword
        TitleContainsKeywordsPredicate predicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("EventOne"));
        assertTrue(predicate.test((new EventBuilder().withTitle("EventOne").build())));

        // Multiple keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("EventOne", "Project"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));

        // Only one matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("EventOne", "NA"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne is over").build()));

        // Mixed-case keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("EvenTONe", "pRojEct"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));

        // Uppercase keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("RANDOMEVENT", "PROJECT"));
        assertTrue(predicate.test(new EventBuilder().withTitle("TaskRandom Project").build()));

        // Different keywords order
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("Project", "EventOne"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));

        // Partial keywords
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("ject", "Event"));
        assertTrue(predicate.test(new EventBuilder().withTitle("EventOne Project").build()));
    }

    @Test
    public void test_taskNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TitleContainsKeywordsPredicate predicate = new TitleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withTitle("Event").build()));

        // Non-matching keyword
        predicate = new TitleContainsKeywordsPredicate(Arrays.asList("unrelated"));
        assertFalse(predicate.test(new EventBuilder().withTitle("Event Project").build()));

    }

}
