package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;

import seedu.address.commons.core.CoinSubredditList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Adds a coin to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a coin to the coin book. "
            + "Parameters: "
            + PREFIX_CODE + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CODE + "BTC "
            + PREFIX_TAG + "fav "
            + PREFIX_TAG + "fastTransfer";

    public static final String MESSAGE_SUCCESS = "New coin added: %1$s";
    public static final String MESSAGE_DUPLICATE_COIN = "This coin already exists in the coin book";
    public static final String MESSAGE_COIN_CODE_NOT_REGISTERED = "\nThis coin is not currently in CoinBook's registry."
            + "\n - View WILL not be able to fetch you Reddit posts for this coin."
            + "\n - Sync MAY not be able to fetch the latest price. ";

    private final Coin toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Coin}
     */
    public AddCommand(Coin coin) {
        requireNonNull(coin);
        toAdd = coin;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addCoin(toAdd);
            return new CommandResult(getSuccessMessage());
        } catch (DuplicateCoinException e) {
            throw new CommandException(MESSAGE_DUPLICATE_COIN);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    private String getSuccessMessage() {
        return String.format(MESSAGE_SUCCESS
                + (CoinSubredditList.isRecognized(toAdd) ? "" : MESSAGE_COIN_CODE_NOT_REGISTERED), toAdd);
    }
}
