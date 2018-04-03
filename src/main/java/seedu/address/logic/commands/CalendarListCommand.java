//@@author ifalluphill

package seedu.address.logic.commands;

import java.io.IOException;
import java.util.List;

import com.google.api.services.calendar.model.Event;

import seedu.address.logic.OAuthManager;

/**
* Lists up to the next 10 calendar events from their Google Calendar to the user.
*/
public class CalendarListCommand extends Command {

    public static final String COMMAND_WORD = "calendar-list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List up to the next 10 calendar events.";

    public static final String MESSAGE_ERROR = "Unable to retrieve calendar events. Please try again later.";

    @Override
    public CommandResult execute() {

        try {
            List<Event> upcomingEvents = OAuthManager.getUpcomingEvents();
            return new CommandResult(upcomingEvents.toString());
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }

    }

}

//@@author
