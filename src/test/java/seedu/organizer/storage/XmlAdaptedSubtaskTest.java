package seedu.organizer.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.util.SampleDataUtil;
import seedu.organizer.testutil.Assert;

public class XmlAdaptedSubtaskTest {

    public static final String NAME = "Jennifer";
    public static final boolean STATUS = false;

    public static final String OTHER_NAME = "Jessica";

    @Test
    public void equal_defaultconstructor() {
        XmlAdaptedSubtask subtask = new XmlAdaptedSubtask(NAME, STATUS);
        XmlAdaptedSubtask otherSubtask = new XmlAdaptedSubtask(NAME, STATUS);
        assertEquals(subtask, otherSubtask);
        assertEquals(subtask, subtask);

        XmlAdaptedSubtask diffSubtask = new XmlAdaptedSubtask(OTHER_NAME, STATUS);
        assertNotEquals(subtask, diffSubtask);
    }

    @Test
    public void equal_subtaskconstructor() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");

        XmlAdaptedSubtask subtask = new XmlAdaptedSubtask(subtasks.get(0));
        XmlAdaptedSubtask otherSubtask = new XmlAdaptedSubtask(subtasks.get(0));
        assertEquals(subtask, otherSubtask);
        assertEquals(subtask, subtask);

        XmlAdaptedSubtask diffSubtask = new XmlAdaptedSubtask(subtasks.get(1));
        assertNotEquals(subtask, diffSubtask);
    }

    @Test
    public void toModel_invalidName() {
        Assert.assertThrows(
            IllegalValueException.class, () -> new XmlAdaptedSubtask("", false).toModelType()
        );
    }

}
