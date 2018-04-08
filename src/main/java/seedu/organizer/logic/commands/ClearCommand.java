package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;

/**
 * Clears the organizer.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Organizer has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.deleteCurrentUserTasks();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
