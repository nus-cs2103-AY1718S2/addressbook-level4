package seedu.address;

import java.io.IOException;
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

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

/** The main Calendar Functionality class
 * Returns a list of upcoming events and times
 * */
public class Calendar {
    /** Application name. */
    private static final String application_name =
            "Google Calendar API Java seedu.address.Calendar";

    /** Directory to store user credentials for this application. */
    private static final java.io.File data_store_dir = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory datastorefactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory json_factory =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httptransport;

    private static ArrayList<String> eventIDs = new ArrayList<>();

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> scopes =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            httptransport = GoogleNetHttpTransport.newTrustedTransport();
            datastorefactory = new FileDataStoreFactory(data_store_dir);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static void addEventIDs(String event) {
        eventIDs.add(event);
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                Calendar.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(json_factory, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httptransport, json_factory, clientSecrets, scopes)
                        .setDataStoreFactory(datastorefactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + data_store_dir.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                httptransport, json_factory, credential)
                .setApplicationName(application_name)
                .build();
    }

    //@@author philos22

    /**
     * iterate through events and list them out with their respective start times
     * @return a list of upcoming events
     * @throws IOException
     */
    public static String listEvents() throws IOException {

        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        StringBuilder result = new StringBuilder();

        if (items.size() == 0) {
            result.append("No upcoming events found.");
        } else {
            Integer eventNumber = 1;
            for (Event event : items) {
                String eventId = event.getId();
                addEventIDs(eventId);
                result.append(String.format("[Event %s] \t %s \t [%s] to [%s] \tLocation: %s\n",
                        eventNumber, event.getSummary(), event.getStart().getDateTime(),
                        event.getEnd().getDateTime(), event.getLocation()));
                eventNumber++;
            }
        }
        return result.toString();
    }

    /**
     * Adds event to the calendar - specifying Name, Location, StartTime, EndTime
     * @return success code
     * @throws IOException
     */
    public static String addEvent(String eventName, String eventLocation, DateTime startDateTime,
                                  DateTime endDateTime) throws IOException {

        String successAddedMessage = "Event added successfully";

        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        Event event = new Event()
                .setSummary(eventName)
                .setLocation(eventLocation);

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("");
        event.setEnd(end);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());

        return successAddedMessage;
    }

    /**
     * Delete event from the calendar - specifying EventID
     * @return success code
     * @throws IOException
     */
    public static String delEvent(String eventArrayId) throws IOException {

        String successDeletedMessage = "Event successfully deleted.";

        int eventArrayIdInt = Integer.parseInt(eventArrayId) - 1;
        String eventId = eventIDs.get(eventArrayIdInt);

        // Build a new authorized API client service.
        com.google.api.services.calendar.Calendar service = getCalendarService();

        service.events().delete("primary", eventId).execute();

        eventIDs.clear();

        String reList = listEvents(); // to regenerate the EventIDs array

        return successDeletedMessage;
    }

}
