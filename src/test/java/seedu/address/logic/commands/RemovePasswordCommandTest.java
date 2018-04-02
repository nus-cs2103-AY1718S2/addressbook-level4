package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.util.SecurityUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Password;
import seedu.address.model.UserPrefs;

//@@author yeggasd
/**
 * Contains integration tests (interaction with the Model and Password) and unit tests
 * for {@code RemovePasswordCommand}.
 */
public class RemovePasswordCommandTest {
    private static final byte[] TEST_PASSWORD = (SecurityUtil.hashPassword("test"));
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        model.updatePassword(TEST_PASSWORD);
        model.updatePassword(TEST_PASSWORD);
    }
    @Test
    public void execute_removePassword_success() throws Exception {
        RemovePasswordCommand removepasswordCommand = prepareCommand();
        String expectedMessage = String.format(RemovePasswordCommand.MESSAGE_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.updatePassword(null);
        assertCommandSuccess(removepasswordCommand, model, expectedMessage, expectedModel);
        assertEquals(model.getAddressBook().getPassword(), new Password(null, TEST_PASSWORD));

        expectedModel.updatePassword(null);
        assertCommandSuccess(removepasswordCommand, model, expectedMessage, expectedModel);
        assertEquals(model.getAddressBook().getPassword(), new Password(null, null));
    }

    /**
     * Returns a {@code RemovePasswordCommand}.
     */
    private RemovePasswordCommand prepareCommand() {
        RemovePasswordCommand removepasswordCommand = new RemovePasswordCommand();
        removepasswordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removepasswordCommand;
    }
}
