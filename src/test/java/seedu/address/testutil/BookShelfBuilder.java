package seedu.address.testutil;

import seedu.address.model.BookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * A utility class to help with building BookShelf objects.
 * Example usage: <br>
 *     {@code BookShelf bs = new BookShelfBuilder().withBook(new Book(...)).build();}
 */
public class BookShelfBuilder {

    private BookShelf bookShelf;

    public BookShelfBuilder() {
        bookShelf = new BookShelf();
    }

    public BookShelfBuilder(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * Adds a new {@code Book} to the {@code BookShelf} that we are building.
     */
    public BookShelfBuilder withBook(Book book) {
        try {
            bookShelf.addBook(book);
        } catch (DuplicateBookException dpe) {
            throw new IllegalArgumentException("book is expected to be unique.");
        }
        return this;
    }

    public BookShelf build() {
        return bookShelf;
    }
}
