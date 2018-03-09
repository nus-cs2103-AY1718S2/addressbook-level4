package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_bookAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();

        CommandResult commandResult = getAddCommandForBook(validBook, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validBook), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validBook), modelStub.booksAdded);
    }

    @Test
    public void execute_duplicateBook_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateBookException();
        Book validBook = new BookBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_BOOK);

        getAddCommandForBook(validBook, modelStub).execute();
    }

    @Test
    public void equals() {
        Book one = new BookBuilder().withTitle("One").build();
        Book two = new BookBuilder().withTitle("Two").build();
        AddCommand addOneCommand = new AddCommand(one);
        AddCommand addTwoCommand = new AddCommand(two);

        // same object -> returns true
        assertTrue(addOneCommand.equals(addOneCommand));

        // same values -> returns true
        AddCommand addOneCommandCopy = new AddCommand(one);
        assertTrue(addOneCommand.equals(addOneCommandCopy));

        // different types -> returns false
        assertFalse(addOneCommand.equals(1));

        // null -> returns false
        assertFalse(addOneCommand.equals(null));

        // different book -> returns false
        assertFalse(addOneCommand.equals(addTwoCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given book.
     */
    private AddCommand getAddCommandForBook(Book book, Model model) {
        AddCommand command = new AddCommand(book);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void resetData(ReadOnlyBookShelf newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyBookShelf getBookShelf() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteBook(Book target) throws BookNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addBook(Book book) throws DuplicateBookException {
            fail("This method should not be called.");
        }

        @Override
        public void updateBook(Book target, Book editedBook) throws BookNotFoundException, DuplicateBookException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            fail("This method should not be called.");
        }

        //// deprecated

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateBookException when trying to add a book.
     */
    private class ModelStubThrowingDuplicateBookException extends ModelStub {
        @Override
        public void addBook(Book book) throws DuplicateBookException {
            throw new DuplicateBookException();
        }

        @Override
        public ReadOnlyBookShelf getBookShelf() {
            return new BookShelf();
        }
    }

    /**
     * A Model stub that always accept the book being added.
     */
    private class ModelStubAcceptingBookAdded extends ModelStub {
        final ArrayList<Book> booksAdded = new ArrayList<>();

        @Override
        public void addBook(Book book) throws DuplicateBookException {
            requireNonNull(book);
            booksAdded.add(book);
        }

        @Override
        public ReadOnlyBookShelf getBookShelf() {
            return new BookShelf();
        }
    }

}
