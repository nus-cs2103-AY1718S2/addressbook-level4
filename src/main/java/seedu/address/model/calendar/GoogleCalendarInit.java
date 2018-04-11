package seedu.address.model.calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.Calendar;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.UserPrefs;

//@@author cambioforma

/**
 * Initialise a new Google Calendar for the current user of reInsurance
 */
public class GoogleCalendarInit {
    private static final Logger logger = LogsCenter.getLogger(GoogleCalendarInit.class);

    /**
     * Creates a new calendar in Google Calendar for birthday and appointment event
     * @throws IOException Unable to create Google Calendar
     */
    public static void init() throws IOException {
        com.google.api.services.calendar.Calendar service = GoogleCalendarApiAuthentication.getCalendarService();

        // Create a new calendar
        Calendar calendar = new Calendar();
        calendar.setSummary("reInsurance");
        calendar.setTimeZone("Asia/Singapore");

        // Insert the new calendar
        Calendar createdCalendar = service.calendars().insert(calendar).execute();
        logger.info("New Google Calendar Created:" + createdCalendar.getId());

        writeCalendarIdToFile(createdCalendar.getId());

        // Create access rule with associated scope
        AclRule rule = new AclRule();
        AclRule.Scope scope = new AclRule.Scope();
        scope.setType("default");
        rule.setScope(scope).setRole("reader");

        // Insert new access rule
        AclRule createdRule = service.acl().insert(createdCalendar.getId(), rule).execute();
        logger.fine("Added read rule to calendar: " + createdRule.getId());
    }

    /**
     * Saves the calendar Id to a text file
     * @param calendarId Id of Calendar of reInsurance in Google Calendar
     */
    private static void writeCalendarIdToFile (String calendarId) {
        try {
            UserPrefs userPrefs = new UserPrefs();
            File f = new File("data");
            f.mkdir();
            PrintWriter writer = new PrintWriter(new File(userPrefs.getCalendarIdFilePath()));
            writer.print(calendarId);
            writer.close();
            logger.info("Calendar ID written to: " + userPrefs.getCalendarIdFilePath());
        } catch (FileNotFoundException e) {
            logger.severe("Unable to write new calendar id to file");
        }
    }
}
