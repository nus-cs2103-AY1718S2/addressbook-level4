//@@author ewaldhew
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCoins.ALIS;
import static seedu.address.testutil.TypicalCoins.BOS;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Coin;


public class SpawnNotificationCommandTest {

    @Test
    public void equals() throws Exception {
        SpawnNotificationCommand firstCommand = prepareCommand(ALIS);
        SpawnNotificationCommand secondCommand = prepareCommand(BOS);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        SpawnNotificationCommand firstCommandCopy = prepareCommand(ALIS);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different coin -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code SpawnNotificationCommand} with the parameter {@code coin}.
     */
    private SpawnNotificationCommand prepareCommand(Coin coin) {
        SpawnNotificationCommand command = new SpawnNotificationCommand(coin.toString());
        command.setData(new ModelManager(getTypicalCoinBook(), new UserPrefs()),
                new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
