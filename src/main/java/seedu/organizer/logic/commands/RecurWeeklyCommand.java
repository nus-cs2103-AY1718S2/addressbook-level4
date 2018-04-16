package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TIMES;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.exceptions.TaskAlreadyRecurredException;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;

//@@author natania-d
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
            model.recurWeeklyTask(taskToRecur, this.times);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        } catch (TaskAlreadyRecurredException tare) {
            throw new CommandException(MESSAGE_RECURRED_TASK);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToRecur));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToRecur = lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurWeeklyCommand // instanceof handles nulls
                && this.index.equals(((RecurWeeklyCommand) other).index) // state check
                && this.times == (((RecurWeeklyCommand) other).times)); // state check
    }
}
