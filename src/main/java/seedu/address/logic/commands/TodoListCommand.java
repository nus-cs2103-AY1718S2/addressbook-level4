package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowTodoListDisplayContentEvent;
import seedu.address.commons.events.ui.ShowTodoListEvent;
import seedu.address.logic.Authentication;
import seedu.address.model.listevent.ListEvent;

/**
 * Show to do list window.
 */
public class TodoListCommand extends Command {

    public static final String COMMAND_WORD = "todoList";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show the To Do List in a seperate window."
            + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "To do list window is loaded.";


    private ArrayList<ListEvent> eventList;

    public TodoListCommand() {
        eventList = new ArrayList<>();
    }


    @Override
    public CommandResult execute() {

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

        String calendarId = "primary";
        String pageToken = null;
        do {
            Events events = null;
            try {
                events = service.events().list(calendarId).setPageToken(pageToken).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Event> items = events.getItems();
            for (Event event : items) {
                eventList.add(new ListEvent(event.getSummary(), event.getLocation(), event.getStart().getDateTime()));
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);

        EventsCenter.getInstance().post(new ShowTodoListEvent());
        EventsCenter.getInstance().post(new ShowTodoListDisplayContentEvent(eventList));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
