//@@author ifalluphill

package seedu.address.logic.commands;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowCalendarRequestEvent;

/**
 * Opens a calendar window.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens a calendar window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SHOWING_CALENDAR = "Opened calendar window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowCalendarRequestEvent());
        return new CommandResult(MESSAGE_SHOWING_CALENDAR);
    }
}

//@@author
