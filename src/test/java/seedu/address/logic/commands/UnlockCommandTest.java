package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.LockManager;
import seedu.address.logic.UndoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.network.Network;

//@@author 592363789
public class UnlockCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalBookShelf(), new UserPrefs());
        // set password + lock
        LockManager.instantiate("testing");
    }

    @After
    public void tearDown() {
        LockManager.instantiate(LockManager.NO_PASSWORD);
    }

    @Test
    public void equals() {

        UnlockCommand unlockCommand = new UnlockCommand("testing");

        UnlockCommand thesameCommand = new UnlockCommand("testing");

        // same value -> returns true
        assertTrue(unlockCommand.equals(thesameCommand));

        // same object -> returns true
        assertTrue(unlockCommand.equals(unlockCommand));

        // null -> returns false
        assertFalse(unlockCommand.equals(null));

        // different commandtypes -> returns false
        assertFalse(unlockCommand.equals(new ClearCommand()));

        // different types -> return false
        assertFalse(unlockCommand.equals(0));

    }

    @Test
    public void sameKeyTest() {
        UnlockCommand unlock = new UnlockCommand("testing");
        unlock.setData(model, mock(Network.class), new CommandHistory(), new UndoStack());
        CommandResult commandResult = unlock.execute();

        assertEquals(UnlockCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void differentKeyTest() {
        UnlockCommand unlock = new UnlockCommand("test");
        unlock.setData(model, mock(Network.class), new CommandHistory(), new UndoStack());
        CommandResult commandResult = unlock.execute();

        assertEquals(UnlockCommand.MESSAGE_WRONG_PASSWORD, commandResult.feedbackToUser);
    }

}
