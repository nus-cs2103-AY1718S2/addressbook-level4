//@@author ifalluphill
// Adapted from https://developers.google.com/calendar/quickstart/java

package seedu.address.logic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
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

import seedu.address.model.login.User;

/**
 * Handles the OAuth authentication process for use with Google Calendar.
 */
public class OAuthManager {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Calendar API Java Quickstart";

    /** Directory base path to store user credentials. */
    private static final String CREDENTIAL_PATH = ".credentials/slap-app-calendar/";

    /** Directory to store user credentials for this session. */
    private static java.io.File DATA_STORE_DIR =
            new java.io.File(CREDENTIAL_PATH);

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
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

    /** Most recent list of retrieved events */
    private static List<Event> mostRecentEventList = new ArrayList<>();

    /** List of events for the specified day */
    private static List<Event> dailyEventsList = new ArrayList<>();

    /** Dat specified in dailyEventsList */
    private static LocalDate dailyEventsListDate = null;

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Ensures that the credential directory matches the current logged in user.
     * @param user
     */
    private static void updateCredentialDirectory(User user) {
        // Set custom key directory for each user
        try {
            DATA_STORE_DIR = new java.io.File(CREDENTIAL_PATH + user.getUsername());
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Deletes the current Oauth certificate for the logged in user.
     * @param user
     * @throws IOException
     */
    public static void deleteOauthCert(User user) throws IOException {
        Path dirPath = Paths.get(DATA_STORE_DIR.getAbsolutePath());
        Files.walk(dirPath)
                .map(Path::toFile)
                .sorted(Comparator.comparing(File::isDirectory))
                .forEach(File::delete);
    }

    /**
     * Creates an authorized Credential object.
     * @param user
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(User user) throws IOException {
        // Load client secrets.
        InputStream in =
            OAuthManager.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        updateCredentialDirectory(user);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar getCalendarService(User user) throws IOException {
        Credential credential = authorize(user);
        return new com.google.api.services.calendar.Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Get a list of upcoming events as a list of event objects.
     * @throws IOException
     */
    public static List<Event> getUpcomingEvents(User user) throws IOException {
        List<Event> upcomingEvents = getNextXEvents(user, 250);
        int numberOfEventsRetrieved = upcomingEvents.size();

        if (numberOfEventsRetrieved == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Retrieved " + String.valueOf(numberOfEventsRetrieved) + " event(s).");
        }

        mostRecentEventList = upcomingEvents;

        return upcomingEvents;
    }

    /**
     * Get a list of upcoming events as a list of strings.
     * @throws IOException
     */
    public static List<String> getUpcomingEventsAsStringList(User user) throws IOException {
        List<Event> upcomingEvents = getUpcomingEvents(user);
        int numberOfEventsRetrieved = upcomingEvents.size();
        List<String> eventListAsString = new ArrayList<>();

        if (numberOfEventsRetrieved == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Retrieved " + String.valueOf(numberOfEventsRetrieved) + " event(s): ");
            int eventIndex = 1;
            for (Event event : upcomingEvents) {
                String eventAsString = formatEventDetailsAsString(event);
                eventListAsString.add(String.valueOf(eventIndex++) + ". " + eventAsString);
            }
        }

        mostRecentEventList = upcomingEvents;

        return eventListAsString;
    }

    /**
     * Get a list of events for the day as a list of event objects.
     * @throws IOException
     */
    public static List<Event> getDailyEvents(User user, LocalDate localDate) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String inputDate = localDate.format(formatter);

        List<Event> dailyEvents = getEventsByDay(user, inputDate);
        int numberOfEventsRetrieved = dailyEvents.size();

        if (numberOfEventsRetrieved == 0) {
            System.out.println("No events found.");
        } else {
            System.out.println("Retrieved " + String.valueOf(numberOfEventsRetrieved) + " event(s).");
        }

        dailyEventsListDate = localDate;
        dailyEventsList = dailyEvents;

        return dailyEvents;
    }

    /**
     * Get a list of events for the day as a list of strings.
     * @throws IOException
     */
    public static List<String> getDailyEventsAsStringList(User user, LocalDate localDate) throws IOException {
        List<Event> dailyEvents = getDailyEvents(user, localDate);
        int numberOfEventsRetrieved = dailyEvents.size();
        List<String> eventListAsString = new ArrayList<>();

        if (numberOfEventsRetrieved == 0) {
            System.out.println("No events found.");
        } else {
            System.out.println("Retrieved " + String.valueOf(numberOfEventsRetrieved) + " event(s): ");
            int eventIndex = 1;
            for (Event event : dailyEvents) {
                String eventAsString = formatEventDetailsAsString(event);
                eventListAsString.add(String.valueOf(eventIndex++) + ". " + eventAsString);
            }
        }

        dailyEventsListDate = localDate;
        dailyEventsList = dailyEvents;

        return eventListAsString;
    }

    /**
     * Formats an event object as a human-readable string.
     */
    public static String formatEventDetailsAsString(Event event) {
        String title = event.getSummary();
        DateTime startAsDateTime = event.getStart().getDateTime();
        DateTime endAsDateTime = event.getEnd().getDateTime();
        String location = event.getLocation();
        String personUniqueId = event.getDescription();

        String start = getDateTimeAsHumanReadable(startAsDateTime);
        String end = getDateTimeAsHumanReadable(endAsDateTime);

        if (start == null) {
            start = "Unable to retrieve start time";
        }
        if (end == null) {
            end = "Unable to retrieve end time";
        }
        if (location == null) {
            location = "No Location Specified";
        }
        if (personUniqueId == null) {
            personUniqueId = "No Person Specified";
        }
        String eventAsString = title + " From: " + start + " To: " + end + " @ "
                + location + " [" + personUniqueId + "]";
        System.out.printf(eventAsString);

        return eventAsString;
    }

    /**
     * Formats date-time string as a human-readable string.
     */
    private static String getDateTimeAsHumanReadable(DateTime inputDateTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        System.out.println(inputDateTime.toString());
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime.toString(), inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        String formattedDateTime = dateTime.format(outputFormatter);
        return formattedDateTime;
    }

    /**
     * Get a list of the next x events as a list of event objects.
     * @throws IOException
     */
    private static List<Event> getNextXEvents(User user, int x) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                getCalendarService(user);

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(x)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> upcomingEvents = events.getItems();
        return upcomingEvents;
    }

    /**
     * Gets a list of events for a particular date from Google Calendar
     * @param date must in yyyy-MM-dd format
     * @throws IOException
     */
    public static List<Event> getEventsByDay(User user, String date) throws IOException {
        com.google.api.services.calendar.Calendar service =
                getCalendarService(user);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(date, dtf);
        Date startDateAsDateType =
                Date.from(parsedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateAsDateType =
                Date.from(parsedDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1).minusSeconds(1).toInstant());

        DateTime desiredDateMidnightBefore = new DateTime(startDateAsDateType);
        DateTime desiredDateMidnightAfter = new DateTime(endDateAsDateType);

        Events events = service.events().list("primary")
                .setTimeMin(desiredDateMidnightBefore)
                .setTimeMax(desiredDateMidnightAfter)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();



        List<Event> upcomingEvents = events.getItems();

        return upcomingEvents;
    }

    /**
     * Add event test example of adding event to Google Calendar via API
     * Used as part of the oauth verification process.
     * @throws IOException
     */
    public static void addEventTest(User user) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                getCalendarService(user);

        Event event = new Event()
                .setSummary("Test Calendar Event")
                .setLocation("800 Howard St., San Francisco, CA 94103")
                .setDescription("A chance to hear more about Google's developer products.");

        DateTime startDateTime = new DateTime("2018-03-29T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2018-03-29T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        String calendarId = "primary";
        Boolean successfulAddDelete = true;

        try {
            // Add the test event
            event = service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
            successfulAddDelete = false;
        }
        String eventUrl = event.getHtmlLink();
        System.out.printf("Event created: %s\n", event.getHtmlLink());


        try {
            // Delete the test event
            service.events().delete(calendarId, event.getId()).execute();
        } catch (IOException e) {
            e.printStackTrace();
            successfulAddDelete = false;
        }

        if (successfulAddDelete) {
            System.out.println("Successfully interacted with user's calendar via Oauth.");
        }
    }

    /**
     * A wrapper of the Google Calendar Event: insert API endpoint to create a new calendar event
     * and add it to a user's Google Calendar.
     * @return a the event URL as a string.
     * @throws IOException
     */
    public static String addEvent(User user, Event event) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.

        com.google.api.services.calendar.Calendar service =
                getCalendarService(user);
        String calendarId = "primary";
        try {
            event = service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String eventUrl = event.getHtmlLink();
        String eventAsString = formatEventDetailsAsString(event);
        String apiResponse = "Something went wrong. No event was added.";

        if (eventUrl != null) {
            apiResponse = "Event created!" + "\n" + eventAsString + "\nUrl: " + eventUrl;
        }

        System.out.printf(apiResponse + "\n");
        return apiResponse;
    }

    /**
     * Gets the most recent event list shown to the user.
     * @return List
     */
    public static List<Event> getMostRecentEventList() {
        return mostRecentEventList;
    }

    /**
     * Gets the specified event by index (offset by 1 due to array indexing) according to a user's input.
     * @param index
     * @return Event
     */
    public static Event getEventByIndexFromLastList(int index) {
        return mostRecentEventList.get(index - 1);
    }

    /**
     * Gets the specified event pair by index (offset by 1 due to array indexing) according to a user's input.
     * @param index
     * @return List
     */
    public static List<Event> getEventByIndexPairFromDailyList(int index) {
        List<Event> eventPair = new ArrayList<>();
        eventPair.add(dailyEventsList.get(index - 1));
        eventPair.add(dailyEventsList.get(index));

        return eventPair;
    }


    /**
     * Gets the most recent daily event list shown to the user.
     * @return List
     */
    public static List<Event> getMostRecentDailyEventList() {
        return dailyEventsList;
    }

    /**
     * Gets the date of the most recent daily event list shown to the user.
     * @return List
     */
    public static LocalDate getMostRecentDailyEventListDate() {
        return dailyEventsListDate;
    }


    /**
     * A wrapper of the Google Calendar Event: delete API endpoint to remove a calendar event
     * from a user's Google Calendar.
     * @throws IOException
     */
    public static void deleteEvent(User user, Event event) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.

        com.google.api.services.calendar.Calendar service =
                getCalendarService(user);
        String calendarId = "primary";
        try {
            service.events().delete(calendarId, event.getId()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//@@author
