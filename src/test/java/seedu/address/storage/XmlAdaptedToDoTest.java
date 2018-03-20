package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT;
import static seedu.address.storage.XmlAdaptedToDo.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalToDos.TODO_A;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.Content;
import seedu.address.testutil.Assert;

public class XmlAdaptedToDoTest {

    @Test
    public void toModelType_validToDoDetails_returnsToDo() throws Exception {
        XmlAdaptedToDo todo = new XmlAdaptedToDo(TODO_A);
        assertEquals(TODO_A, todo.toModelType());
    }

    @Test
    public void toModelType_invalidContent_throwsIllegalValueException() {
        XmlAdaptedToDo todo =
                new XmlAdaptedToDo(INVALID_CONTENT);
        String expectedMessage = Content.MESSAGE_CONTENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }

    @Test
    public void toModelType_nullContent_throwsIllegalValueException() {
        XmlAdaptedToDo todo = new XmlAdaptedToDo((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, todo::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedToDo todoA = new XmlAdaptedToDo(TODO_A);
        XmlAdaptedToDo todoB = new XmlAdaptedToDo(TODO_A);
        assertTrue(todoA.equals(todoA));
        assertFalse(todoA.equals(1));
        assertTrue(todoA.equals(todoB));
    }
}
