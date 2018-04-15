//@@author ewaldhew
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Adds value to an existing coin in the book.
 */
public class BuyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "buy";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add value to the coin account identified "
            + "by the index number used in the last coin listing or its code. "
            + "Parameters: TARGET "
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_AMOUNT + "50.0";

    public static final String MESSAGE_BUY_COIN_SUCCESS = "Bought: %1$s";
    public static final String MESSAGE_NOT_BOUGHT = "Invalid code or amount entered.";

    private final CommandTarget target;
    private final Amount amountToAdd;

    private Coin coinToEdit;
    private Coin editedCoin;

    /**
     * @param target      in the filtered coin list to change
     * @param amountToAdd to the coin
     */
    public BuyCommand(CommandTarget target, Amount amountToAdd) {
        requireNonNull(target);

        this.target = target;
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
        return new CommandResult(String.format(MESSAGE_BUY_COIN_SUCCESS, editedCoin));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Coin> lastShownList = model.getFilteredCoinList();

        try {
            Index index = target.toIndex(model.getFilteredCoinList());
            coinToEdit = lastShownList.get(index.getZeroBased());
            editedCoin = createEditedCoin(coinToEdit, amountToAdd);
        } catch (IndexOutOfBoundsException oobe) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_TARGET);
        }
    }

    /**
     * Creates and returns a {@code Coin} with the details of {@code coinToEdit}
     */
    private static Coin createEditedCoin(Coin coinToEdit, Amount amountToAdd) {
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
        return target.equals(e.target)
                && amountToAdd.equals(e.amountToAdd)
                && Objects.equals(coinToEdit, e.coinToEdit);
    }
}
