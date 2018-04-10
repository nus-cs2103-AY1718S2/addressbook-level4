package seedu.address.logic;
//@@author crizyli
import java.io.IOException;

import com.google.api.services.calendar.model.Calendar;

/**
 * Create a calendar for a person.
 */
public class CreateNewCalendar {

    /**
     * Create a new calendar for person with personName.
     *
     */
    public static String execute(String personName) throws IOException {
        // Build a new authorized API client service.
        com.google.api.services.calendar.Calendar service =
                null;

        service = Authentication.getCalendarService();

        // Create a new calendar
        com.google.api.services.calendar.model.Calendar calendar = new Calendar();
        calendar.setSummary(personName);
        calendar.setTimeZone("Asia/Singapore");


        // Insert the new calendar
        String calendarId = "primary";

        try {
            Calendar createdCalendar = service.calendars().insert(calendar).execute();
            calendarId = createdCalendar.getId();
            System.out.println(calendarId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calendarId;
    }

}
