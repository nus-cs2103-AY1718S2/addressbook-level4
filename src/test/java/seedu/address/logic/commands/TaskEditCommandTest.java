package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKSECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_CATEGORY_WORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_TASKFIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PRIORITY_TASKFIRST;
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
import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

//@@author CYX28
/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for TaskEditCommand.
 */
public class TaskEditCommandTest {

    private Model model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand taskEditCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(taskEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withTaskName(VALID_TASK_NAME_TASKFIRST)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST).withTaskCategories(VALID_TASK_CATEGORY_WORK).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST)
                .withTaskPriority(VALID_TASK_PRIORITY_TASKFIRST).withTaskCategories(VALID_TASK_CATEGORY_WORK).build();
        TaskEditCommand taskEditCommand = prepareCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        assertCommandSuccess(taskEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        TaskEditCommand taskEditCommand = prepareCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());

        assertCommandSuccess(taskEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withTaskName(VALID_TASK_NAME_TASKFIRST).build();
        TaskEditCommand taskEditCommand = prepareCommand(INDEX_FIRST_TASK, new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_TASKFIRST).build());

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(taskEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        TaskEditCommand taskEditCommand = prepareCommand(INDEX_SECOND_TASK, descriptor);

        assertCommandFailure(taskEditCommand, model, taskEditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        // edit task in filtered list into a duplicate in address book
        Task taskInList = model.getTaskBook().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        TaskEditCommand taskEditCommand = prepareCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        assertCommandFailure(taskEditCommand, model, TaskEditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTaskName(VALID_TASK_NAME_TASKFIRST).build();
        TaskEditCommand taskEditCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(taskEditCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskBook().getTaskList().size());

        TaskEditCommand taskEditCommand = prepareCommand(outOfBoundIndex, new EditTaskDescriptorBuilder()
                .withTaskName(VALID_TASK_NAME_TASKFIRST).build());

        assertCommandFailure(taskEditCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final TaskEditCommand standardCommand = prepareCommand(INDEX_FIRST_TASK, DESC_TASKFIRST);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_TASKFIRST);
        TaskEditCommand commandWithSameValues = prepareCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TaskEditCommand(INDEX_SECOND_TASK, DESC_TASKFIRST)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new TaskEditCommand(INDEX_FIRST_TASK, DESC_TASKSECOND)));
    }

    /**
     * Returns an {@code TaskEditCommand} with parameters {@code index} and {@code descriptor}
     */
    private TaskEditCommand prepareCommand(Index index, EditTaskDescriptor descriptor) {
        TaskEditCommand taskEditCommand = new TaskEditCommand(index, descriptor);
        taskEditCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return taskEditCommand;
    }

}
