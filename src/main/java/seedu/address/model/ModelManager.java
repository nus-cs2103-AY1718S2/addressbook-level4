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
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.BookShelfChangedEvent;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the book shelf data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BookShelf bookShelf;
    private final FilteredList<Book> filteredBooks;

    private AddressBook addressBook;
    private FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given bookShelf and userPrefs.
     */
    public ModelManager(ReadOnlyBookShelf bookShelf, UserPrefs userPrefs) {
        super();
        requireAllNonNull(bookShelf, userPrefs);

        logger.fine("Initializing with book shelf: " + bookShelf + " and user prefs " + userPrefs);

        this.bookShelf = new BookShelf(bookShelf);
        this.filteredBooks = new FilteredList<>(this.bookShelf.getBookList());
        this.addressBook = new AddressBook();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    @Deprecated
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        this(new BookShelf(), userPrefs);
        requireNonNull(addressBook);

        logger.fine("Re-initializing address book: " + addressBook);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
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
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
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
        return bookShelf.equals(other.bookShelf) // TODO switch to using book shelf
                && filteredBooks.equals(other.filteredBooks);
    }

    //// deprecated

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

}
