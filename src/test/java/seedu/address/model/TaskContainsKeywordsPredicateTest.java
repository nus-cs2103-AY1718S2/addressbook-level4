package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TaskBuilder;
//@@author a-shakra
public class TaskContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TaskContainsKeywordsPredicate firstPredicate = new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskContainsKeywordsPredicate secondPredicate = new TaskContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskContainsKeywordsPredicate firstPredicateCopy = new TaskContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));


        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskContainsKeywords_returnsTrue() {
        // One keyword
        TaskContainsKeywordsPredicate predicate = new TaskContainsKeywordsPredicate(Collections
                .singletonList("exampleTask1"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("exampleTask1").buildPersonalTask()));

        // Multiple keywords
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("exampleTask1", "exampleTask2"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("exampleTask1 exampleTask2").buildPersonalTask()));

        // Only one matching keyword
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("exampleTask2", "exampleTask3"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("exampleTask1 exampleTask3").buildPersonalTask()));

        // Mixed-case keywords
        predicate = new TaskContainsKeywordsPredicate(Arrays.asList("eXampleTask1", "ExampleTask2"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("exampleTask1 exampleTask2").buildPersonalTask()));
    }
}


