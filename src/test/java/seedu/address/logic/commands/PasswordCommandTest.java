package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.util.SecurityUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class PasswordCommandTest {
    private static final String TEST_PASSWORD = "test";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        PasswordCommand passwordCommand = prepareCommand(TEST_PASSWORD + "1");

        String expectedMessage = String.format(PasswordCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePassword(SecurityUtil.hashPassword(TEST_PASSWORD + "1"));

        assertCommandSuccess(passwordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        PasswordCommand passwordFirstCommand = prepareCommand(TEST_PASSWORD);
        PasswordCommand passwordSecondCommand = prepareCommand(TEST_PASSWORD + "1");

        // same object -> returns true
        assertTrue(passwordFirstCommand.equals(passwordFirstCommand));

        // same values -> returns true
        PasswordCommand passwordFirstCommandCopy = prepareCommand(TEST_PASSWORD);
        assertTrue(passwordFirstCommand.equals(passwordFirstCommandCopy));

        // different types -> returns false
        assertFalse(passwordFirstCommand.equals(1));

        // null -> returns false
        assertFalse(passwordFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(passwordFirstCommand.equals(passwordSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private PasswordCommand prepareCommand(String password) {
        PasswordCommand passwordCommand = new PasswordCommand(password);
        passwordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return passwordCommand;
    }
}
