//@@author jaronchan
package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.logic.OAuthManager;

public class ScheduledEventCardTest extends GuiUnitTest {

    private static final String EVENT_NUM_HEADER = "EVENT NUM: ";
    private static final String EVENT_TITLE_HEADER = "TITLE: ";
    private static final String EVENT_TIMING_HEADER = "TIME: ";
    private static final String EVENT_LOCATION_HEADER = "LOCATION: ";
    private static final String EVENT_PERSON_HEADER = "NAME: ";
    private static final String EVENT_CONDITION_HEADER = "CONDITION: ";
    private static final String EVENT_MOBILE_HEADER = "MOBILE: ";
    private static final String FUTURE_IMPLEMENTATION = "TO BE IMPLEMENTED IN 2.0";
    private static final String EVENT_DIVIDER = "================================ \n";

    private ScheduledEventCard scheduledEventCard;

    private Event eventToTest;
    private int eventIndex;

    @Before
    public void setUp() {

        eventToTest = new Event()
                .setSummary("Test Calendar Event")
                .setLocation("One Sansome St., San Francisco, CA 94104");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToTest.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToTest.setEnd(end);

        eventIndex = 1;

        String expectedEventAsString = "Test Calendar Event From: 2018-04-30 09:00 AM To: 2018-04-30 05:00 PM"
                + " @ One Sansome St., San Francisco, CA 94104";

        guiRobot.interact(() -> scheduledEventCard = new ScheduledEventCard(eventToTest, eventIndex));
        uiPartRule.setUiPart(scheduledEventCard);
    }

    @Test
    public void formatScheduledEvent_allFieldsPresent_success() {
        Event eventToFormat = new Event()
                .setSummary("Test Calendar Event")
                .setLocation("One Sansome St., San Francisco, CA 94104");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToFormat.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToFormat.setEnd(end);

        int eventIndexToFormat = 1;

        DateTime startAsDateTime = eventToFormat.getStart().getDateTime();
        DateTime endAsDateTime = eventToFormat.getEnd().getDateTime();

        String startAsString = OAuthManager.getDateTimeAsHumanReadable(startAsDateTime);
        String endAsString = OAuthManager.getDateTimeAsHumanReadable(endAsDateTime);

        ScheduledEventCard eventCardToFormat = new ScheduledEventCard(eventToFormat, eventIndexToFormat);

        String eventAsString = EVENT_NUM_HEADER + "1" + "\n"
                + EVENT_TITLE_HEADER + eventToFormat.getSummary() + "\n"
                + EVENT_TIMING_HEADER + startAsString + " - " + endAsString + "\n"
                + EVENT_DIVIDER
                + EVENT_LOCATION_HEADER + eventToFormat.getLocation() + "\n"
                + EVENT_PERSON_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_MOBILE_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_CONDITION_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_DIVIDER + EVENT_DIVIDER;

        assertEquals(eventAsString, eventCardToFormat.getFormattedScheduledEvent());
    }

    @Test
    public void formatScheduledEvent_nullLocation_success() {
        Event nullLocationEventToFormat = new Event()
                .setSummary("Test Calendar Event");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        nullLocationEventToFormat.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        nullLocationEventToFormat.setEnd(end);

        int eventIndexToFormat = 1;

        DateTime startAsDateTime = nullLocationEventToFormat.getStart().getDateTime();
        DateTime endAsDateTime = nullLocationEventToFormat.getEnd().getDateTime();

        String startAsString = OAuthManager.getDateTimeAsHumanReadable(startAsDateTime);
        String endAsString = OAuthManager.getDateTimeAsHumanReadable(endAsDateTime);

        ScheduledEventCard eventCardToFormat = new ScheduledEventCard(nullLocationEventToFormat, eventIndexToFormat);

        String eventAsString = EVENT_NUM_HEADER + "1" + "\n"
                + EVENT_TITLE_HEADER + nullLocationEventToFormat.getSummary() + "\n"
                + EVENT_TIMING_HEADER + startAsString + " - " + endAsString + "\n"
                + EVENT_DIVIDER
                + EVENT_LOCATION_HEADER + "No Location Specified" + "\n"
                + EVENT_PERSON_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_MOBILE_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_CONDITION_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_DIVIDER + EVENT_DIVIDER;

        assertEquals(eventAsString, eventCardToFormat.getFormattedScheduledEvent());
    }
}
