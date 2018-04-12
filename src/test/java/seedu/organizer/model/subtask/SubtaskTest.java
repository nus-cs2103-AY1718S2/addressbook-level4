package seedu.organizer.model.subtask;

//@@author agus
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Status;
import seedu.organizer.testutil.Assert;

public class SubtaskTest {
    public static final Name VALID_NAME = new Name("hue");
    public static final Status VALID_STATUS = new Status(true);

    @Test
    public void constructorwithouttask_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subtask(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new Subtask(null, VALID_STATUS));
        Assert.assertThrows(NullPointerException.class, () -> new Subtask(null));
    }

    @Test
    public void constructor_nullstatus_statusfalse() {
        Subtask subtask = new Subtask(VALID_NAME, null);
        assertEquals(subtask.getStatus().value, false);
    }

    @Test
    public void equal_allcombination() {
        Subtask subtask1 = new Subtask(VALID_NAME);
        Subtask subtask2 = new Subtask(VALID_NAME, new Status(true));
        Subtask subtask3 = new Subtask(VALID_NAME, new Status(false));
        Subtask subtask4 = new Subtask(new Name("ganteng"), new Status(false));

        assertEquals(subtask1, subtask2);
        assertEquals(subtask1, subtask3);
        assertEquals(subtask2, subtask3);
        assertNotEquals(subtask4, subtask3);
    }
}
