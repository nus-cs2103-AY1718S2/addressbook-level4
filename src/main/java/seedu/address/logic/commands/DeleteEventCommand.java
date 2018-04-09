package seedu.address.logic.commands;
//@@author crizyli
import java.io.IOException;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Authentication;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Deletes an event with specified title in a person's calendar.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete an event specified by title of the person identified by the index number used "
            + "in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + " TITLE (event title)\n"
            + "Example: " + COMMAND_WORD + " 1 Weekly Meeting";

    public static final String MESSAGE_SUCCESS = "Event deleted!";
    public static final String MESSAGE_NO_SUCH_EVENT = "There is no such event!";
    public static final String MESSAGE_FAILURE = "Unable to delete event, please try again later.";


    private final Index targetIndex;
    private final String title;

    public DeleteEventCommand(Index index, String title) {
        this.targetIndex = index;
        this.title = title;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());

        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = Authentication.getCalendarService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String calendarId = targetPerson.getCalendarId();
        String pageToken = null;
        String eventId = null;
        do {
            Events events = null;
            try {
                events = service.events().list(calendarId).setPageToken(pageToken).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getSummary().compareTo(title) == 0) {
                    eventId = event.getId();
                    break;
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);

        if (eventId != null) {
            try {
                service.events().delete(calendarId, eventId).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return new CommandResult(MESSAGE_NO_SUCH_EVENT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public Index getTargetIndex() {
        return this.targetIndex;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEventCommand) other).getTargetIndex())
                && this.title.equals(((DeleteEventCommand) other).getTitle())); // state check
    }
}
