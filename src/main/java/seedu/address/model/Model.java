package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    @Deprecated
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyBookShelf newData);

    /** Clears existing backing model and replaces with the provided new data. */
    @Deprecated
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the BookShelf */
    ReadOnlyBookShelf getBookShelf();

    /** Deletes the given book. */
    void deleteBook(Book target) throws BookNotFoundException;

    /** Adds the given book */
    void addBook(Book book) throws DuplicateBookException;

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     *
     * @throws BookNotFoundException if {@code target} could not be found in the list.
     * @throws DuplicateBookException if updating the book details causes the book to be equivalent to
     *      another existing book in the list.
     */
    void updateBook(Book target, Book editedBook)
            throws BookNotFoundException, DuplicateBookException;

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);

    //// deprecated

    /** Returns the AddressBook */
    @Deprecated
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    @Deprecated
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    @Deprecated
    void addPerson(Person person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    @Deprecated
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    @Deprecated
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Deprecated
    void updateFilteredPersonList(Predicate<Person> predicate);

}
