package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import javafx.collections.ObservableList;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_loginAcceptedByModel_loginSuccessful() throws Exception {
        ModelStubAcceptingLogin modelStub = new ModelStubAcceptingLogin();

        LoginAttempt loginAttempt = new LoginAttempt("slap", "password");

        CommandResult commandResult = getLoginCommandForLoginAttempt(loginAttempt.getUsername(),
                loginAttempt.getPassword(), modelStub).execute();

        assertEquals(LoginCommand.MESSAGE_LOGIN_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_loginAcceptedByModel_loginFailure() throws Exception {
        ModelStubAcceptingLogin modelStub = new ModelStubAcceptingLogin();
        LoginAttempt invalidloginAttempt = new LoginAttempt("slapsdad", "password");

        CommandResult commandResult = getLoginCommandForLoginAttempt(invalidloginAttempt.getUsername(),
                invalidloginAttempt.getPassword(), modelStub).execute();

        assertEquals(LoginCommand.MESSAGE_LOGIN_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_alreadyLoggedIn_throwsCommandException() throws Exception {
        ModelStubThrowingDuplicatePersonException modelStub = new ModelStubThrowingDuplicatePersonException();
        LoginAttempt validloginAttempt = new LoginAttempt("slap", "password");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_LOGIN_ALREADY);

        getLoginCommandForLoginAttempt(validloginAttempt.getUsername(),
                validloginAttempt.getPassword(), modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private LoginCommand getLoginCommandForLoginAttempt(Username username, Password password, Model model) {
        LoginCommand command = new LoginCommand(username, password);
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
    }


    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingLogin extends ModelStub {

        private boolean loginStatus = false;

        @Override
        public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
            requireNonNull(username);
            requireNonNull(password);
            setLoginStatus(true);
            return true;
        }


        @Override
        public void setLoginStatus(boolean status) {
            this.loginStatus = true;
        }

        @Override
        public boolean hasLoggedIn() {
            return this.loginStatus;
        }
    }


    /**
     * A Model stub that always throw a AlreadyLoggedInException when trying to login.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public boolean checkLoginCredentials(Username username, Password password)
                throws AlreadyLoggedInException {
            throw new AlreadyLoggedInException();
        }

    }

    private class LoginAttempt {
        private Username username;
        private Password password;

        public LoginAttempt(String username, String password) {
            this.username = new Username(username);
            this.password = new Password(password);
        }

        public Password getPassword() {
            return password;
        }

        public Username getUsername() {
            return username;
        }
    }

}
