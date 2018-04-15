# chantiongley
###### /java/seedu/address/logic/parser/DeleteAccountCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.DeleteAccountCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class DeleteAccountCommandParserTest {

    private DeleteAccountCommandParser parser = new DeleteAccountCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAccountCommand
                .MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/parser/ReserveCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ReserveCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the BorrowCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the BorrowCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ReserveCommandParserTest {

    private ReserveCommandParser parser = new ReserveCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReserveCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/AddAccountCommandIntegrationTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccounts;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.testutil.AccountBuilder;

public class AddAccountCommandIntegrationTest {
    private Model model;

    @Before
    public void setUp() {
        model = getTypicalAccountList();
    }

    @Test
    public void execute_newAccount_success() throws Exception {
        Account validAccount = new AccountBuilder().build();

        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.addAccount(validAccount);

        assertCommandSuccess(prepareCommand(validAccount, model), model,
            String.format(AddAccountCommand.MESSAGE_SUCCESS, validAccount), expectedModel);
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() {
        Account accountInList = getTypicalAccounts().get(0);
        assertCommandFailure(prepareCommand(accountInList, model), model, AddAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);
    }

    /**
     * Generates a new {@code AddAccountCommand} which upon execution, adds {@code account} into the {@code model}.
     */
    private AddAccountCommand prepareCommand(Account account, Model model) {
        AddAccountCommand command = new AddAccountCommand(account);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/ReserveCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.model.book.Avail.RESERVED;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code reserveCommand}.
 */
public class ReserveCommandTest {

    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FOURTH_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FOURTH_BOOK);

        String expectedMessage = String.format(reserveCommand.MESSAGE_RESERVE_BOOK_SUCCESS, bookToReserve);

        ModelManager expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.reserveBook(bookToReserve, reservedBook);

        assertCommandSuccess(reserveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of catalogue list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalogue().getBookList().size());

        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIFTH_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIFTH_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        // reserve -> first book reserve
        reserveCommand.execute();
        undoRedoStack.push(reserveCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book reserved again
        expectedModel.returnBook(bookToReserve, reservedBook);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> reserveCommand not pushed into undoRedoStack
        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        ReserveCommand reserveFirstCommand = prepareCommand(INDEX_THIRD_BOOK);
        ReserveCommand reserveSecondCommand = prepareCommand(INDEX_FOURTH_BOOK);

        // same object -> returns true
        assertTrue(reserveFirstCommand.equals(reserveFirstCommand));

        // different types -> returns false
        assertFalse(reserveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reserveFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(reserveFirstCommand.equals(reserveSecondCommand));
    }

    private ReserveCommand prepareCommand(Index index) {
        ReserveCommand reserveCommand = new ReserveCommand(index);
        reserveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return reserveCommand;
    }

    /**
     * @param bookToReserve
     * @return duplicated book with Reserved Availability
     */
    public Book createReservedBook(Book bookToReserve) {
        assert bookToReserve != null;

        Title updatedTitle = bookToReserve.getTitle();
        Isbn updatedIsbn = bookToReserve.getIsbn();
        Avail updatedAvail = new Avail(RESERVED);
        Author updatedAuthor = bookToReserve.getAuthor();
        Set<Tag> updatedTags = bookToReserve.getTags();

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }

}
```
###### /java/seedu/address/logic/commands/AddAccountCommandTest.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;

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
import seedu.address.model.Catalogue;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.AccountBuilder;

public class AddAccountCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAccountCommand(null);
    }

    @Test
    public void execute_accountAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAccountAdded modelStub = new ModelStubAcceptingAccountAdded();
        Account validAccount = new AccountBuilder().build();

        CommandResult commandResult = getAddCommandForAccount(validAccount, modelStub).execute();

        assertEquals(String.format(AddAccountCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAccount), modelStub.accountsAdded);
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAccountException();
        Account validAccount = new AccountBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);

        getAddCommandForAccount(validAccount, modelStub).execute();
    }

    @Test
    public void equals() {
        Account alice = new AccountBuilder().withName("Alice").build();
        Account bob = new AccountBuilder().withName("Bob").build();
        AddAccountCommand addAliceCommand = new AddAccountCommand(alice);
        AddAccountCommand addBobCommand = new AddAccountCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different account -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddAccountCommand with the details of the given account.
     */
    private AddAccountCommand getAddCommandForAccount(Account account, Model model) {
        AddAccountCommand command = new AddAccountCommand(account);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addBook(Book book) throws DuplicateBookException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyCatalogue newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetAccount(UniqueAccountList newData) {

        }

        @Override
        public ReadOnlyCatalogue getCatalogue() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteBook(Book target) {
            fail("This method should not be called.");
        }

        @Override
        public void updateBook(Book target, Book editedBook) {
            fail("This method should not be called.");
        }

        @Override
        public void returnBook(Book target, Book returnedBook) throws BookNotFoundException {
        }

        @Override
        public void borrowBook(Book target, Book borrowedBook) throws BookNotFoundException {

        }

        @Override
        public void reserveBook(Book target, Book reservedBook) throws BookNotFoundException {

        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public UniqueAccountList getAccountList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public PrivilegeLevel authenticate(Credential c) {
            return Model.PRIVILEGE_LEVEL_GUEST;
        }

        @Override
        public void logout() {

        }

        @Override
        public PrivilegeLevel getPrivilegeLevel() {
            return Model.PRIVILEGE_LEVEL_GUEST;
        }

        public void addAccount(Account account) throws DuplicateAccountException {

        }

        public void deleteAccount(Account account) {

        }

        public void updateAccount(Account account, Account editedAccount) {

        }
    }

    /**
     * A Model stub that always throw a DuplicateAccountException when trying to add a account.
     */
    private class ModelStubThrowingDuplicateAccountException extends ModelStub {
        @Override
        public void addAccount(Account account) throws DuplicateAccountException {
            throw new DuplicateAccountException();
        }

        @Override

        public ReadOnlyCatalogue getCatalogue() {
            return new Catalogue();
        }
    }

    /**
     * A Model stub that always accept the account being added.
     */
    private class ModelStubAcceptingAccountAdded extends ModelStub {
        final ArrayList<Account> accountsAdded = new ArrayList<>();

        @Override
        public void addAccount(Account account) {
            requireNonNull(account);
            accountsAdded.add(account);
        }

        @Override
        public ReadOnlyCatalogue getCatalogue() {
            return new Catalogue();
        }
    }

}
```
