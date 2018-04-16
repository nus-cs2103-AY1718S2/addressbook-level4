package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.EventBook;

//@@author x3tsunayh

/**
 * Clears the event list.
 */
public class ClearEventsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clearevents";
    public static final String MESSAGE_SUCCESS = "Event list has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new EventBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
