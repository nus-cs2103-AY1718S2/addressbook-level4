//@@author ifalluphill

package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_START_DATE_TIME;

import java.io.IOException;

import com.google.api.services.calendar.model.Event;

import seedu.address.logic.OAuthManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.User;

/**
* Adds an event to the user's Google Calendar
*/
public class CalendarAddCommand extends Command {

    public static final String COMMAND_WORD = "calendar-add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an event to Google Calendar. "
            + "Parameters: "
            + PREFIX_CAL_EVENT_NAME + "EVENT NAME "
            + PREFIX_CAL_START_DATE_TIME + "START DATE & TIME "
            + PREFIX_CAL_END_DATE_TIME + "END DATE & TIME "
            + PREFIX_CAL_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CAL_EVENT_NAME + "CS2103 Tutorial "
            + PREFIX_CAL_START_DATE_TIME + "This Thursday at 3PM "
            + PREFIX_CAL_END_DATE_TIME + "4PM on Thursday "
            + PREFIX_CAL_LOCATION + "NUS School of Computing, COM1, 13 Computing Drive, Singapore 117417";


    public static final String MESSAGE_ERROR = "Unable to add new event. Please try again later.";
    private final Event event;


    public CalendarAddCommand(Event event) {
        this.event = event;
    };

    @Override
    public CommandResult execute() throws CommandException {
        User user = model.getLoggedInUser();

        try {
            String apiResponse = OAuthManager.addEvent(user, event);
            return new CommandResult(apiResponse);

        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }
    }
}
