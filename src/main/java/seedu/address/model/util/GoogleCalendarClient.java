package seedu.address.model.util;

import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;


/**
 * Client for the Google Calendar API
 */
public class GoogleCalendarClient {
    /** Application name. */
    private static final String applicationName =
            "reInsurance Events";

    /** Directory to store user credentials for this application. */
    private static final java.io.File dataStoreDir = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory jsonFactory =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(dataStoreDir);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * @return a Credential object
     */
    public static Credential authorize() {
        try {
            InputStream in = GoogleCalendarClient.class.getResourceAsStream("/client_secret.json");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                    inputStreamReader);
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, clientSecrets, SCOPES
            ).setDataStoreFactory(dataStoreFactory).build();

            return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private static void setEventDates (Event event, String date) {
        String[] dates = date.split("-");
        String day = dates[0];
        String month = dates[1];
        String year = dates[2];

        DateTime startDateTime = new DateTime(year + "-" + month + "-" + day + "T00:00:00+08:00");
        EventDateTime startEventDateTime = new EventDateTime().setDateTime(startDateTime).setTimeZone("Asia/Singapore");

        DateTime endDateTime = new DateTime(year + "-" + month + "-" + day + "T23:59:59+08:00");
        EventDateTime endEventDateTime = new EventDateTime().setDateTime(endDateTime).setTimeZone("Asia/Singapore");

        event.setStart(startEventDateTime).setEnd(endEventDateTime);
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
    public static void insertCalendar(UniquePersonList persons) throws Exception {
        Credential credentials = GoogleCalendarClient.authorize();

        Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
                .setApplicationName(applicationName).build();

        String existingCalendarId = getExistingCalendarId(service, "reInsurance Events");

        // If the reInsurance Events calendar already exists, delete it
        if (existingCalendarId != null) {
            service.calendars().delete(existingCalendarId).execute();
        }

        // Create a new calendar
        com.google.api.services.calendar.model.Calendar calendar =
                new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary("reInsurance Events");
        calendar.setTimeZone("Asia/Singapore");

        // Insert the new calendar
        com.google.api.services.calendar.model.Calendar createdCalendar =
                service.calendars().insert(calendar).execute();

        // Get created calendar Id
        String calendarId = createdCalendar.getId();

        // Insert events into create calendar
        List<Event> events = createEvents(persons);
        for (Event event : events) {
            service.events().insert(calendarId, event).execute();
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
        } catch (Exception e) {
            System.out.println("Unable to get CalendarList from Google Calendar");
            System.out.println(e);
        }

        return null;
    }
}
