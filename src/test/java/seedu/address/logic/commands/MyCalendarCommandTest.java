package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code MyCalendarCommand}.
 */
public class MyCalendarCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        MyCalendarCommand myCalendarCommand = new MyCalendarCommand();

        // same object -> returns true
        assertTrue(myCalendarCommand.equals(myCalendarCommand));

        // different types -> returns false
        assertFalse(myCalendarCommand.equals(1));

        // null -> returns false
        assertFalse(myCalendarCommand.equals(null));

    }

    @Test
    public void viewSuccess() {
        MyCalendarCommand testCommand = new MyCalendarCommand();
        testCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = MyCalendarCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = testCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
