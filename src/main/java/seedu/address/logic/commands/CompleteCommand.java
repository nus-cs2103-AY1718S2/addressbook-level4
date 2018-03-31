package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@Author YuanQLLer
/**
 *
 * Complete a task identified using it's last displayed index from the CLInder.
 */
public class CompleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Complete the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Activity: %1$s";

    private final Index targetIndex;

    private Activity activityToComplete;

    public CompleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(activityToComplete);
        try {
            Activity completedActivity = activityToComplete.getCompletedCopy();
            model.updateActivity(activityToComplete, completedActivity);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("The target activity cannot be missing");
        } catch (DuplicateActivityException dae) {
            throw new AssertionError("The completed activity cannot be duplicated");
        }
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, activityToComplete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Activity> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        activityToComplete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((CompleteCommand) other).targetIndex) // state check
                && Objects.equals(this.activityToComplete, ((CompleteCommand) other).activityToComplete));
    }
}
