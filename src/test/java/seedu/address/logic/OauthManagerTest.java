//@@author ifalluphill

package seedu.address.logic;

import static org.junit.Assert.assertEquals;

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
        Event eventToTest = new Event()
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

        String expectedEventAsString = "Test Calendar Event From: 2018-04-30 09:00 AM To: 2018-04-30 05:00 PM"
                + " @ One Sansome St., San Francisco, CA 94104";
        assertEquals(expectedEventAsString, OAuthManager.formatEventDetailsAsString(eventToTest));
    }

    @Test
    public void formatEventDetailsAsString_noLocation() {
        Event eventToTest = new Event()
                .setSummary("Test Calendar Event");

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

        String expectedEventAsString = "Test Calendar Event From: 2018-04-30 09:00 AM To: 2018-04-30 05:00 PM"
                + " @ No Location Specified";
        assertEquals(expectedEventAsString, OAuthManager.formatEventDetailsAsString(eventToTest));
    }


}

//@@author
