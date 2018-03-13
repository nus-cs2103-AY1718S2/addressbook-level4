package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;

/**
 * Deletes a book identified using it's last displayed index from the book shelf.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the index number used in the current book listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";
    public static final String MESSAGE_WRONG_ACTIVE_LIST = "Items from the current list cannot be deleted.";

    private final Index targetIndex;

    private Book bookToDelete;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(bookToDelete);
        if (model.getActiveListType() != ActiveListType.BOOK_SHELF) {
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }

        try {
            model.deleteBook(bookToDelete);
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("The target book cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        bookToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.bookToDelete, ((DeleteCommand) other).bookToDelete));
    }
}
