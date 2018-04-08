package seedu.organizer.model.task.predicates;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.organizer.model.user.User;
import seedu.organizer.testutil.TaskBuilder;

//@@author dominickenn
public class TaskByUserPredicateTest {

    @Test
    public void equals() {
        User firstPredicate = new User("bobby", "bobby");
        User secondPredicate = new User("mary123", "mary123");

        TaskByUserPredicate firstUserPredicate = new TaskByUserPredicate(firstPredicate);
        TaskByUserPredicate secondUserPredicate = new TaskByUserPredicate(secondPredicate);

        // same object -> returns true
        assertTrue(firstUserPredicate.equals(firstUserPredicate));

        // same values -> returns true
        TaskByUserPredicate firstUserPredicateCopy = new TaskByUserPredicate(firstPredicate);
        assertTrue(firstUserPredicate.equals(firstUserPredicateCopy));

        // different types -> returns false
        assertFalse(firstUserPredicate.equals(1));

        // null -> returns false
        assertFalse(firstUserPredicate.equals(null));

        // different user -> returns false
        assertFalse(firstUserPredicate.equals(secondUserPredicate));
    }

    @Test
    public void test_taskContainsUser_returnsTrue() {
        TaskByUserPredicate predicate = new TaskByUserPredicate(new User("admin", "admin"));
        assertTrue(predicate.test(new TaskBuilder().build()));
    }

    @Test
    public void test_taskDoesNotContainUser_returnsFalse() {
        TaskByUserPredicate predicate = new TaskByUserPredicate(new User("doesntexist", "doesntexist"));
        assertFalse(predicate.test(new TaskBuilder().build()));
    }
}

