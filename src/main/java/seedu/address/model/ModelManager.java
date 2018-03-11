package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.BookShelfChangedEvent;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Represents the in-memory model of the book shelf data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BookShelf bookShelf;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given bookShelf and userPrefs.
     */
    public ModelManager(ReadOnlyBookShelf bookShelf, UserPrefs userPrefs) {
        super();
        requireAllNonNull(bookShelf, userPrefs);

        logger.fine("Initializing with book shelf: " + bookShelf + " and user prefs " + userPrefs);

        this.bookShelf = new BookShelf(bookShelf);
        this.filteredBooks = new FilteredList<>(this.bookShelf.getBookList());
    }

    public ModelManager() {
        this(new BookShelf(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyBookShelf newData) {
        bookShelf.resetData(newData);
        indicateBookShelfChanged();
    }

    @Override
    public ReadOnlyBookShelf getBookShelf() {
        return bookShelf;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateBookShelfChanged() {
        raise(new BookShelfChangedEvent(bookShelf));
    }

    @Override
    public void deleteBook(Book target) throws BookNotFoundException {
        bookShelf.removeBook(target);
        indicateBookShelfChanged();
    }

    @Override
    public void addBook(Book book) throws DuplicateBookException {
        bookShelf.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        indicateBookShelfChanged();
    }

    @Override
    public void updateBook(Book target, Book editedBook) throws BookNotFoundException, DuplicateBookException {
        requireAllNonNull(target, editedBook);

        bookShelf.updateBook(target, editedBook);
        indicateBookShelfChanged();
    }

    //=========== Filtered Book List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Book} backed by the internal list of
     * {@code bookShelf}
     */
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return FXCollections.unmodifiableObservableList(filteredBooks);
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return bookShelf.equals(other.bookShelf)
                && filteredBooks.equals(other.filteredBooks);
    }

}
