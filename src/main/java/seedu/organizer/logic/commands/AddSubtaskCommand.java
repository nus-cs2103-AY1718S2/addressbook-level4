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
 * Add a subtask into a task
 */
public class AddSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "adds";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a subtask to a task. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Submit report ";

    public static final String MESSAGE_SUCCESS = "New subtask added: %1$s";
    public static final String MESSAGE_DUPLICATED = "Subtask already exist";

    private final Subtask toAdd;
    private final Index index;

    private Task taskToEdit;
    private Task editedTask;

    public AddSubtaskCommand(Index index, Subtask toAdd) {
        requireNonNull(toAdd);
        requireNonNull(index);
        this.index = index;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            editedTask = createEditedTask(taskToEdit, toAdd);
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

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(index.getZeroBased());
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     */
    private static Task createEditedTask(Task taskToEdit, Subtask toAdd) throws DuplicateSubtaskException {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted oldDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(taskToEdit.getSubtasks());
        Status updatedStatus = taskToEdit.getStatus();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        updatedSubtasks.add(toAdd);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded, oldDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(), getCurrentlyLoggedInUser(),
                updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSubtaskCommand // instanceof handles nulls
                && this.index.equals(((AddSubtaskCommand) other).index) // state check
                && this.toAdd.equals(((AddSubtaskCommand) other).toAdd)); // state check
    }
}
