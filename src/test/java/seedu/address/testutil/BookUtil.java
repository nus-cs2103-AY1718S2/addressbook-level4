package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.book.Book;

/**
 * A utility class for Book.
 */
@Deprecated
public class BookUtil {

    /**
     * Returns an add command string for adding the {@code book}.
     */
    public static String getAddCommand(Book book) {
        return AddCommand.COMMAND_WORD + " " + getBookDetails(book);
    }

    /**
     * Returns the part of command string for the given {@code book}'s details.
     */
    public static String getBookDetails(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + book.getTitle().title + " ");
        book.getAuthors().stream().forEach(
            s -> sb.append(PREFIX_AUTHOR + s.fullName + " ")
        );
        book.getCategories().stream().forEach(
            s -> sb.append(PREFIX_CATEGORY + s.category + " ")
        );
        sb.append(PREFIX_DESCRIPTION + book.getDescription().description + " ");
        return sb.toString();
    }
}
