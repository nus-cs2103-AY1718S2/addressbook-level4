//@@author ZhangYijiong
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Implementation follows {@code DeleteCommand}
 * Deletes n orders at the front of the queue, n is the user input.
 */
public class CompleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "completeOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Complete n orders in the current queue, n be the user input.\n"
            + "Parameters: Number (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = " Order(s) Completed";

    private final Index targetIndex;
    private final Index numberOfTimes;

    public CompleteOrderCommand(Index targetIndex,Index numberOfTimes) {
        this.targetIndex = targetIndex;
        this.numberOfTimes = numberOfTimes;
    }

    @Override
    public CommandResult execute() throws CommandException {
        int number = numberOfTimes.getOneBased();
        while (number-- != 0) {

        List<Task> lastShownList = model.getFilteredTaskList();

        if (number >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteOrderCommand // instanceof handles nulls
                && this.targetIndex.equals(((CompleteOrderCommand) other).targetIndex)
                && this.numberOfTimes.equals(((CompleteOrderCommand) other).numberOfTimes)); // state check
    }
}

