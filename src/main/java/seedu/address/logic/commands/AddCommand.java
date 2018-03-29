package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Adds a book to the book shelf.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the book identified by the index number used in the current search result.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ADDING = "Adding the book into your book shelf...";
    public static final String MESSAGE_ADD_FAIL = "Failed to add book into your book shelf. "
            + "Make sure you are connected to the Internet.";
    public static final String MESSAGE_SUCCESS = "New book added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the book shelf";
    public static final String MESSAGE_WRONG_ACTIVE_LIST = "Items from the current list cannot be added.";

    private final Index targetIndex;

    private Book toAdd;

    public AddCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(toAdd);

        makeAsyncBookDetailsRequest();
        return new CommandResult(MESSAGE_ADDING);
    }

    /**
     * Makes an asynchronous request to retrieve book details.
     */
    private void makeAsyncBookDetailsRequest() {
        network.getBookDetails(toAdd.getGid().gid)
                .thenAccept(this::addBook)
                .exceptionally(e -> {
                    EventsCenter.getInstance().post(new NewResultAvailableEvent(AddCommand.MESSAGE_ADD_FAIL));
                    return null;
                });
    }

    /**
     * Adds the given book to the book shelf and posts events to update the UI.
     */
    private void addBook(Book book) {
        Platform.runLater(() -> {
            try {
                model.addBook(book);
                EventsCenter.getInstance().post(new NewResultAvailableEvent(
                        String.format(AddCommand.MESSAGE_SUCCESS, book)));
            } catch (DuplicateBookException e) {
                EventsCenter.getInstance().post(new NewResultAvailableEvent(AddCommand.MESSAGE_DUPLICATE_BOOK));
            }
        });
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireNonNull(model);

        switch (model.getActiveListType()) {
        case SEARCH_RESULTS:
        {
            List<Book> searchResultsList = model.getSearchResultsList();

            if (targetIndex.getZeroBased() >= searchResultsList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
            }

            toAdd = searchResultsList.get(targetIndex.getZeroBased());
            break;
        }
        case RECENT_BOOKS:
        {
            List<Book> recentBooksList = model.getRecentBooksList();

            if (targetIndex.getZeroBased() >= recentBooksList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
            }

            toAdd = recentBooksList.get(targetIndex.getZeroBased());
            break;
        }
        default:
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && this.targetIndex.equals(((AddCommand) other).targetIndex)
                && Objects.equals(this.toAdd, ((AddCommand) other).toAdd));
    }
}
