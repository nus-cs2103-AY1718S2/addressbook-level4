package seedu.address.GoogleCalendar;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.GoogleCalendar.AddCalendarEvent;
import seedu.address.model.CalendarEvent.CalendarEvent;

import java.io.IOException;
import java.util.logging.Logger;


public class AddEventManager {

    private static final Logger logger = LogsCenter.getLogger(AddEventManager.class);

    private static AddEventManager instance = null;

    public AddEventManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     */
    private void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Creates an instance of the Google Calendar Api and register it as an event handler.
     */
    public static AddEventManager init() {
        if (instance == null) {
            instance = new AddEventManager();
        }
        return instance;
    }

    @Subscribe
    public void handleNewAddCalendarEvent(AddCalendarEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        CalendarEvent newEvent = event.getCalendarEvent();
        GoogleCalendarAPI.createEvent(newEvent);
    }
}
