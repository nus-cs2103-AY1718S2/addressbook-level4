package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstCoin;
import static seedu.address.logic.commands.CommandTestUtil.showCoinAtIndex;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalCoinBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalCoinBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstCoin(expectedModel);
        assertEquals(expectedModel, model);

        showCoinAtIndex(model, INDEX_FIRST_COIN);

        // undo() should cause the model's filtered list to show all coins
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalCoinBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showCoinAtIndex(model, INDEX_FIRST_COIN);

        // redo() should cause the model's filtered list to show all coins
        dummyCommand.redo();
        deleteFirstCoin(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first coin in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Coin coinToDelete = model.getFilteredCoinList().get(0);
            try {
                model.deleteCoin(coinToDelete);
            } catch (CoinNotFoundException pnfe) {
                fail("Impossible: coinToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
