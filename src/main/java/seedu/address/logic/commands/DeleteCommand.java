package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DeselectBookRequestEvent;
import seedu.address.commons.events.ui.ReselectBookRequestEvent;
import seedu.address.commons.util.CommandUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

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

    public static final String UNDO_SUCCESS = "Successfully undone deleting of %s.";
    public static final String UNDO_FAILURE = "Failed to undo deleting of %s.";

    private final Index targetIndex;

    private Book bookToDelete;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireAllNonNull(model, bookToDelete);

        try {
            EventsCenter.getInstance().post(new DeselectBookRequestEvent());
            model.deleteBook(bookToDelete);
            EventsCenter.getInstance().post(new ReselectBookRequestEvent());
        } catch (BookNotFoundException pnfe) {
            throw new AssertionError("The target book cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        checkActiveListType();
        CommandUtil.checkValidIndex(model, targetIndex);

        bookToDelete = CommandUtil.getBook(model, targetIndex);
    }

    /**
     * Throws a {@link CommandException} if the active list type is not supported by this command.
     */
    private void checkActiveListType() throws CommandException {
        requireNonNull(model);

        if (model.getActiveListType() != ActiveListType.BOOK_SHELF) {
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }
    }

    @Override
    protected String undo() {
        requireAllNonNull(model, bookToDelete);

        try {
            model.addBook(bookToDelete);
            return String.format(UNDO_SUCCESS, bookToDelete);
        } catch (DuplicateBookException e) {
            // Should never end up here
            return String.format(UNDO_FAILURE, bookToDelete);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.bookToDelete, ((DeleteCommand) other).bookToDelete));
    }
}
