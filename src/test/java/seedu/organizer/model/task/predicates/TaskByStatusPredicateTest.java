package seedu.organizer.model.task.predicates;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.organizer.model.task.Status;
import seedu.organizer.testutil.TaskBuilder;

//@@author dominickenn
public class TaskByStatusPredicateTest {

    @Test
    public void equals() {
        Status firstPredicate = new Status(true);
        Status secondPredicate = new Status(false);

        TaskByStatusPredicate firstStatusPredicate = new TaskByStatusPredicate(firstPredicate);
        TaskByStatusPredicate secondStatusPredicate = new TaskByStatusPredicate(secondPredicate);

        // same object -> returns true
        assertTrue(firstStatusPredicate.equals(firstStatusPredicate));

        // same values -> returns true
        TaskByStatusPredicate firstStatusPredicateCopy = new TaskByStatusPredicate(firstPredicate);
        assertTrue(firstStatusPredicate.equals(firstStatusPredicateCopy));

        // different types -> returns false
        assertFalse(firstStatusPredicate.equals(1));

        // null -> returns false
        assertFalse(firstStatusPredicate.equals(null));

        // different status -> returns false
        assertFalse(firstStatusPredicate.equals(secondStatusPredicate));
    }

    @Test
    public void test_taskContainsStatus_returnsTrue() {
        TaskByStatusPredicate predicate = new TaskByStatusPredicate(new Status(false));
        assertTrue(predicate.test(new TaskBuilder().build()));
    }

    @Test
    public void test_taskDoesNotContainStatus_returnsFalse() {
        TaskByStatusPredicate predicate = new TaskByStatusPredicate(new Status(true));
        assertFalse(predicate.test(new TaskBuilder().build()));
    }
}
