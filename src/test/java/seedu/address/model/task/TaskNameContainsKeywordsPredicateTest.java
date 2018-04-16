package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TaskBuilder;

//@@author CYX28
public class TaskNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("firstTask");
        List<String> secondPredicateKeywordList = Arrays.asList("firstTask", "secondTask");

        TaskNameContainsKeywordsPredicate firstPredicate =
                new TaskNameContainsKeywordsPredicate(firstPredicateKeywordList);
        TaskNameContainsKeywordsPredicate secondPredicate =
                new TaskNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskNameContainsKeywordsPredicate firstPredicateCopy =
                new TaskNameContainsKeywordsPredicate(firstPredicateKeywordList);

        // different types -> returns false
        assertFalse(firstPredicate.equals(5));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskNameContainsKeywords_returnsTrue() {
        // One keyword
        TaskNameContainsKeywordsPredicate predicate =
                new TaskNameContainsKeywordsPredicate(Collections.singletonList("TaskRandom"));
        assertTrue(predicate.test((new TaskBuilder().withTaskName("TaskRandom xyz").build())));

        // Multiple keywords
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("TaskRandom", "Project"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("TaskRandom Project").build()));

        // Only one matching keyword
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("Project", "nothing"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("Nothing to do").build()));

        // Mixed-case keywords
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("tAskRanDom", "pRojEct"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("TaskRandom Project").build()));

        // Uppercase keywords
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("TASKRANDOM", "PROJECT"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("TaskRandom Project").build()));

        // Different keywords order
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("Project", "TaskRandom"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("TaskRandom Project").build()));

        // Partial keywords
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("ject", "rand"));
        assertTrue(predicate.test(new TaskBuilder().withTaskName("TaskRandom Project").build()));
    }

    @Test
    public void test_taskNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TaskNameContainsKeywordsPredicate predicate = new TaskNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withTaskName("TaskRandom").build()));

        // Non-matching keyword
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("unrelated"));
        assertFalse(predicate.test(new TaskBuilder().withTaskName("TaskRandom Project").build()));

        // Keywords match due date and status, but does not match name
        predicate = new TaskNameContainsKeywordsPredicate(Arrays.asList("2018-04-20", "undone"));
        assertFalse(predicate.test(new TaskBuilder().withTaskName("TaskRandom Project").withTaskDueDate("2018-04-20")
                .withTaskStatus("undone").build()));
    }

}
