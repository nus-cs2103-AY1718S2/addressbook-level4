package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowCalendarEvent;

//@@author wayneong95

/**
 * Opens up the Google Calendar window
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CALENDAR_MESSAGE = "Calendar displayed!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowCalendarEvent());
        /*try {
            GoogleCalendarApi.startCalendar();
        } catch (IOException e) {
            System.out.println("IOException");
        }*/
        return new CommandResult(SHOWING_CALENDAR_MESSAGE);
    }
}
