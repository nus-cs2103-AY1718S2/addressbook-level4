package seedu.organizer.logic.commands;

import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.Recurrence;
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
import seedu.organizer.model.user.User;

/**
 * Deletes a subtask of a task
 */
public class DeleteSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete-subtask";
    public static final String COMMAND_ALIAS = "ds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete a subtask of a task. "
            + "Parameters: TASK_INDEX (must be a positive integer) SUBTASK_INDEX (must be a positive integer)";

    public static final String MESSAGE_EDIT_SUBTASK_SUCCESS = "Subtask Deleted: %1$s";

    public final Index taskIndex;
    public final Index subtaskIndex;

    private Task taskToEdit;
    private Task editedTask;
    private Subtask deletedSubtask;

    /**
     * @param taskIndex index of the task in the filtered task list to edit
     * @param subtaskIndex index of the subtask of the task to edit
     */
    public DeleteSubtaskCommand(Index taskIndex, Index subtaskIndex) {
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
        return new CommandResult(String.format(MESSAGE_EDIT_SUBTASK_SUCCESS, deletedSubtask));
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
        deletedSubtask = taskToEdit.getSubtasks().get(subtaskIndex.getZeroBased());
        editedTask = createEditedTask(taskToEdit, subtaskIndex);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     */
    private static Task createEditedTask(Task taskToEdit, Index subtaskIndex) {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted oldDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        User user = taskToEdit.getUser();
        Set<Tag> updatedTags = taskToEdit.getTags();
        List<Subtask> originalSubtasks = new ArrayList<>(taskToEdit.getSubtasks());
        Status updatedStatus = taskToEdit.getStatus();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        originalSubtasks.remove(subtaskIndex.getZeroBased());
        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(originalSubtasks);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded, oldDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(), user, updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSubtaskCommand // instanceof handles nulls
                && this.taskIndex.equals(((DeleteSubtaskCommand) other).taskIndex) // state check
                && this.subtaskIndex.equals(((DeleteSubtaskCommand) other).subtaskIndex)); // state check
    }
}
