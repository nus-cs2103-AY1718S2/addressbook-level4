package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.DeskBoard;

//@@author Kyomian
/**
 * Clears the deskboard.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Deskboard has been cleared!";
    public static final String MESSAGE_CLEAR_TASK_SUCCESS = "Tasks have been cleared!";
    public static final String MESSAGE_CLEAR_EVENT_SUCCESS = "Events have been cleared!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears task panel, event panel or both task and event panel.\n"
            + "Parameters: [task/event]\n"
            + "Example: " + COMMAND_WORD + " OR "
            + COMMAND_ALIAS + " event";

    private final String activityOption;

    public ClearCommand(String activityOption) {
        this.activityOption = activityOption;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        if (activityOption.equals("")) {
            model.resetData(new DeskBoard());
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (activityOption.equals("task")) {
            model.clearActivities("TASK");
            return new CommandResult(MESSAGE_CLEAR_TASK_SUCCESS);
        } else {
            model.clearActivities("EVENT");
            return new CommandResult(MESSAGE_CLEAR_EVENT_SUCCESS);
        }
    }
}
