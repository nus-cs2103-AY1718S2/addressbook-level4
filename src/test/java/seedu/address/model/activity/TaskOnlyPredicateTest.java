package seedu.address.model.activity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TaskBuilder;

//@@Author YuanQLLer
public class TaskOnlyPredicateTest {
    @Test
    public void equals() {

        TaskOnlyPredicate firstPredicate = new TaskOnlyPredicate();
        TaskOnlyPredicate secondPredicate = new TaskOnlyPredicate();

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_isEvent_returnsTrue() {
        TaskOnlyPredicate predicate = new TaskOnlyPredicate();

        assertTrue(predicate.test(new TaskBuilder().withName("Task 1").build()));
        assertTrue(predicate.test((Activity) new TaskBuilder().withName("Activity").build()));
    }

    @Test
    public void test_isNotEvent_returnsFalse() {
        TaskOnlyPredicate predicate = new TaskOnlyPredicate();

        assertFalse(predicate.test(new EventBuilder().withName("Task 1").build()));
        assertFalse(predicate.test((Activity) new EventBuilder().withName("Activity Task").build()));
    }
}
