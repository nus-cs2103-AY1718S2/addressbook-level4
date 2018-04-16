package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author CYX28
public class TaskDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskDescription(null));
    }

    @Test
    public void constructor_invalidTaskDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskDescription(invalidDescription));
    }

    @Test
    public void isValidTaskDescription() {
        // null task description
        Assert.assertThrows(NullPointerException.class, () -> TaskDescription.isValidTaskDescription(null));

        // invalid task description
        assertFalse(TaskDescription.isValidTaskDescription("")); // empty string
        assertFalse(TaskDescription.isValidTaskDescription(" ")); // spaces only

        // valid task description
        assertTrue(TaskDescription.isValidTaskDescription("random xyz")); // random string
        assertTrue(TaskDescription.isValidTaskDescription("12345")); // numbers
        assertTrue(TaskDescription.isValidTaskDescription("random 123")); // alphanumeric
        assertTrue(TaskDescription.isValidTaskDescription("Agenda for meeting")); // with capital letters
        assertTrue(TaskDescription.isValidTaskDescription("Super long tasks to be done this week")); // long string
        assertTrue(TaskDescription.isValidTaskDescription("Hello World!")); // non-alphanumeric characters
        assertTrue(TaskDescription.isValidTaskDescription("Attend proposal discussion @ LT100 at 2:00pm"));
    }

}
