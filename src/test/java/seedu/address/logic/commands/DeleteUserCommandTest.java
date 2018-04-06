package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
//@@author kaisertanqr
public class DeleteUserCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_deleteAcceptedByModel_deleteSuccessful() throws Exception {
        DeleteUserCommandTest.ModelStubAcceptingDeleteUser modelStub =
                new DeleteUserCommandTest.ModelStubAcceptingDeleteUser();

        User validUser = createValidUser();

        CommandResult commandResult =
                getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute();

        assertEquals(String.format(DeleteUserCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_userNotFound_throwsCommandException() throws Exception {
        DeleteUserCommandTest.ModelStubThrowingUserNotFoundException modelStub =
                new DeleteUserCommandTest.ModelStubThrowingUserNotFoundException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteUserCommand.MESSAGE_DELETE_FAILURE);

        getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute();
    }

    @Test
    public void execute_notLoggedOut_throwsCommandException() throws Exception {
        DeleteUserCommandTest.ModelStubThrowingAlreadyLoggedInException modelStub =
                new DeleteUserCommandTest.ModelStubThrowingAlreadyLoggedInException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteUserCommand.MESSAGE_NOT_LOGGED_OUT);

        getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private DeleteUserCommand getDeleteUserCommandForDeleteUserAttempt(User user, Model model) {

        DeleteUserCommand command = new DeleteUserCommand(user.getUsername(), user.getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
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
        public boolean hasLoggedIn() {
            return false;
        }

        @Override
        public User getLoggedInUser() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void setLoginStatus(boolean status) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            return true;
        };

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public void addUser(User person) throws DuplicateUserException {
            fail("This method should not be called.");
        };

        @Override
        public void deleteUser(User target) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public ReadOnlyAddressBook getUserDatabase() {
            fail("This method should not be called.");
            return null;
        };
    }


    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingDeleteUser extends DeleteUserCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void deleteUser(User user) throws UserNotFoundException {
            usersAdded.add(createValidUser());
            requireNonNull(user);
            usersAdded.remove(user);
        }

    }


    /**
     * A Model stub that always throw a DuplicatePersonException when trying to login.
     */
    private class ModelStubThrowingUserNotFoundException extends DeleteUserCommandTest.ModelStub {
        @Override
        public void deleteUser(User user) throws UserNotFoundException {
            throw new UserNotFoundException();
        }

    }

    /**
     * A Model stub that always throw a AlreadyLoggedInException when trying to login.
     */
    private class ModelStubThrowingAlreadyLoggedInException extends DeleteUserCommandTest.ModelStub {
        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            throw new AlreadyLoggedInException();
        }

    }

    private User createValidUser() {
        return new User(new Username("slap"), new Password("pass"));
    }
}
