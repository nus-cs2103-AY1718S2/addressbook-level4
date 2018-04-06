package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandTestUtil.ModelStub;
import seedu.address.model.CoinBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCoinBook;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.testutil.CoinBuilder;

public class BuyCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTarget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new BuyCommand(null, 0);
    }

    @Test
    public void execute_valueAdded() throws Exception {
        ModelStubAcceptingCoinUpdated modelStub = new ModelStubAcceptingCoinUpdated();
        Coin validCoin = new CoinBuilder().build();
        Coin editedCoin = new Coin(validCoin);
        double amountToBuy = 10.0;

        modelStub.addCoin(validCoin);
        editedCoin.addTotalAmountBought(amountToBuy);
        CommandResult commandResult = getBuyCommandForCoin(validCoin, amountToBuy, modelStub).execute();

        assertEquals(String.format(BuyCommand.MESSAGE_BUY_COIN_SUCCESS, editedCoin), commandResult.feedbackToUser);
    }

    @Test
    public void equals() {
        Coin alice = new CoinBuilder().withName("ALC").build();
        Coin bob = new CoinBuilder().withName("BOB").build();
        BuyCommand buyAliceCommand = new BuyCommand(new CommandTarget(alice.getCode()), 10);
        BuyCommand buyBobCommand = new BuyCommand(new CommandTarget(bob.getCode()), 10);

        // same object -> returns true
        assertTrue(buyAliceCommand.equals(buyAliceCommand));

        // same values -> returns true
        BuyCommand buyAliceCommandCopy = new BuyCommand(new CommandTarget(alice.getCode()), 10);
        assertTrue(buyAliceCommand.equals(buyAliceCommandCopy));

        // different types -> returns false
        assertFalse(buyAliceCommand.equals(1));

        // null -> returns false
        assertFalse(buyAliceCommand.equals(null));

        // different coin -> returns false
        assertFalse(buyAliceCommand.equals(buyBobCommand));

        // same coin but different value -> returns false
        buyAliceCommandCopy = new BuyCommand(new CommandTarget(alice.getCode()), 3);
        assertFalse(buyAliceCommand.equals(buyAliceCommandCopy));

    }

    /**
     * Generates a new BuyCommand with the details of the given coin.
     */
    private BuyCommand getBuyCommandForCoin(Coin coin, double amountToAdd, Model model) {
        BuyCommand command = new BuyCommand(new CommandTarget(coin.getCode()), amountToAdd);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always accept the coin being updated.
     */
    private class ModelStubAcceptingCoinUpdated extends ModelStub {
        final ArrayList<Coin> coinsAdded = new ArrayList<>();

        @Override
        public void addCoin(Coin coin) throws DuplicateCoinException {
            coinsAdded.add(coin);
        }

        @Override
        public ObservableList<Coin> getFilteredCoinList() {
            return FXCollections.observableList(coinsAdded);
        }

        @Override
        public void updateCoin(Coin coin, Coin editedCoin) throws DuplicateCoinException {
            requireNonNull(coin);
            coinsAdded.add(coin);
        }

        @Override
        public void updateFilteredCoinList(Predicate<Coin> predicate) {
            return;
        }

        @Override
        public ReadOnlyCoinBook getCoinBook() {
            return new CoinBook();
        }
    }


}
