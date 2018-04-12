package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TestUtil.getAddCommandSuccessMessage;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.CoinBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCoinBook(), new UserPrefs());
    }

    @Test
    public void execute_newCoin_success() throws Exception {
        Coin validCoin = new CoinBuilder().build();

        Model expectedModel = new ModelManager(model.getCoinBook(), new UserPrefs());
        expectedModel.addCoin(validCoin);

        assertCommandSuccess(prepareCommand(validCoin, model), model,
                getAddCommandSuccessMessage(validCoin), expectedModel);
    }

    @Test
    public void execute_duplicateCoin_throwsCommandException() {
        Coin coinInList = model.getCoinBook().getCoinList().get(0);
        assertCommandFailure(prepareCommand(coinInList, model), model, AddCommand.MESSAGE_DUPLICATE_COIN);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code coin} into the {@code model}.
     */
    private AddCommand prepareCommand(Coin coin, Model model) {
        AddCommand command = new AddCommand(coin);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
