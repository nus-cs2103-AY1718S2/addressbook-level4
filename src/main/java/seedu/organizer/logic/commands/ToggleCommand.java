package seedu.organizer.logic.commands;

import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.subtask.Subtask;
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
 * Inverse the value of task status (Done or Not done)
 */
public class ToggleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "toggle";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggle task status\n";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the organizer.";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Toggled Task: %1$s";

    public final Index index;

    private Task taskToEdit;
    private Task editedTask;

    /**
     * @param index                of the task in the filtered task list to edit
     */
    public ToggleCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(index.getZeroBased());
        editedTask = createEditedTask(taskToEdit);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit} with status inversed
     */
    private static Task createEditedTask(Task taskToEdit) {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getPriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted updatedDateCompleted = taskToEdit.getDateCompleted().toggle();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        List<Subtask> updatedSubtasks = taskToEdit.getSubtasks();
        Status updatedStatus = taskToEdit.getStatus().getInverse();

        return new Task(updatedName, updatedPriority, updatedDeadline, oldDateAdded,
                updatedDateCompleted, updatedDescription, updatedStatus, updatedTags, updatedSubtasks);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToggleCommand // instanceof handles nulls
                && this.index.equals(((ToggleCommand) other).index)); // state check
    }
}
