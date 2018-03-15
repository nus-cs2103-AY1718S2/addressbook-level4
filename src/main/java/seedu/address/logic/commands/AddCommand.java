package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.network.ApiBookDetailsRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActiveListType;
import seedu.address.model.book.Book;

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
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(toAdd);
        if (model.getActiveListType() != ActiveListType.SEARCH_RESULTS) {
            throw new CommandException(MESSAGE_WRONG_ACTIVE_LIST);
        }
        LogsCenter.getLogger(AddCommand.class).info("GID: " + toAdd.getGid().gid);
        EventsCenter.getInstance().post(new ApiBookDetailsRequestEvent(toAdd.getGid().gid));
        return new CommandResult(MESSAGE_ADDING);

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getSearchResultsList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        toAdd = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && this.targetIndex.equals(((AddCommand) other).targetIndex)
                && Objects.equals(this.toAdd, ((AddCommand) other).toAdd));
    }
}
