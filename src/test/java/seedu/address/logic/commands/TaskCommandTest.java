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
import seedu.address.model.DeskBoard;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.testutil.TaskBuilder;

//@@author Kyomian
public class TaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new TaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Activity validTask = new TaskBuilder().build();

        CommandResult commandResult = getTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(TaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateActivityException();
        Activity validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(TaskCommand.MESSAGE_DUPLICATE_TASK);

        getTaskCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Activity assignment = new TaskBuilder().build();
        Activity project = new TaskBuilder().withName("Project").build();
        TaskCommand addAssignmentCommand = new TaskCommand((Task) assignment);
        TaskCommand addProjectCommand = new TaskCommand((Task) project);

        // same object -> returns true
        assertTrue(addAssignmentCommand.equals(addAssignmentCommand));

        // same values -> returns true
        TaskCommand addAssignmentCommandCopy = new TaskCommand((Task) assignment);
        assertTrue(addAssignmentCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand.equals(null));

        // different activity -> returns false
        assertFalse(addAssignmentCommand.equals(addProjectCommand));
    }

    /**
     * Generates a new TaskCommand with the details of the given task.
     */
    private TaskCommand getTaskCommandForTask(Activity task, Model model) {
        TaskCommand command = new TaskCommand((Task) task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDeskBoard newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteActivity(Activity target) throws ActivityNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateActivity(Activity target, Activity editedActivity)
                throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateActivityException when trying to add a activity.
     */
    private class ModelStubThrowingDuplicateActivityException extends ModelStub {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            throw new DuplicateActivityException();
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }

    /**
     * A Model stub that always accept the activity being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Activity> tasksAdded = new ArrayList<>();

        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            requireNonNull(activity);
            tasksAdded.add(activity);
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return new DeskBoard();
        }
    }

}
