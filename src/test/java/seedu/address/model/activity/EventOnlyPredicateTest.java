package seedu.address.model.activity;

import org.junit.Test;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TaskBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//Author YuanQLLer
public class EventOnlyPredicateTest {
    @Test
    public void equals() {

        EventOnlyPredicate firstPredicate = new EventOnlyPredicate();
        EventOnlyPredicate secondPredicate = new EventOnlyPredicate();

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
        EventOnlyPredicate predicate = new EventOnlyPredicate();

        assertTrue(predicate.test(new EventBuilder().withName("Event 1").build()));
        assertTrue(predicate.test((Activity) new EventBuilder().withName("Activity").build()));
    }

    @Test
    public void test_isNotEvent_returnsFalse() {
        EventOnlyPredicate predicate = new EventOnlyPredicate();

        assertFalse(predicate.test(new TaskBuilder().withName("Task 1").build()));
        assertFalse(predicate.test((Activity) new TaskBuilder().withName("Activity Task").build()));
    }

}
