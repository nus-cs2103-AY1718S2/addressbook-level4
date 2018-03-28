package seedu.organizer.logic.commands;

import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.DateAdded;
import seedu.organizer.model.task.DateCompleted;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;

/**
 * Inverse the value of task status
 */
public class ToggleSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "toggle-subtask";
    public static final String COMMAND_ALIAS = "ts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a subttask to a task. "
            + "Parameters: TASK_INDEX (must be a positive integer) SUBTASK_INDEX (must be a positive integer)";

    public static final String MESSAGE_EDIT_SUBTASK_SUCCESS = "Toogled Subtask: %1$s";

    public final Index taskIndex;
    public final Index subtaskIndex;

    private Task taskToEdit;
    private Task editedTask;
    private Subtask editedSubtask;

    /**
     * @param taskIndex index of the task in the filtered task list to edit
     * @param subtaskIndex index of the subtask of the task to edit
     */
    public ToggleSubtaskCommand(Index taskIndex, Index subtaskIndex) {
        this.taskIndex = taskIndex;
        this.subtaskIndex = subtaskIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException("This exception should not happen (duplicated task while toggling)");
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("This exception should not happen (task missing while toggling)");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_SUBTASK_SUCCESS, editedSubtask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        taskToEdit = lastShownList.get(taskIndex.getZeroBased());

        if (subtaskIndex.getZeroBased() >= taskToEdit.getSubtasks().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX);
        }
        editedTask = createEditedTask(taskToEdit, subtaskIndex);
        editedSubtask = editedTask.getSubtasks().get(subtaskIndex.getZeroBased());
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit} with status inversed
     */
    private static Task createEditedTask(Task taskToEdit, Index subtaskIndex) {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getPriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted updatedDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        List<Subtask> originalSubtasks = new ArrayList<>(taskToEdit.getSubtasks());
        Status updatedStatus = taskToEdit.getStatus();

        Subtask originalSubtask = originalSubtasks.get(subtaskIndex.getZeroBased());
        Name subtaskName = originalSubtask.getName();
        Status subtaskStatus = originalSubtask.getStatus().getInverse();

        Subtask editedSubtask = new Subtask(subtaskName, subtaskStatus);
        originalSubtasks.set(subtaskIndex.getZeroBased(), editedSubtask);

        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(originalSubtasks);

        return new Task(updatedName, updatedPriority, updatedDeadline, oldDateAdded, updatedDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToggleSubtaskCommand // instanceof handles nulls
                && this.taskIndex.equals(((ToggleSubtaskCommand) other).taskIndex) // state check
                && this.subtaskIndex.equals(((ToggleSubtaskCommand) other).subtaskIndex)); // state check
    }
}
