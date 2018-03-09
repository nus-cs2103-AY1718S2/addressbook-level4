package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.BookShelf;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Book shelf has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new BookShelf());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
