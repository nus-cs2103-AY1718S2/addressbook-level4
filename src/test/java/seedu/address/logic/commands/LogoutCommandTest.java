package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.function.Predicate;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.OAuthManager;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


//@@author kaisertanqr

public class LogoutCommandTest {

    @Test
    public void execute_loginAcceptedByModel_logoutSuccessful() throws Exception {
        LogoutCommandTest.ModelStubAcceptingLogout modelStub = new LogoutCommandTest.ModelStubAcceptingLogout();

        CommandResult commandResult = getLogoutCommand(modelStub).execute();

        assertEquals(LogoutCommand.MESSAGE_LOGOUT_SUCCESS, commandResult.feedbackToUser);

        //@@author ifalluphill
        assertNull(OAuthManager.getMostRecentEventList());
        assertNull(OAuthManager.getMostRecentDailyEventList());
        assertNull(OAuthManager.getMostRecentDailyEventListDate());
        //@@author kaisertanqr
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private LogoutCommand getLogoutCommand(Model model) {
        LogoutCommand command = new LogoutCommand();
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

        @Override
        public void addLogToPerson(Person target, Person editedPersonWithNewLog)
                throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingLogout extends LogoutCommandTest.ModelStub {

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
            this.loginStatus = status;
        }


    }
}
