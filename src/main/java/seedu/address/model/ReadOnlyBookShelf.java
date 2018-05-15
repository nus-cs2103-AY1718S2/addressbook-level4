package seedu.address.model;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;

/**
 * Unmodifiable view of a book shelf
 */
public interface ReadOnlyBookShelf {

    /**
     * Returns an {@code Optional} containing the book that matches the given {@code isbn}.
     * Returns an empty {@code Optional} if there is no matching book.
     */
    Optional<Book> getBookByIsbn(Isbn isbn);

    /**
     * Returns an unmodifiable view of the book list.
     * This list will not contain any duplicate books.
     */
    ObservableList<Book> getBookList();

    /**
     * Returns the number of books contained in this book shelf.
     */
    int size();
}
