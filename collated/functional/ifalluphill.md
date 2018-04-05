# ifalluphill
###### /resources/view/MainWindow.fxml
``` fxml
          <Menu mnemonicParsing="false" text="View">
            <MenuItem fx:id="viewCalendarMenuItem" mnemonicParsing="false" onAction="#handleViewCalendar" text="Open Calendar" />
            <MenuItem fx:id="viewErrorsMenuItem" mnemonicParsing="false" onAction="#handleViewErrors" text="Show Error Log" />
          </Menu>
```
###### /resources/view/ErrorsWindow.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.Scene?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.web.WebView?>

<!-- TODO: set a more appropriate initial size -->
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         title="Error Log" minWidth="450" minHeight="600">
    <icons>
        <Image url="@/images/info_icon.png" />
    </icons>
    <scene>
        <Scene>
            <WebView fx:id="browser" />
        </Scene>
    </scene>
</fx:root>

```
###### /resources/view/CalendarWindow.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.scene.Scene?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.web.WebView?>

<!-- TODO: set a more appropriate initial size -->
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         title="Calendar" minWidth="450" minHeight="600">
    <icons>
        <Image url="@/images/calendar.png" />
    </icons>
    <scene>
        <Scene>
            <WebView fx:id="browser" />
        </Scene>
    </scene>
</fx:root>

```
###### /java/seedu/address/ui/CalendarWindow.java
``` java

package seedu.address.ui;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * The CalendarWindow Window. Provides the basic window generation,
 * content population, and rendering.
 */
public class CalendarWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ErrorsWindow.class);
    private static final String FXML = "CalendarWindow.fxml";

    @FXML
    private WebView browser;


    /**
     * Creates a new CalendarWindow (Overload).
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    public CalendarWindow(Stage root, Logic logic) throws IOException {
        super(FXML, root);
        WebEngine engine = browser.getEngine();

        if (logic.hasLoggedIn()) {
            String googleCalendarLink = "https://calendar.google.com/calendar/r";
            URI uri = URI.create(googleCalendarLink);
            Map<String, List<String>> headers = new LinkedHashMap<>();
            headers.put("Set-Cookie", Arrays.asList("name=value"));
            java.net.CookieHandler.getDefault().put(uri, headers);
            engine.setUserAgent(engine.getUserAgent().replace("Macintosh; ", ""));
            engine.load(googleCalendarLink);
        } else {
            browser.getEngine().loadContent("<html>You must be logged in to use the calendar feature.</html>",
                    "text/Html");
        }

    }

    /**
     * Creates a new CalendarWindow.
     */
    public CalendarWindow(Logic logic) throws IOException {
        this(new Stage(), logic);
    }

    /**
     * Shows the calendar window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing calendar window.");
        getRoot().show();
    }
}

```
###### /java/seedu/address/ui/CalendarPanel.java
``` java
    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() throws IOException {

        WebEngine engine = browser.getEngine();
        URI uri = URI.create(CALENDAR_URL);
        Map<String, List<String>> headers = new LinkedHashMap<>();
        headers.put("Set-Cookie", Arrays.asList("name=value"));
        java.net.CookieHandler.getDefault().put(uri, headers);
        engine.setUserAgent(engine.getUserAgent().replace("Macintosh; ", ""));
        Platform.runLater(() -> browser.getEngine().load(CALENDAR_URL));
    }

```
###### /java/seedu/address/ui/ErrorsWindow.java
``` java

package seedu.address.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * The ErrorWindow Window. Provides the basic window generation,
 * layout, content population, and rendering.
 */
public class ErrorsWindow extends UiPart<Stage> {

    private static final String ERROR_LOG_FILE_PATH = "./addressbook.log.0";
    private static final Logger logger = LogsCenter.getLogger(ErrorsWindow.class);
    private static final String FXML = "ErrorsWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new ErrorsWindow (Overload).
     *
     * @param root Stage to use as the root of the ErrorsWindow.
     */
    public ErrorsWindow(Stage root, Logic logic) {
        super(FXML, root);

        if (logic.hasLoggedIn()) {
            String errorLog = getErrorLogAsHtmlString();
            String errorWindowHtml = createErrorLogPageAsHtmlString(errorLog);
            browser.getEngine().loadContent(errorWindowHtml, "text/Html");
        } else {
            browser.getEngine().loadContent("<html>You must be logged in to view the error log.</html>",
                    "text/Html");
        }

    }

    /**
     * Creates a new ErrorsWindow.
     */
    public ErrorsWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Formats the error log for use in an Html page.
     */

    private String getErrorLogAsHtmlString() {
        StringBuilder errorLog = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(ERROR_LOG_FILE_PATH)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorLog.append("<p>").append(line).append("</p>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return errorLog.toString();
    }

    /**
     * Generates the Html page content for the errors window.
     */

    private String createErrorLogPageAsHtmlString(String errorLog) {
        return  "<HTML>"
                    + "<style>"
                        + "body { background: #eee; }"
                        + "p { margin: 0; }"
                    + "</style>"
                    + "<body>"
                        + "<div>" + errorLog + "</div>"
                    + "</body>"
                + "</HTML>";
    }

    /**
     * Shows the error window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing error log within the application.");
        getRoot().show();
    }
}

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Opens the error window.
     */
    @FXML
    public void handleViewErrors() {
        ErrorsWindow errorsWindow = new ErrorsWindow(logic);
        errorsWindow.show();
    }

    /**
     * Opens the calendar window.
     */
    @FXML
    public void handleViewCalendar() throws IOException {
        CalendarWindow calendarWindow = new CalendarWindow(logic);
        calendarWindow.show();
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleShowErrorsEvent(ShowErrorsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleViewErrors();
    }

    @Subscribe
    private void handleViewCalendarEvent(ShowCalendarRequestEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleViewCalendar();
    }
```
###### /java/seedu/address/commons/events/ui/ShowCalendarRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to open the calendar.
 */
public class ShowCalendarRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/commons/events/ui/ShowErrorsRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the error log.
 */
public class ShowErrorsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String eventName} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventName} is invalid.
     */
    public static String parseEventName(String name) throws IllegalValueException {
        requireNonNull(name);
        return name.trim();
    }

    /**
     * Parses a {@code String dateTime} into a {@code DateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventName} is invalid.
     */
    public static String parseDateTime(String dateTime) throws IllegalValueException {
        requireNonNull(dateTime);
        return dateTime.trim();
    }

    /**
     * Parses a {@code String location} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code location} is invalid.
     */
    public static String parseLocation(String location) throws IllegalValueException {
        requireNonNull(location);
        return location.trim();
    }

```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    public static final Prefix PREFIX_CAL_EVENT_NAME = new Prefix("title/");
    public static final Prefix PREFIX_CAL_START_DATE_TIME = new Prefix("start/");
    public static final Prefix PREFIX_CAL_END_DATE_TIME = new Prefix("end/");
    public static final Prefix PREFIX_CAL_LOCATION = new Prefix("loc/");
    public static final Prefix PREFIX_CAL_LINK_PERSON = new Prefix("lp/");
```
###### /java/seedu/address/logic/parser/CalendarAddCommandParser.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_LINK_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_START_DATE_TIME;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CalendarAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalendarAddCommand object
 */
public class CalendarAddCommandParser implements Parser<CalendarAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CAL_EVENT_NAME, PREFIX_CAL_START_DATE_TIME,
                        PREFIX_CAL_END_DATE_TIME, PREFIX_CAL_LOCATION, PREFIX_CAL_LINK_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_CAL_EVENT_NAME, PREFIX_CAL_START_DATE_TIME,
                PREFIX_CAL_END_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarAddCommand.MESSAGE_USAGE));
        }


        try {
            String eventName = ParserUtil.parseEventName(
                    argMultimap.getValue(PREFIX_CAL_EVENT_NAME).orElse(""));
            String startDateTime = ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_CAL_START_DATE_TIME).orElse(""));
            String endDateTime = ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_CAL_END_DATE_TIME).orElse(""));
            String location = ParserUtil.parseLocation(
                    argMultimap.getValue(PREFIX_CAL_LOCATION).orElse(""));

            Event newEvent = new Event();
            newEvent.setSummary(eventName);
            EventDateTime start = new EventDateTime().setDateTime(convertFriendlyDateTimeToDateTime(startDateTime));
            newEvent.setStart(start);
            EventDateTime end = new EventDateTime().setDateTime(convertFriendlyDateTimeToDateTime(endDateTime));
            newEvent.setEnd(end);

            if (location != null) {
                newEvent.setLocation(location);
            }

            return new CalendarAddCommand(newEvent);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Converts a human-readable date time string into a usable date time string
     */
    private DateTime convertFriendlyDateTimeToDateTime(String datetime) {
        List<Date> dates = new com.joestelmach.natty.Parser().parse(datetime).get(0).getDates();
        return new DateTime(dates.get(0));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

```
###### /java/seedu/address/logic/OAuthManager.java
``` java
// Adapted from https://developers.google.com/calendar/quickstart/java

package seedu.address.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Creates an authorized Credential object.
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
        List<Event> upcomingEvents = getNextXEvents(user, 10);
        int numberOfEventsRetrieved = upcomingEvents.size();

        if (numberOfEventsRetrieved == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Retrieved " + String.valueOf(numberOfEventsRetrieved) + " event(s).");
        }

        return upcomingEvents;
    }

    /**
     * Get a list of upcoming events as a list of strings.
     * @throws IOException
     */
    public static List<String> getUpcomingEventsAsStringList(User user) throws IOException {
        List<Event> upcomingEvents = getNextXEvents(user, 10);
        int numberOfEventsRetrieved = upcomingEvents.size();
        List<String> eventListAsString = new ArrayList<>();

        if (numberOfEventsRetrieved == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Retrieved " + String.valueOf(numberOfEventsRetrieved) + " event(s): ");
            for (Event event : upcomingEvents) {
                String eventAsString = formatEventDetailsAsString(event);
                eventListAsString.add(eventAsString);
            }
        }

        return eventListAsString;
    }

    /**
     * Formats an event object as a human-readable string.
     */
    private static String formatEventDetailsAsString(Event event) {
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
     * @param date must in RFC 3339 format
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
    public static void addEvent(User user) throws IOException {
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

}

```
###### /java/seedu/address/logic/commands/OAuthTestCommand.java
``` java

package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.OAuthManager;
import seedu.address.model.login.User;

/**
 * Opens a calendar window.
 */
public class OAuthTestCommand extends Command {

    public static final String COMMAND_WORD = "reauthenticate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tests OAuth certificate.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TEST_OAUTH = "Tested OAuth certificate.";

    @Override
    public CommandResult execute() {
        User user = model.getLoggedInUser();

        try {
            OAuthManager.authorize(user);
            OAuthManager.addEvent(user);
        } catch (IOException e) {
            // Do nothing for now
        }

        return new CommandResult(MESSAGE_TEST_OAUTH);
    }
}

```
###### /java/seedu/address/logic/commands/ErrorLogCommand.java
``` java

package seedu.address.logic.commands;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowErrorsRequestEvent;

/**
 * Opens a calendar window.
 */
public class ErrorLogCommand extends Command {

    public static final String COMMAND_WORD = "errorlog";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the error log in a new window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SHOWING_ERRORLOG = "Opened error log window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowErrorsRequestEvent());
        return new CommandResult(MESSAGE_SHOWING_ERRORLOG);
    }
}

```
###### /java/seedu/address/logic/commands/CalendarListCommand.java
``` java

package seedu.address.logic.commands;

import java.io.IOException;
import java.util.List;

import seedu.address.logic.OAuthManager;
import seedu.address.model.login.User;

/**
* Lists up to the next 10 calendar events from their Google Calendar to the user.
*/
public class CalendarListCommand extends Command {

    public static final String COMMAND_WORD = "calendar-list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List up to the next 10 calendar events.";

    public static final String MESSAGE_ERROR = "Unable to retrieve calendar events. Please try again later.";

    @Override
    public CommandResult execute() {
        User user = model.getLoggedInUser();

        try {
            List<String> upcomingEvents = OAuthManager.getUpcomingEventsAsStringList(user);
            String upcomingEventsAsString = String.join("\n", upcomingEvents);

            return new CommandResult(upcomingEventsAsString);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }

    }

}

```
###### /java/seedu/address/logic/commands/CalendarAddCommand.java
``` java

package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_START_DATE_TIME;

import java.io.IOException;

import com.google.api.services.calendar.model.Event;

import seedu.address.logic.OAuthManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.User;

/**
* Adds an event to the user's Google Calendar
*/
public class CalendarAddCommand extends Command {

    public static final String COMMAND_WORD = "calendar-add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add an event to Google Calendar. "
            + "Parameters: "
            + PREFIX_CAL_EVENT_NAME + "EVENT NAME "
            + PREFIX_CAL_START_DATE_TIME + "START DATE & TIME "
            + PREFIX_CAL_END_DATE_TIME + "END DATE & TIME "
            + PREFIX_CAL_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CAL_EVENT_NAME + "CS2103 Tutorial "
            + PREFIX_CAL_START_DATE_TIME + "This Thursday at 3PM "
            + PREFIX_CAL_END_DATE_TIME + "4PM on Thursday "
            + PREFIX_CAL_LOCATION + "NUS School of Computing, COM1, 13 Computing Drive, Singapore 117417";


    public static final String MESSAGE_ERROR = "Unable to add new event. Please try again later.";
    private final Event event;


    public CalendarAddCommand(Event event) {
        this.event = event;
    };

    @Override
    public CommandResult execute() throws CommandException {
        User user = model.getLoggedInUser();

        try {
            String apiResponse = OAuthManager.addEvent(user, event);
            return new CommandResult(apiResponse);

        } catch (IOException e) {
            return new CommandResult(MESSAGE_ERROR);
        }
    }
}
```
###### /java/seedu/address/logic/commands/CalendarCommand.java
``` java

package seedu.address.logic.commands;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowCalendarRequestEvent;

/**
 * Opens a calendar window.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar-launch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens a calendar window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SHOWING_CALENDAR = "Opened calendar window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowCalendarRequestEvent());
        return new CommandResult(MESSAGE_SHOWING_CALENDAR);
    }
}

```
