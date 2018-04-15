//@@author ifalluphill

package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;


/**
 * Unit tests for OauthManager and contained function logic.
 */
public class OauthManagerTest {
    @Test
    public void getDateTimeAsHumanReadable() {
        DateTime dateToBeConverted = new DateTime("2018-04-30T10:00:00+08:00");
        String expectedDateOutput = "2018-04-30 10:00 AM";
        assertEquals(expectedDateOutput, OAuthManager.getDateTimeAsHumanReadable(dateToBeConverted));
    }

    @Test
    public void formatEventDetailsAsString_fullDetails() {
        Event eventToTest = generateValidEventWithLocation();

        String expectedEventAsString = "Test Calendar Event From: 2018-04-30 09:00 AM To: 2018-04-30 05:00 PM"
                + " @ One Sansome St., San Francisco, CA 94104";
        assertEquals(expectedEventAsString, OAuthManager.formatEventDetailsAsString(eventToTest));
    }

    @Test
    public void formatEventDetailsAsString_noLocation() {
        Event eventToTest = generateValidEventWithoutLocation();

        String expectedEventAsString = "Test Calendar Event From: 2018-04-30 09:00 AM To: 2018-04-30 05:00 PM"
                + " @ No Location Specified";
        assertEquals(expectedEventAsString, OAuthManager.formatEventDetailsAsString(eventToTest));
    }


    @Test
    public void clearCachedCalendarData_alreadyNull() {
        OAuthManager.clearCachedCalendarData();
        assertNull(OAuthManager.getMostRecentEventList());
        assertNull(OAuthManager.getMostRecentDailyEventList());
        assertNull(OAuthManager.getMostRecentDailyEventListDate());
    }

    @Test
    public void clearCachedCalendarData_nonNull() {
        Event firstEventToTest = generateValidEventWithLocation();
        Event secondEventToTest = generateValidEventWithoutLocation();
        LocalDate dateToTest = LocalDate.parse("2018-05-05");

        OAuthManager.setNonNullEventsCacheForTest(firstEventToTest, secondEventToTest, dateToTest);
        assertNotNull(OAuthManager.getMostRecentEventList());
        assertNotNull(OAuthManager.getMostRecentDailyEventList());
        assertNotNull(OAuthManager.getMostRecentDailyEventListDate());

        List<Event> mostRecentEventList = new ArrayList<>();
        mostRecentEventList.add(firstEventToTest);
        mostRecentEventList.add(secondEventToTest);

        List<Event> dailyEventsList = new ArrayList<>();
        dailyEventsList.add(firstEventToTest);
        dailyEventsList.add(secondEventToTest);

        assertEquals(mostRecentEventList, OAuthManager.getMostRecentEventList());
        assertEquals(dailyEventsList, OAuthManager.getMostRecentDailyEventList());
        assertEquals(dateToTest, OAuthManager.getMostRecentDailyEventListDate());

        OAuthManager.clearCachedCalendarData();
        assertNull(OAuthManager.getMostRecentEventList());
        assertNull(OAuthManager.getMostRecentDailyEventList());
        assertNull(OAuthManager.getMostRecentDailyEventListDate());
    }

    /**
     * Created a valid event with a location.
     * @returns Event
     */
    private Event generateValidEventWithLocation() {
        Event validEvent = new Event()
                .setSummary("Test Calendar Event")
                .setLocation("One Sansome St., San Francisco, CA 94104");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        validEvent.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        validEvent.setEnd(end);

        return validEvent;
    }

    /**
     * Created a valid event without a location.
     * @returns Event
     */
    private Event generateValidEventWithoutLocation() {
        Event validEvent = new Event()
                .setSummary("Test Calendar Event");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        validEvent.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        validEvent.setEnd(end);
        return validEvent;
    }

}

//@@author
