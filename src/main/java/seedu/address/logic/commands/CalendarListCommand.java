//@@author ifalluphill

package seedu.address.logic.commands;

import java.io.IOException;
import java.util.List;

import seedu.address.logic.OAuthManager;
import seedu.address.model.login.User;

/**
* Lists up to the next 250 calendar events from their Google Calendar to the user.
*/
public class CalendarListCommand extends Command {

    public static final String COMMAND_WORD = "calendar-list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List up to the next 250 calendar events.";

    public static final String MESSAGE_NO_EVENTS = "No upcoming events found.";
    public static final String MESSAGE_ERROR = "Unable to retrieve calendar events. Please try again later.";

    @Override
    public CommandResult execute() {
        User user = model.getLoggedInUser();

        try {
            List<String> upcomingEvents = OAuthManager.getUpcomingEventsAsStringList(user);
            String upcomingEventsAsString = String.join("\n", upcomingEvents);

            if (upcomingEventsAsString.length() == 0) {
                upcomingEventsAsString = MESSAGE_NO_EVENTS;
            }

            return new CommandResult(upcomingEventsAsString);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }

    }

}

//@@author
