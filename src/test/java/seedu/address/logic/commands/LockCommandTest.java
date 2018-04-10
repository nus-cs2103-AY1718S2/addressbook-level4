package seedu.address.logic.commands;
//@@author crizyli
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code LockCommand}.
 */
public class LockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        LockCommand firstLockCommand = new LockCommand();
        LockCommand secondLockCommand = new LockCommand();

        // same object -> returns true
        assertTrue(firstLockCommand.equals(firstLockCommand));

        // different types -> returns false
        assertFalse(firstLockCommand.equals(1));

        // null -> returns false
        assertFalse(firstLockCommand.equals(null));

    }

    @Test
    public void lockSuccess() {

        LockCommand testLockCommand = new LockCommand();
        testLockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = LockCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = testLockCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }



}
