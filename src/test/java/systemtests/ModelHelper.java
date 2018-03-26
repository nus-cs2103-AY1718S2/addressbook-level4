package systemtests;

import java.util.List;

import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {

    /**
     * Updates {@code model}'s search results to be {@code results}.
     */
    public static void setSearchResults(Model model, List<Book> results) {
        BookShelf searchResults = new BookShelf();
        try {
            searchResults.setBooks(results);
        } catch (DuplicateBookException e) {
            throw new AssertionError("Results contains duplicate books.");
        }
        model.updateSearchResults(searchResults);
    }

    /**
     * Updates {@code model}'s recent books to be {@code results}.
     */
    public static void setRecentBooks(Model model, List<Book> results) {
        for (int index = results.size() - 1; index >= 0; index--) {
            model.addRecentBook(results.get(index));
        }
    }

}
