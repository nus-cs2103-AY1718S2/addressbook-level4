package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.organizer.model.Organizer;

/**
 * Clears the organizer book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new Organizer());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
