//@@author ifalluphill

package seedu.address.logic.commands;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowErrorsRequestEvent;

/**
 * Opens a calendar window.
 */
public class ErrorLogCommand extends Command {

    public static final String COMMAND_WORD = "errorlog";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the error log in a new window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SHOWING_ERRORLOG = "Opened error log window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowErrorsRequestEvent());
        return new CommandResult(MESSAGE_SHOWING_ERRORLOG);
    }
}

//@@author