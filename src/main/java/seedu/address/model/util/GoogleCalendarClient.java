package seedu.address.model.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.model.export.exceptions.CalendarAccessDeniedException;
import seedu.address.model.export.exceptions.ConnectivityIssueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

//@@author daviddalmaso
/**
 * Client for the Google Calendar API
 */
public class GoogleCalendarClient {

    /** Application name. */
    private static final String applicationName =
            "reInsurance Events";

    /** Global instance of the JSON factory. */
    private static final JsonFactory jsonFactory =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/export-calendar-java-credentials
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Checks internet connectivity of application.
     * Export calendar can only be run with internet access
     * @throws ConnectivityIssueException
     */
    public static void checkInternetConnection() throws ConnectivityIssueException {
        try {
            InetAddress byName = InetAddress.getByName("www.google.com");
            if (!byName.isReachable(1000)) {
                throw new ConnectivityIssueException();
            }
        } catch (IOException e) {
            throw new ConnectivityIssueException();
        }
    }

    /**
     * Authorizes a user using the authorization code flow supported by Google OAuth API
     * @return a Credential object
     */
    public static Credential authorize() throws CalendarAccessDeniedException, ConnectivityIssueException {
        try {
            InputStream in = GoogleCalendarClient.class.getResourceAsStream("/oAuth/client_secret.json");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                    inputStreamReader);
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, clientSecrets, SCOPES
            ).build();
            checkInternetConnection();
            return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        } catch (IOException e) {
            throw new CalendarAccessDeniedException();
        }
    }

    /**
     * @param date string in "dd-mm-yyyy" format
     * @return string in "yyyy-mm-dd" format
     */
    private static String formatDate(String date) {
        String[] dates = date.split("-");
        return dates[2] + "-" + dates[1] + "-" + dates[0];
    }

    /**
     * @param date date of the event
     * @return an EventDateTime object for the specified date
     */
    private static EventDateTime createEventDateTime(String date) {
        DateTime dateTime = new DateTime(formatDate(date));
        return new EventDateTime().setDateTime(dateTime).setTimeZone("Asia/Singapore");
    }

    /**
     * @param event event to set start and end dateTimes
     * @param date date to use for the event
     */
    private static void setEventDates (Event event, String date) {
        EventDateTime eventDateTime = createEventDateTime(date);
        event.setStart(eventDateTime).setEnd(eventDateTime);
    }

    /**
     *
     * @param persons UniquePersonList: all Person objects in the address book
     * @return returns a list of events, being the birthdays and appointments of each person
     */
    private static List<Event> createEvents(UniquePersonList persons) {
        List<Event> events = new ArrayList<>();

        // Iterate through persons to get their birthdays and appointments
        for (Person person : persons) {
            String name = person.getName().fullName;

            // Create birthday event for current Person object
            Event birthday = new Event()
                    .setSummary(name + "'s Birthday");

            String birthdayDate = person.getBirthday().value;
            setEventDates(birthday, birthdayDate);
            String[] recc = new String[] {"RRULE:FREQ=YEARLY;COUNT=100"};
            birthday.setRecurrence(Arrays.asList(recc));

            // Create appointment event for current Person object
            Event appointment = new Event()
                    .setSummary("Appointment with " + name);

            String appointmentDate = person.getAppointment().value;
            setEventDates(appointment, appointmentDate);

            events.add(birthday);
            events.add(appointment);
        }

        return events;
    }

    /**
     * @param persons UniquePersonList to make the Calendar out of
     * @throws Exception if the Google API Client fails
     */
    public static void insertCalendar(UniquePersonList persons)
            throws CalendarAccessDeniedException, ConnectivityIssueException {
        Credential credentials = GoogleCalendarClient.authorize();

        Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
                .setApplicationName(applicationName).build();

        String existingCalendarId = getExistingCalendarId(service, "reInsurance Events");

        // If the reInsurance Events calendar already exists, delete it
        try {
            if (existingCalendarId != null) {
                service.calendars().delete(existingCalendarId).execute();
            }

            // Create a new calendar
            com.google.api.services.calendar.model.Calendar calendar =
                    new com.google.api.services.calendar.model.Calendar();
            calendar.setSummary("reInsurance Events");
            calendar.setTimeZone("Asia/Singapore");

            // Insert the new calendar into the Google Account
            com.google.api.services.calendar.model.Calendar createdCalendar =
                    service.calendars().insert(calendar).execute();

            // Get created calendar Id
            String calendarId = createdCalendar.getId();

            // Insert events into create calendar
            List<Event> events = createEvents(persons);
            for (Event event : events) {
                service.events().insert(calendarId, event).execute();
            }
        } catch (IOException e) {
            //TODO: Handle this
        }
    }

    public static String getExistingCalendarId(Calendar service, String calendarSummary) {
        try {
            // Get list of calendars that user has
            List<CalendarListEntry> calendarList = service.calendarList().list().execute().getItems();

            // Iterate through the list of calendars to find the calendar id of the calendarSummary that matches
            for (CalendarListEntry calendarListEntry : calendarList) {
                if (calendarListEntry.getSummary().compareTo(calendarSummary) == 0) {
                    return calendarListEntry.getId();
                }
            }
        } catch (IOException e) {
            //TODO: Handle this
        }

        return null;
    }
}

