package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;

//@@author Kyomian
/**
 * Removes an activity based on its last displayed index in the desk board.
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rm";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes task/event identified by the index number in the last displayed task/event listing.\n"
            + "Parameters: task/event INDEX (INDEX must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " task 1" + " OR "
            + COMMAND_WORD + " event 1";

    public static final String MESSAGE_REMOVE_TASK_SUCCESS = "Removed task: %1$s";
    public static final String MESSAGE_REMOVE_EVENT_SUCCESS = "Removed event: %1$s";

    private final Index targetIndex;
    private Activity activityToDelete;
    private final String activityOption;

    public RemoveCommand(String activityOption, Index targetIndex) {
        this.activityOption = activityOption;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(activityToDelete);
        try {
            model.deleteActivity(activityToDelete);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("The target activity cannot be missing");
        }

        if (activityOption.equals("task")) {
            return new CommandResult(String.format(MESSAGE_REMOVE_TASK_SUCCESS, activityToDelete));
        } else {
            return new CommandResult(String.format(MESSAGE_REMOVE_EVENT_SUCCESS, activityToDelete));
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {

        if (activityOption.equals("task")) {
            List<Activity> lastShownTaskList = model.getFilteredTaskList();
            if (targetIndex.getZeroBased() >= lastShownTaskList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
            }
            activityToDelete = lastShownTaskList.get(targetIndex.getZeroBased());
        } else if (activityOption.equals("event")) {
            List<Activity> lastShownEventList = model.getFilteredEventList();
            if (targetIndex.getZeroBased() >= lastShownEventList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
            }
            activityToDelete = lastShownEventList.get(targetIndex.getZeroBased());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemoveCommand) other).targetIndex) // state check
                && Objects.equals(this.activityToDelete, ((RemoveCommand) other).activityToDelete));
    }
}
