package seedu.organizer.logic.commands;

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
import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Model;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.recurrence.exceptions.TaskNotRecurringException;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//@@author dominickenn
public class SignUpCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SignUpCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        User validUser = new User("david", "david123");

        CommandResult commandResult = getSignUpCommandForUser(validUser, modelStub).execute();

        assertEquals(String.format(SignUpCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validUser), modelStub.usersAdded);
    }

    @Test
    public void execute_duplicateUser_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateUserException();
        User validUser = new User("admin", "admin");

        thrown.expect(CommandException.class);
        thrown.expectMessage(SignUpCommand.MESSAGE_DUPLICATE_USER);

        getSignUpCommandForUser(validUser, modelStub).execute();
    }

    @Test
    public void equals() {
        User alice = new User("alice", "alice123");
        User bob = new User("bobby", "bobby123");
        SignUpCommand signUpAliceCommand = new SignUpCommand(alice);
        SignUpCommand signUpBobCommand = new SignUpCommand(bob);

        // same object -> returns true
        assertTrue(signUpAliceCommand.equals(signUpAliceCommand));

        // same values -> returns true
        SignUpCommand addAliceCommandCopy = new SignUpCommand(alice);
        assertTrue(signUpAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(signUpAliceCommand.equals(new LoginCommand(alice)));

        // null -> returns false
        assertFalse(signUpAliceCommand.equals(null));

        // different task -> returns false
        assertFalse(signUpAliceCommand.equals(signUpBobCommand));
    }

    /**
     * Generates a new SignUpCommand with the given user.
     */
    private SignUpCommand getSignUpCommandForUser(User user, Model model) {
        SignUpCommand command = new SignUpCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addUser(User user) throws DuplicateUserException {
            fail("This method should not be called");
        }

        @Override
        public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
            fail("This method should not be called");
        }

        @Override
        public void logout() {
            fail("This method should not be called");
        }

        @Override
        public void deleteCurrentUserTasks() {
            fail("This method should not be called");
        }

        @Override
        public void addQuestionAnswerToUser(User toRemove, UserWithQuestionAnswer toAdd) {
            fail("This method should not be called");
        }

        @Override
        public User getUserByUsername(String username) throws UserNotFoundException {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void addTask(Task task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyOrganizer newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTask(Task target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void recurWeeklyTask(Task task, int times) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteRecurredTasks(Task task) throws DuplicateTaskException, TaskNotRecurringException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateUserException when trying to add a user.
     */
    private class ModelStubThrowingDuplicateUserException extends ModelStub {
        @Override
        public void addUser(User user) throws DuplicateUserException {
            throw new DuplicateUserException();
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

    /**
     * A Model stub that always accept the user being added.
     */
    private class ModelStubAcceptingUserAdded extends ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            requireNonNull(user);
            usersAdded.add(user);
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

}
