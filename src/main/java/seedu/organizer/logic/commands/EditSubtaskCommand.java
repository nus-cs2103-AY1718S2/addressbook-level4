package seedu.organizer.logic.commands;

//@@author aguss787
import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;
import static seedu.organizer.model.subtask.UniqueSubtaskList.DuplicateSubtaskException;

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

/**
 * Edit a subtask
 */
public class EditSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edits";
    public static final String COMMAND_ALIAS = "es";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit a subttask. "
            + "Parameters: TASK_INDEX (must be a positive integer) SUBTASK_INDEX (must be a positive integer)"
            + PREFIX_NAME + "NAME ";

    public static final String MESSAGE_SUCCESS = "Subtask edited: %1$s";
    public static final String MESSAGE_DUPLICATED = "Subtask already exist";

    private final Subtask toEdit;
    private final Index taskIndex;
    private final Index subtaskIndex;

    private Task taskToEdit;
    private Task editedTask;

    public EditSubtaskCommand(Index taskIndex, Index subtaskIndex, Subtask toEdit) {
        requireNonNull(toEdit);
        requireNonNull(taskIndex);
        requireNonNull(subtaskIndex);
        this.taskIndex = taskIndex;
        this.subtaskIndex = subtaskIndex;
        this.toEdit = toEdit;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            editedTask = createEditedTask(taskToEdit, subtaskIndex, toEdit);
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new AssertionError("Task duplication should not happen");
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        } catch (DuplicateSubtaskException dse) {
            throw new CommandException(MESSAGE_DUPLICATED);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTask));
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
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     */
    private static Task createEditedTask(Task taskToEdit, Index subtaskIndex,
                                         Subtask toAdd) throws DuplicateSubtaskException {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted oldDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        Status updatedStatus = taskToEdit.getStatus();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(taskToEdit.getSubtasks());
        updatedSubtasks.set(subtaskIndex, toAdd);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded, oldDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(), getCurrentlyLoggedInUser(),
                updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditSubtaskCommand // instanceof handles nulls
                && this.toEdit.equals(((EditSubtaskCommand) other).toEdit) // state check
                && this.subtaskIndex.equals(((EditSubtaskCommand) other).subtaskIndex) // state check
                && this.taskIndex.equals(((EditSubtaskCommand) other).taskIndex)); // state check
    }
}

