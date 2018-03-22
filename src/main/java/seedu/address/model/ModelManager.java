package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.BookShelfChangedEvent;
import seedu.address.commons.events.ui.BookListSelectionChangedEvent;
import seedu.address.commons.events.ui.SearchResultsSelectionChangedEvent;
import seedu.address.model.book.Book;
import seedu.address.model.book.UniqueBookCircularList;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Represents the in-memory model of the book shelf data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private ActiveListType activeListType;
    private final BookShelf bookShelf;
    private final FilteredList<Book> filteredBooks;
    private final BookShelf searchResults;
    private final UniqueBookCircularList recentBooks;

    /**
     * Initializes a ModelManager with the given bookShelf, userPrefs and recentBooksList.
     */
    public ModelManager(ReadOnlyBookShelf bookShelf, UserPrefs userPrefs,
                        ReadOnlyBookShelf recentBooksList) {
        super();
        requireAllNonNull(bookShelf, userPrefs, recentBooksList);

        logger.fine("Initializing with book shelf: " + bookShelf + " and user prefs " + userPrefs
                + " and recent books: " + recentBooksList);

        this.activeListType = ActiveListType.BOOK_SHELF;
        this.bookShelf = new BookShelf(bookShelf);
        this.filteredBooks = new FilteredList<>(this.bookShelf.getBookList());
        this.searchResults = new BookShelf();

        this.recentBooks = new UniqueBookCircularList();
        List<Book> list = recentBooksList.getBookList();
        for (int index = list.size() - 1; index >= 0; index--) {
            this.recentBooks.addToFront(list.get(index));
        }
    }

    public ModelManager(ReadOnlyBookShelf bookShelf, UserPrefs userPrefs) {
        this(bookShelf, userPrefs, new BookShelf());
    }

    public ModelManager() {
        this(new BookShelf(), new UserPrefs());
    }

    @Override
    public ActiveListType getActiveListType() {
        return activeListType;
    }

    @Override
    public void setActiveListType(ActiveListType type) {
        this.activeListType = type;
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

    //=========== Search Results ===========================================================================

    /**
     * Returns an unmodifable view of the list of {@code Book} backed by the internal list of {@code searchResults}.
     * */
    @Override
    public ObservableList<Book> getSearchResultsList() {
        return FXCollections.unmodifiableObservableList(searchResults.getBookList());
    }

    @Override
    public void updateSearchResults(ReadOnlyBookShelf newResults) {
        searchResults.resetData(newResults);
    }

    //=========== Recent books ===========================================================================

    @Override
    public ObservableList<Book> getRecentBooksList() {
        return FXCollections.unmodifiableObservableList(recentBooks.asObservableList());
    }

    public ReadOnlyBookShelf getRecentBooksListAsBookShelf() {
        BookShelf bookShelf = new BookShelf();
        try {
            bookShelf.setBooks(getRecentBooksList());
        } catch (DuplicateBookException e) {
            logger.warning("Should never throw DuplicateBookException");
        }
        return bookShelf;
    }

    @Override
    public void addRecentBook(Book newBook) {
        requireNonNull(newBook);
        recentBooks.addToFront(newBook);
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
                && filteredBooks.equals(other.filteredBooks)
                && searchResults.equals(other.searchResults)
                && recentBooks.equals(other.recentBooks);
    }


    @Subscribe
    private void handleSearchResultsSelectionChangedEvent(SearchResultsSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        addRecentBook(event.getNewSelection().book);
    }

    @Subscribe
    private void handleBookListSelectionChangedEvent(BookListSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        addRecentBook(event.getNewSelection().book);
    }
}
