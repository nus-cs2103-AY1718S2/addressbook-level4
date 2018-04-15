//@@author ifalluphill

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import com.google.api.services.calendar.model.Event;

import seedu.address.logic.OAuthManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.User;

/**
* Deletes a calendar event using it's last displayed index from the command result box.
*/
public class CalendarDeleteCommand extends Command {

    public static final String COMMAND_WORD = "calendar-delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the last event listing.\n"
            + "This list can be created by either using the `calendar-list` or `show-schedule` command.\n"
            + "This command CANNOT be undone once executed.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Successfully deleted event:\n%s, ";
    public static final String MESSAGE_ERROR = "Unable to find/delete selected event. "
            + "Please ensure that the selected event exists.";
    private final Event event;

    public CalendarDeleteCommand(Event event) {
        this.event = event;
    };

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        User user = model.getLoggedInUser();

        if (event == null) {
            return new CommandResult(MESSAGE_ERROR);
        }

        try {
            String eventAsString = OAuthManager.formatEventDetailsAsString(event);
            String commandResultMessage = MESSAGE_ERROR;

            if (event != null) {
                OAuthManager.deleteEvent(user, event);
                commandResultMessage = String.format(MESSAGE_SUCCESS, eventAsString);
            }

            return new CommandResult(commandResultMessage);

        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }
    }

}

//@@author
