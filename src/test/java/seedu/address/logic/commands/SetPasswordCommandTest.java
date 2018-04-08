package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SetPasswordCommand}.
 */
public class SetPasswordCommandTest {

    @Test
    public void equals() {

        SetPasswordCommand firstCommand = new SetPasswordCommand();

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));
    }

    @Test
    public void setPasswordFail() {
        //incorrect old password entered.
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LogicManager logicManager = new LogicManager(model);
        LogicManager.setPassword("psw");
        model.setPassword("psw");
        SetPasswordCommand command = new SetPasswordCommand();
        command.setTestMode();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SetPasswordCommand.MESSAGE_INCORRECT_OLDPASSWORD;
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);

    }

    @Test
    public void setPasswordSuccess() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        LogicManager logicManager = new LogicManager(model);
        LogicManager.setPassword("admin");
        model.setPassword("admin");
        SetPasswordCommand command = new SetPasswordCommand();
        command.setTestMode();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SetPasswordCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);

    }
}
