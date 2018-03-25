package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import org.junit.Before;
import org.junit.Test;


/**
 * Contains integration tests (interaction with the Model) for {@code SetPasswordCommand}.
 */
public class SetPasswordCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        model.setPassword("admin");
        LogicManager logicManager = new LogicManager(model);
    }

    @Test
    public void equals() {

        SetPasswordCommand firstCommand= new SetPasswordCommand("admin", "qwe");
        SetPasswordCommand secondCommand = new SetPasswordCommand("admin", "123");
        SetPasswordCommand thirdCommand = new SetPasswordCommand("test", "12345");

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        SetPasswordCommand secondCommandcopy = new SetPasswordCommand("admin", "123");
        assertTrue(secondCommand.equals(secondCommandcopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different value -> returns false
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(thirdCommand.equals(secondCommand));
    }

    @Test
    public void setPasswordFail() {
        //incorrect old password entered.
        SetPasswordCommand command = new SetPasswordCommand("123", "qwe");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SetPasswordCommand.MESSAGE_INCORRECT_OLDPASSWORD;
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);

    }

    @Test
    public void setPasswordSuccess() {

        SetPasswordCommand command = new SetPasswordCommand("admin", "qwe");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SetPasswordCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);

    }
}
