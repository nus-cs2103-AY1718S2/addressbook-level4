package seedu.address.logic;
//@@author crizyli
import java.io.IOException;


/**
 * Delete calendar of a person.
 */
public class DeleteCalendar {

    /**
     * Delete a calendar specified by calendarId.
     *
     */
    public static void execute(String calendarId) throws IOException {
        // Build a new authorized API client service.
        com.google.api.services.calendar.Calendar service =
                null;

        service = Authentication.getCalendarService();

        // Delete the calendar.
        service.calendars().delete(calendarId).execute();

    }

}
