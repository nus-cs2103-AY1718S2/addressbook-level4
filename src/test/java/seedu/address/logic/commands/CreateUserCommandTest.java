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


public class CreateUserCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_createUserAcceptedByModel_createSuccessful() throws Exception {
        CreateUserCommandTest.ModelStubAcceptingCreateUser modelStub =
                new CreateUserCommandTest.ModelStubAcceptingCreateUser();

        User validUser = createValidUser();

        CommandResult commandResult =
                getCreateUserCommandForCreateUserAttempt(validUser, modelStub).execute();

        assertEquals(String.format(CreateUserCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_duplicateUser_throwsCommandException() throws Exception {
        CreateUserCommandTest.ModelStubThrowingDuplicateUserException modelStub =
                new CreateUserCommandTest.ModelStubThrowingDuplicateUserException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateUserCommand.MESSAGE_DUPLICATE_USER);

        getCreateUserCommandForCreateUserAttempt(validUser, modelStub).execute();
    }

    /**
     * Generates a new CreateUserCommand with the details of the given person.
     */
    private CreateUserCommand getCreateUserCommandForCreateUserAttempt(User user, Model model) {

        CreateUserCommand command = new CreateUserCommand(user);
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
            fail("This method should not be called.");
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
            fail("This method should not be called.");
            return false;
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
    private class ModelStubAcceptingCreateUser extends CreateUserCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            requireNonNull(user);
            usersAdded.add(user);
        }

    }


    /**
     * A Model stub that always throw a DuplicateUserException when trying to login.
     */
    private class ModelStubThrowingDuplicateUserException extends CreateUserCommandTest.ModelStub {
        @Override
        public void addUser(User person) throws DuplicateUserException {
            throw new DuplicateUserException();
        }

    }

    private User createValidUser() {
        return new User(new Username("slap"), new Password("pass"));
    }

}
