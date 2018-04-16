package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

//@@author CYX28
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TaskDeleteCommand}.
 */
public class TaskDeleteCommandTest {

    private Model model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TaskDeleteCommand taskDeleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(TaskDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(taskDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        TaskDeleteCommand taskDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(taskDeleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TaskDeleteCommand taskDeleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(TaskDeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(taskDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskBook().getTaskList().size());

        TaskDeleteCommand taskDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(taskDeleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        TaskDeleteCommand taskDeleteFirstCommand = prepareCommand(INDEX_FIRST_TASK);
        TaskDeleteCommand taskDeleteSecondCommand = prepareCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(taskDeleteFirstCommand.equals(taskDeleteFirstCommand));

        // same values -> returns true
        TaskDeleteCommand taskDeleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK);
        assertTrue(taskDeleteFirstCommand.equals(taskDeleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        taskDeleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(taskDeleteFirstCommand.equals(taskDeleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(taskDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(taskDeleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(taskDeleteFirstCommand.equals(taskDeleteSecondCommand));
    }

    /**
     * Returns a {@code TaskDeleteCommand} with the parameter {@code index}.
     */
    private TaskDeleteCommand prepareCommand(Index index) {
        TaskDeleteCommand taskDeleteCommand = new TaskDeleteCommand(index);
        taskDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return taskDeleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no task.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(t -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }

}
