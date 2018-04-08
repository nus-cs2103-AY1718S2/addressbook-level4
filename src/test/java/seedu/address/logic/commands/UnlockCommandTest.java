package seedu.address.logic.commands;
//@@author crizyli
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
 * Contains integration tests (interaction with the Model) for {@code UnlockCommand}.
 */
public class UnlockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        UnlockCommand firstUnlockCommand = new UnlockCommand();

        // same object -> returns true
        assertTrue(firstUnlockCommand.equals(firstUnlockCommand));

        // different types -> returns false
        assertFalse(firstUnlockCommand.equals(1));

        // null -> returns false
        assertFalse(firstUnlockCommand.equals(null));
    }

    @Test
    public void unlockSuccess() {
        model.setPassword("admin");
        LogicManager logicManager = new LogicManager(model);
        LockCommand testLockCommand = new LockCommand();
        testLockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testLockCommand.execute();
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.setTestMode();
        String expectedMessage = UnlockCommand.MESSAGE_SUCCESS;
        CommandResult commandResult = testUnlockCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void unlockFail() {
        model.setPassword("qwer");
        LogicManager logicManager = new LogicManager(model);
        LockCommand testLockCommand = new LockCommand();
        testLockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testLockCommand.execute();
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.setTestMode();
        String expectedMessage = UnlockCommand.MESSAGE_INCORRECT_PASSWORD;
        CommandResult commandResult = testUnlockCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

}
