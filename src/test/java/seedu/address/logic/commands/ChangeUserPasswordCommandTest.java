package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
public class ChangeUserPasswordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Password newPassword = new Password("hello");

    @Test
    public void execute_changePasswordAcceptedByModel_changePasswordSuccessful() throws Exception {
        ChangeUserPasswordCommandTest.ModelStubAcceptingChangePassword modelStub =
                new ChangeUserPasswordCommandTest.ModelStubAcceptingChangePassword();

        User validUser = createValidUser();

        CommandResult commandResult =
                getChangePasswordCommandForChangePasswordAttempt(validUser, modelStub).execute();

        assertEquals(String.format(ChangeUserPasswordCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_userNotFound_throwsCommandException() throws Exception {
        ChangeUserPasswordCommandTest.ModelStubThrowingUserNotFoundException modelStub =
                new ChangeUserPasswordCommandTest.ModelStubThrowingUserNotFoundException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(ChangeUserPasswordCommand.MESSAGE_UPDATE_FAILURE);

        getChangePasswordCommandForChangePasswordAttempt(validUser, modelStub).execute();
    }

    @Test
    public void execute_notLoggedOut_throwsCommandException() throws Exception {
        ChangeUserPasswordCommandTest.ModelStubThrowingAlreadyLoggedInException modelStub =
                new ChangeUserPasswordCommandTest.ModelStubThrowingAlreadyLoggedInException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(ChangeUserPasswordCommand.MESSAGE_NOT_LOGGED_OUT);

        getChangePasswordCommandForChangePasswordAttempt(validUser, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private ChangeUserPasswordCommand getChangePasswordCommandForChangePasswordAttempt(User user, Model model) {

        ChangeUserPasswordCommand command = new ChangeUserPasswordCommand(user.getUsername(),
                user.getPassword(), newPassword);
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
    private class ModelStubAcceptingChangePassword extends ChangeUserPasswordCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void updateUserPassword(User user, User userNewPassword) throws UserNotFoundException {
            usersAdded.add(createValidUser());
            requireAllNonNull(user, userNewPassword);
            usersAdded.set(0, userNewPassword);
        }

    }


    /**
     * A Model stub that always throw a DuplicatePersonException when trying to login.
     */
    private class ModelStubThrowingUserNotFoundException extends ChangeUserPasswordCommandTest.ModelStub {
        @Override
        public void updateUserPassword(User target, User userNewPassword) throws UserNotFoundException {
            throw new UserNotFoundException();
        }

    }

    /**
     * A Model stub that always throw a AlreadyLoggedInException when trying to login.
     */
    private class ModelStubThrowingAlreadyLoggedInException extends ChangeUserPasswordCommandTest.ModelStub {
        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            throw new AlreadyLoggedInException();
        }

    }

    private User createValidUser() {
        return new User(new Username("slap"), new Password("pass"));
    }
}
