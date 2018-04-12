package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a coin identified using it's last displayed index from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_ALIAS = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the coin identified by the index number used in the last coin listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_COIN_SUCCESS = "Selected Coin: %1$s";

    private final CommandTarget target;

    public ViewCommand(CommandTarget target) {
        this.target = target;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Index index = target.toIndex(model.getFilteredCoinList());
            EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
            return new CommandResult(String.format(MESSAGE_SELECT_COIN_SUCCESS, index.getOneBased()));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_TARGET);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.target.equals(((ViewCommand) other).target)); // state check
    }
}
