package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.exceptions.TaskNotRecurringException;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;

//@@author natania-d
/**
 * Deletes a group of recurred tasks (both the original task and recurred versions of it)
 * identified using it's last displayed index from the organizer.
 */
public class DeleteRecurredTasksCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleter";
    public static final String COMMAND_ALIAS = "dr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task at the specified index and all its recurred versions.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task & its Recurred Versions: %1$s";
    public static final String MESSAGE_NOT_RECURRED_TASK = "This task is not recurring and cannot be deleted "
            + "using this command. Use 'delete' instead.";

    private final Index targetIndex;

    private Task taskToDelete;

    public DeleteRecurredTasksCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(taskToDelete);
        try {
            model.deleteRecurredTasks(taskToDelete);
        } catch (DuplicateTaskException pnfe) {
            throw new AssertionError("The target task cannot be a duplicate");
        } catch (TaskNotRecurringException tnre) {
            throw new CommandException(MESSAGE_NOT_RECURRED_TASK);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRecurredTasksCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteRecurredTasksCommand) other).targetIndex) // state check
                && Objects.equals(this.taskToDelete, ((DeleteRecurredTasksCommand) other).taskToDelete));
    }
}
