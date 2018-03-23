package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

/**
 * Lists all coins in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all coins";


    @Override
    public CommandResult execute() {
        model.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
