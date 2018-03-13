package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.BookShelf;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Book shelf has been cleared!";
    public static final String MESSAGE_WRONG_ACTIVE_LIST = "Items from the current list cannot be cleared.";


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (model.getActiveListType() != ActiveListType.BOOK_SHELF) {
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }

        model.resetData(new BookShelf());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
