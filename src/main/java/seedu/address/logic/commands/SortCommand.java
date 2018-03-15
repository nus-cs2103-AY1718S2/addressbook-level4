package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

/**
 * Sorts all persons in the address book in alphabetical order.
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons in alphabetical order";

    @Override
    protected CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
