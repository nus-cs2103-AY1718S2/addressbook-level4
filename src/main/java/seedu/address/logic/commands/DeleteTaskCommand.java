package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTask";
    public static final String COMMAND_ALIAS = "dt";
    public static final String COMMAND_SIGN = "-t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    private final Index targetIndex;

    private Task taskToDelete;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(taskToDelete);
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
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
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex) // state check
                && Objects.equals(this.taskToDelete, ((DeleteTaskCommand) other).taskToDelete));
    }
}

