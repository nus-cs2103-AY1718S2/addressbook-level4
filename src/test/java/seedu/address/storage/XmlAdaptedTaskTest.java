package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTasks.EXAMPLE1;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.Assert;
//@@author a-shakra
public class XmlAdaptedTaskTest {
    private static final String VALID_DESCRIPTION = "A description";

    private static final String VALID_DURATION = EXAMPLE1.getDuration();
    private static final String VALID_DATEANDTIME = EXAMPLE1.getTaskDateTime().toString(); //Double check this

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(EXAMPLE1);
        assertEquals(EXAMPLE1, task.toModelType());
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_DURATION, VALID_DATEANDTIME);
        String expectedMessage = "Task's Tasks Should have a non-empty description field is missing!";
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DESCRIPTION, null, VALID_DATEANDTIME);
        String expectedMessage = "Task's Duration must be a non-null value field is missing!";
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDateAndTime_throwsNullPointerException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_DESCRIPTION, VALID_DURATION, null);
        String expectedMessage = "text";
        Assert.assertThrows(NullPointerException.class, expectedMessage, task::toModelType);
    }

}
