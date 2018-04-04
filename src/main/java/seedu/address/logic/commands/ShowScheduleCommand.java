//@@author ifalluphill

package seedu.address.logic.commands;

import java.io.IOException;
import java.util.List;

import seedu.address.logic.OAuthManager;
import seedu.address.model.login.User;

/**
* Shows the user the events for the current day
*/
public class ShowScheduleCommand extends Command {

    public static final String COMMAND_WORD = "show-schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all events for the current day.";

    public static final String MESSAGE_NO_EVENTS = "No events found for today.";
    public static final String MESSAGE_ERROR = "Unable to retrieve today's events. Please try again later.";

    @Override
    public CommandResult execute() {
        User user = model.getLoggedInUser();

        try {
            List<String> dailyEvents = OAuthManager.getDailyEventsAsStringList(user);
            String dailyEventsAsString = String.join("\n", dailyEvents);

            if (dailyEventsAsString.length() == 0) {
                dailyEventsAsString = MESSAGE_NO_EVENTS;
            }

            return new CommandResult(dailyEventsAsString);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }

    }

}

//@@author
