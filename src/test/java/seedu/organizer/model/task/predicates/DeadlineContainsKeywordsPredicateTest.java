package seedu.organizer.model.task.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.organizer.testutil.TaskBuilder;

//@@author guekling
public class DeadlineContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DeadlineContainsKeywordsPredicate firstPredicate = new DeadlineContainsKeywordsPredicate(
            firstPredicateKeywordList);
        DeadlineContainsKeywordsPredicate secondPredicate = new DeadlineContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeadlineContainsKeywordsPredicate firstPredicateCopy = new DeadlineContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_deadlineContainsKeywords_returnsTrue() {
        // One keyword
        DeadlineContainsKeywordsPredicate predicate = new DeadlineContainsKeywordsPredicate(Collections.singletonList
                ("2018-03-17"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Multiple keywords
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Only one matching keyword
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));
    }

    @Test
    public void test_deadlineDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DeadlineContainsKeywordsPredicate predicate = new DeadlineContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Non-matching keyword
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2018-03-17"));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Keywords match name, priority and description, but does not match deadline
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2", "Study", "Chapter"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
            .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }
}
