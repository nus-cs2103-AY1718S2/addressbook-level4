package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyBookShelf previousBookShelf;
    private ReadOnlyBookShelf previousSearchResults;
    private ActiveListType previousActiveListType;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#bookShelf}.
     */
    private void saveBookShelfSnapshot() {
        requireNonNull(model);
        this.previousBookShelf = new BookShelf(model.getBookShelf());
        BookShelf bs = new BookShelf();
        try {
            bs.setBooks(model.getSearchResultsList());
            this.previousSearchResults = bs;
        } catch (DuplicateBookException e) {
            // Don't store previousSearchResults
        }
        this.previousActiveListType = model.getActiveListType();
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the BookShelf to the state before this command
     * was executed and updates the filtered book list to
     * show all books.
     */
    protected final void undo() {
        requireAllNonNull(model, previousBookShelf);
        model.resetData(previousBookShelf);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    /**
     * Executes the command.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            ActiveListType curActiveList = model.getActiveListType();
            model.setActiveListType(previousActiveListType);
            if (previousSearchResults != null) {
                model.updateSearchResults(previousSearchResults);
            }
            executeUndoableCommand();
            model.setActiveListType(curActiveList);
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveBookShelfSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
