package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;

/**
 * Unmodifiable view of a book shelf
 */
public interface ReadOnlyBookShelf {

    /**
     * Returns an unmodifiable view of the book list.
     * This list will not contain any duplicate books.
     */
    ObservableList<Book> getBookList();

}
