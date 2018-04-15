package seedu.address.logic.commands;

/**
 * Sorts all coins in Coin List by alphabetical order and displays sorted list to user
 */
//@@author neilish3re
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "order";
    public static final String COMMAND_ALIAS = "o";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all the coins the user has input into the Coin Book as a list"
            + " sorted by lexicographical order of cryptocurrency coin name\n";

    public static final String MESSAGE_SUCCESS = "Sorted all coins lexicographically";

    private final boolean isSort;

    public SortCommand(boolean isSort) {
        this.isSort = isSort;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortCoinList(isSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

