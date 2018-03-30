//@@author ewaldhew
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Adds value to an existing coin in the book.
 */
public class BuyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "buy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add value to the coin account identified "
            + "by the index number used in the last coin listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + "50.0";

    public static final String MESSAGE_BUY_COIN_SUCCESS = "Bought: %1$s";
    public static final String MESSAGE_NOT_BOUGHT = "No amount entered.";

    private final Index index;
    private final double amountToAdd;

    private Coin coinToEdit;
    private Coin editedCoin;

    /**
     * @param index of the coin in the filtered coin list to change
     * @param amountToAdd to the coin
     */
    public BuyCommand(Index index, double amountToAdd) {
        requireNonNull(index);

        this.index = index;
        this.amountToAdd = amountToAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCoin(coinToEdit, editedCoin);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException("Unexpected code path!");
        } catch (CoinNotFoundException pnfe) {
            throw new AssertionError("The target coin cannot be missing");
        }
        model.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        return new CommandResult(String.format(MESSAGE_BUY_COIN_SUCCESS, coinToEdit));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Coin> lastShownList = model.getFilteredCoinList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COIN_DISPLAYED_INDEX);
        }

        coinToEdit = lastShownList.get(index.getZeroBased());
        editedCoin = createEditedCoin(coinToEdit, amountToAdd);
    }

    /**
     * Creates and returns a {@code Coin} with the details of {@code coinToEdit}
     * edited with {@code editCoinDescriptor}.
     */
    private static Coin createEditedCoin(Coin coinToEdit, double amountToAdd) {
        assert coinToEdit != null;

        Coin editedCoin = new Coin(coinToEdit);
        editedCoin.addTotalAmountBought(amountToAdd);

        return editedCoin;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyCommand)) {
            return false;
        }

        // state check
        BuyCommand e = (BuyCommand) other;
        return index.equals(e.index)
                && amountToAdd == e.amountToAdd
                && Objects.equals(coinToEdit, e.coinToEdit);
    }
}
