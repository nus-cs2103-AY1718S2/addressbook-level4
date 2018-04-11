package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TIMES;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;

import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.recurrence.exceptions.TaskAlreadyRecurredException;
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

//@@author natania
/**
 * Recurs a task weekly for the specified number of weeks.
 */
public class RecurWeeklyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "recurw";
    public static final String COMMAND_ALIAS = "rw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recurs a task weekly for specified number of weeks. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TIMES + "NO. OF TIMES TO RECUR "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIMES + "2 ";

    public static final String MESSAGE_SUCCESS = "Recurring task: %1$s";
    public static final String MESSAGE_RECURRED_TASK = "Task is already recurring";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the organizer.";

    private final Index index;
    private final int times;

    private Task taskToRecur;
    private Task recurredTask;

    /**
     * @param index of the task in the filtered task list to edit
     * @param times task is to be recurred
     */
    public RecurWeeklyCommand(Index index, int times) {
        requireNonNull(index);

        this.index = index;
        this.times = times;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            recurredTask = createRecurredTask(taskToRecur);
            model.updateTask(taskToRecur, recurredTask);
            model.recurTask(recurredTask, this.times);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        } catch (TaskAlreadyRecurredException tare) {
            throw new CommandException(MESSAGE_RECURRED_TASK);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, recurredTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToRecur = lastShownList.get(index.getZeroBased());
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToRecur}
     */
    private static Task createRecurredTask(Task taskToRecur) throws TaskAlreadyRecurredException {
        assert taskToRecur != null;

        Name updatedName = taskToRecur.getName();
        Priority updatedPriority = taskToRecur.getUpdatedPriority();
        Priority basePriority = taskToRecur.getBasePriority();
        Deadline updatedDeadline = taskToRecur.getDeadline();
        DateAdded oldDateAdded = taskToRecur.getDateAdded();
        DateCompleted oldDateCompleted = taskToRecur.getDateCompleted();
        Description updatedDescription = taskToRecur.getDescription();
        Set<Tag> updatedTags = taskToRecur.getTags();
        List<Subtask> updatedSubtasks = taskToRecur.getSubtasks();
        Status updatedStatus = taskToRecur.getStatus();
        Recurrence updatedRecurrence = new Recurrence(taskToRecur.getRecurrence().getIsRecurring(),
                taskToRecur.hashCode(), true);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded, oldDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks, getCurrentlyLoggedInUser(),
                updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurWeeklyCommand // instanceof handles nulls
                && this.index.equals(((RecurWeeklyCommand) other).index) // state check
                && this.times == (((RecurWeeklyCommand) other).times)); // state check
    }
}
