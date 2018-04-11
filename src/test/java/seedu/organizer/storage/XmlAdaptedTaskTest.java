package seedu.organizer.storage;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.organizer.storage.XmlAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.organizer.testutil.TypicalTasks.SPRINGCLEAN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.testutil.Assert;

public class XmlAdaptedTaskTest {
    private static final XmlAdaptedUser VALID_XML_ADAPTED_USER = new XmlAdaptedUser("admin", "admin");
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRIORITY = "+651234";
    private static final String INVALID_DEADLINE = "2018/09/09";
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_NAME = SPRINGCLEAN.getName().toString();
    private static final String VALID_PRIORITY = SPRINGCLEAN.getUpdatedPriority().toString();
    private static final String VALID_DEADLINE = SPRINGCLEAN.getDeadline().toString();
    private static final String VALID_DATEADDED = SPRINGCLEAN.getDateAdded().toString();
    private static final String VALID_DATECOMPLETED = SPRINGCLEAN.getDateCompleted().toString();
    private static final String VALID_DESCRIPTION = SPRINGCLEAN.getDescription().toString();
    private static final Boolean VALID_STATUS = SPRINGCLEAN.getStatus().value;
    private static final List<XmlAdaptedTag> VALID_TAGS = SPRINGCLEAN.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedSubtask> VALID_SUBTASKS = SPRINGCLEAN.getSubtasks().stream()
            .map(XmlAdaptedSubtask::new)
            .collect(Collectors.toList());
    private static final XmlAdaptedRecurrence VALID_RECURRENCE = new XmlAdaptedRecurrence(false,
            0);

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(SPRINGCLEAN);
        assertEquals(SPRINGCLEAN, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(INVALID_NAME, VALID_PRIORITY, VALID_PRIORITY,
                        VALID_DEADLINE, VALID_DATEADDED, VALID_DATECOMPLETED,
                        VALID_DESCRIPTION, VALID_STATUS, VALID_TAGS,
                        VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_PRIORITY, VALID_PRIORITY,
                VALID_DEADLINE, VALID_DATEADDED,
                VALID_DATECOMPLETED, VALID_DESCRIPTION, VALID_STATUS,
                VALID_TAGS, VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, INVALID_PRIORITY, VALID_PRIORITY,
                        VALID_DEADLINE, VALID_DATEADDED, VALID_DATECOMPLETED,
                        VALID_DESCRIPTION, VALID_STATUS, VALID_TAGS,
                        VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        String expectedMessage = Priority.MESSAGE_PRIORITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, null, VALID_PRIORITY,
                VALID_DEADLINE, VALID_DATEADDED,
                VALID_DATECOMPLETED, VALID_DESCRIPTION, VALID_STATUS,
                VALID_TAGS, VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_PRIORITY, VALID_PRIORITY,
                        INVALID_DEADLINE, VALID_DATEADDED,
                        VALID_DATECOMPLETED, VALID_DESCRIPTION, VALID_STATUS,
                        VALID_TAGS, VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        String expectedMessage = Deadline.MESSAGE_DEADLINE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, VALID_PRIORITY, VALID_PRIORITY,
                null, VALID_DATEADDED,
                VALID_DATECOMPLETED, VALID_DESCRIPTION, VALID_STATUS,
                VALID_TAGS, VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test

    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, VALID_PRIORITY, VALID_PRIORITY,
                VALID_DEADLINE, VALID_DATEADDED,
                VALID_DATECOMPLETED, null, VALID_STATUS,
                VALID_TAGS, VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_PRIORITY, VALID_PRIORITY,
                        VALID_DEADLINE, VALID_DATEADDED,
                        VALID_DATECOMPLETED, VALID_DESCRIPTION, VALID_STATUS,
                        invalidTags, VALID_SUBTASKS, VALID_XML_ADAPTED_USER, VALID_RECURRENCE);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

    //@@author guekling
    @Test
    public void equalsTrue_sameTask() {
        XmlAdaptedTask task = new XmlAdaptedTask(SPRINGCLEAN);
        assertTrue(task.equals(task));
    }

    @Test
    public void equalsFalse() {
        XmlAdaptedTask task = new XmlAdaptedTask(SPRINGCLEAN);
        assertFalse(task.equals(new Integer(1)));
    }
    //@@author
}
