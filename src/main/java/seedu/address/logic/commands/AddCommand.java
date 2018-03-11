package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBookListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Adds a book to the book shelf.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a book to the book shelf. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AUTHOR + "AUTHOR... "
            + PREFIX_CATEGORY + "CATEGORY... "
            + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Nudge "
            + PREFIX_AUTHOR + "Cass Sunstein "
            + PREFIX_AUTHOR + "Richard Thaler "
            + PREFIX_CATEGORY + "Psychology "
            + PREFIX_DESCRIPTION + "Nudge is about how we make choices and "
            + "how we can make better ones. ";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the book shelf";

    private final Book toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Book}
     */
    public AddCommand(Book book) {
        requireNonNull(book);
        toAdd = book;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addBook(toAdd);
            EventsCenter.getInstance().post(new ShowBookListRequestEvent());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateBookException e) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
