# wayneong95
###### /resources/view/LightTheme.css
``` css

.background {
    -fx-background-color: white;
    background-color: white;
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: white;
    -fx-control-inner-background: white;
    -fx-background-color: white;
    -fx-table-cell-border-color: #dddddd;
    -fx-table-header-border-color: #dddddd;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: white;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: white;
    -fx-border-color:
        #dddddd
        #dddddd
        #dddddd
        #dddddd;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: white;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: white;
    -fx-border-color: #dddddd #dddddd #dddddd #dddddd;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: white;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: white;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: white;
}

.list-cell:filled:odd {
    -fx-background-color: white;
}

.list-cell:filled:selected {
    -fx-background-color: #eaedf2;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #000000;
    -fx-border-width: 2;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: white;
}

.pane-with-border {
     -fx-background-color: white;
     -fx-border-color: #dddddd #dddddd #dddddd #dddddd;
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: white;
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: white;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: white;
    -fx-border-color: #dddddd;
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: white;
    -fx-border-color: #dddddd;
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: white;
}

.context-menu {
    -fx-background-color: white;
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: white;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-style: null;
    -fx-background-radius: 0;

    -fx-background-color: #cccccc;

    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: black;
}

.button:hover{
    -fx-background-color: #d8d8d8;
}

.button:pressed, .button:default:hover:pressed
{
  -fx-background-color: black;
  -fx-text-fill: white;
}

.button:focused
{
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-style: segments(1, 1);
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:disabled, .button:default:disabled
{
    -fx-opacity: 0.4;
    -fx-background-color: #cccccc;
    -fx-text-fill: #212121;
}

.button:default
{
    -fx-background-color: #008287;
    -fx-text-fill: #ffffff;
}

.button:default:hover{
    -fx-background-color: #219297;
}

.dialog-pane {
    -fx-background-color: white;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: white;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: white;
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: white;
}

.scroll-bar .thumb {
    -fx-background-color: #bbbbbb;
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

```
###### /java/seedu/address/ui/CalendarWindow.java
``` java

/**
 * Controller for a Calendar Window.
 */
public class CalendarWindow extends UiPart<Region> {

    public static final String CALENDAR_PAGE_URL =
            "https://calendar.google.com/calendar";

    private static final Logger logger = LogsCenter.getLogger(CalendarWindow.class);
    private static final String FXML = "CalendarWindow.fxml";

    @FXML
    private WebView calendar;

    /**
     * Creates a new Calendar Window.
     */

    public CalendarWindow() {
        super(FXML);
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadCalendarPage();
        registerAsAnEventHandler(this);
    }

    private void loadCalendarPage() {
        loadPage(CALENDAR_PAGE_URL);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> calendar.getEngine().load(url));
    }

    /**
     * Frees resources allocated to the Google calendar.
     */
    public void freeResources() {
        calendar = null;
    }

    @Subscribe
    private void showCalendarEvent (ShowCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(CALENDAR_PAGE_URL);
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java

    /**
     * Switch to calendar window.
     */
    @FXML
    private void handleCalendar() {
        calendarWindow = new CalendarWindow();
        browserPlaceholder.getChildren().add(calendarWindow.getRoot());
        browserPlaceholder.getChildren().setAll(calendarWindow.getRoot());
    }

    /**
     * Switch to browser from calendar.
     */
    @FXML
    private void handleBrowser() {
        browserPlaceholder.getChildren().setAll(browserPanel.getRoot());
    }

```
###### /java/seedu/address/commons/events/ui/ShowCalendarEvent.java
``` java

/**
 * An event requesting to view calendar.
 */
public class ShowCalendarEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/commons/events/GoogleCalendar/AddCalendarEvent.java
``` java

/**
 * An event requesting to add an event to the calendar.
 */
public class AddCalendarEvent extends BaseEvent {

    private CalendarEvent calendarEvent;

    public AddCalendarEvent(CalendarEvent calendarEvent) { this.calendarEvent = calendarEvent; }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/logic/parser/AddEventCommandParser.java
``` java

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_START_DATE, PREFIX_EVENT_START_TIME,
                        PREFIX_EVENT_END_DATE, PREFIX_EVENT_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_START_DATE, PREFIX_EVENT_START_TIME,
                PREFIX_EVENT_END_DATE, PREFIX_EVENT_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            EventName eventName =
                    ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME)).get();
            EventStartDate eventStartDate =
                    ParserUtil.parseEventStartDate(argMultimap.getValue(PREFIX_EVENT_START_DATE)).get();
            EventStartTime eventStartTime =
                    ParserUtil.parseEventStartTime(argMultimap.getValue(PREFIX_EVENT_START_TIME)).get();
            EventEndDate eventEndDate =
                    ParserUtil.parseEventEndDate(argMultimap.getValue(PREFIX_EVENT_END_DATE)).get();
            EventEndTime eventEndTime =
                    ParserUtil.parseEventEndTime(argMultimap.getValue(PREFIX_EVENT_END_TIME)).get();

            dateRestrictions(eventStartDate, eventEndDate);

            //If event is on same day, check for time restrictions

            if (eventStartDate.toString().equals(eventEndDate.toString())) {
                try {
                    timeRestrictions(eventStartTime, eventEndTime);
                }
                catch (Exception e) {
                    throw new ParseException(INVALID_END_TIME_MESSAGE);
                }
            }

            CalendarEvent calendarEvent = new CalendarEvent(eventName, eventStartDate, eventStartTime,
                    eventEndDate, eventEndTime);

            return new AddEventCommand(calendarEvent);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        catch (Exception e) {
            throw new ParseException(INVALID_END_DATE_MESSAGE);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if eventEndDate is earlier then eventStartDate and throws an exception if so.
     */

    public void dateRestrictions (EventStartDate eventStartDate, EventEndDate eventEndDate) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = sdf.parse(eventStartDate.toString());
            Date date2 = sdf.parse(eventEndDate.toString());
            if (date2.before(date1)) {
                throw new Exception("End Date cannot be earlier than Start Date!");
            }
        } catch (java.text.ParseException e) {
            System.out.println("ParseException");
        }
    }

    /**
     * Checks if eventEndTime is earlier then eventStartTime if events are
     * on the same day and throws an exception if so.
     */

    public void timeRestrictions (EventStartTime eventStartTime, EventEndTime eventEndTime) throws Exception {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");

        try {
            Date time1 = timeFormat.parse(eventStartTime.toString());
            Date time2 = timeFormat.parse(eventEndTime.toString());
            if (time2.before(time1)) {
                throw new Exception("End Time cannot be earlier than Start Time!");
            }
        } catch (java.text.ParseException e) {
            System.out.println("ParseException");
        }
    }

}
```
###### /java/seedu/address/logic/commands/AddEventCommand.java
``` java

/**
 * Adds an event to the Google Calendar.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the Google Calendar. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT NAME "
            + PREFIX_EVENT_START_DATE + "EVENT START DATE "
            + PREFIX_EVENT_START_TIME + "EVENT START TIME "
            + PREFIX_EVENT_END_DATE + "EVENT END DATE "
            + PREFIX_EVENT_END_TIME + "EVENT END TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Weights Training "
            + PREFIX_EVENT_START_DATE + "2017-11-30 "
            + PREFIX_EVENT_START_TIME + "11:30 "
            + PREFIX_EVENT_END_DATE + "2017-11-30 "
            + PREFIX_EVENT_END_TIME + "13:30 ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    private final CalendarEvent toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code CalendarEvent}
     */
    public AddEventCommand(CalendarEvent calendarEvent) {
        requireNonNull(calendarEvent);
        toAdd = calendarEvent;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(toAdd);
        AddEventManager.init();
        EventsCenter.getInstance().post(new AddCalendarEvent(toAdd));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/CalendarCommand.java
``` java

/**
 * Opens up the Google Calendar window
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CALENDAR_MESSAGE = "Calendar displayed!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowCalendarEvent());
        /*try {
            GoogleCalendarApi.startCalendar();
        } catch (IOException e) {
            System.out.println("IOException");
        }*/
        return new CommandResult(SHOWING_CALENDAR_MESSAGE);
    }
}
```
###### /java/seedu/address/model/person/Weight.java
``` java

/**
 * Represents a Person's weight (in Kg) in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {


    public static final String MESSAGE_WEIGHT_CONSTRAINTS =
            "Weight(in Kg) can only contain numbers and decimals, and should be at least 2 digits long";
    public static final String WEIGHT_VALIDATION_REGEX = "\\d{2,}(\\.\\d+)?";
    public final String value;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight (in Kg).
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_WEIGHT_CONSTRAINTS);
        this.value = weight;
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(WEIGHT_VALIDATION_REGEX) && weightWithinRange(test);
    }

```
###### /java/seedu/address/model/ReadOnlyCalendarEvent.java
``` java

/**
 * Unmodifiable view of an event.
 */
public interface ReadOnlyCalendarEvent {
    /**
     * Returns an unmodifiable view of the event.
     */
    EventName getEventName();
    EventStartDate getEventStartDate();
    EventStartTime getEventStartTime();
    EventEndDate getEventEndDate();
    EventEndTime getEventEndTime();

}
```
###### /java/seedu/address/model/CalendarEvent/EventEndDate.java
``` java

/**
 * Represents the end date of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */

public class EventEndDate {

    public static final String MESSAGE_END_DATE_CONSTRAINTS =
            "Event end date should only contain numbers in the format YYYY-MM-DD. Eg. 2008-11-30";

    public static final String INVALID_END_DATE_MESSAGE = "End Date cannot be earlier than Start Date!";

    public static final String DATE_VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    public final String endDate;

    /**
     * Constructs an {@code EventEndDate}.
     *
     * @param endDate A valid endDate.
     */

    public EventEndDate(String endDate) {
        requireNonNull(endDate);
        checkArgument(isValidDate(endDate), MESSAGE_END_DATE_CONSTRAINTS);
        this.endDate = endDate;
    }

    /**
     * Returns true if a given string is a valid event end date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return endDate;
    }
}
```
###### /java/seedu/address/model/CalendarEvent/EventName.java
``` java

/**
 * Represents the name of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventName(String)}
 */

public class EventName {

    public static final String MESSAGE_EVENT_NAME_CONSTRAINTS =
            "Event name should only contain alphanumeric characters and spaces,"
                    + " and it should not be blank. Eg. Weights Training";

    public static final String EVENTNAME_VALIDATION_REGEX = "^[a-zA-Z0-9_ ]+$";

    public final String eventName;

    /**
     * Constructs a {@code EventName}.
     *
     * @param eventName A valid eventName.
     */

    public EventName(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidEventName(eventName), MESSAGE_EVENT_NAME_CONSTRAINTS);
        this.eventName = eventName;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidEventName(String test) {
        return test.matches(EVENTNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return eventName;
    }
}
```
###### /java/seedu/address/model/CalendarEvent/CalendarEvent.java
``` java

/**
 * Represents a Calendar event in the Personal Trainer Pro app.
 * Guarantees: details are present and not null, field values are validated.
 */

public class CalendarEvent implements ReadOnlyCalendarEvent {

    private final EventName eventName;
    private final EventStartDate eventStartDate;
    private final EventStartTime eventStartTime;
    private final EventEndDate eventEndDate;
    private final EventEndTime eventEndTime;

    public CalendarEvent (EventName eventName, EventStartDate eventStartDate, EventStartTime eventStartTime,
                          EventEndDate eventEndDate, EventEndTime eventEndTime) {

        requireAllNonNull(eventName, eventStartDate, eventStartTime, eventEndDate, eventEndTime);

        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventStartTime = eventStartTime;
        this.eventEndDate = eventEndDate;
        this.eventEndTime = eventEndTime;
    }

    @Override
    public EventName getEventName() { return eventName; }

    @Override
    public EventStartDate getEventStartDate() {
        return eventStartDate;
    }

    @Override
    public EventStartTime getEventStartTime() {
        return eventStartTime;
    }

    @Override
    public EventEndDate getEventEndDate() {
        return eventEndDate;
    }

    @Override
    public EventEndTime getEventEndTime() {
        return eventEndTime;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Start Date: ")
                .append(getEventStartDate())
                .append(" Start Time: ")
                .append(getEventStartTime())
                .append(" End Date: ")
                .append(getEventEndDate())
                .append(" End Time: ")
                .append(getEventEndTime());
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/CalendarEvent/EventStartDate.java
``` java

/**
 * Represents the start date of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */

public class EventStartDate {

    public static final String MESSAGE_START_DATE_CONSTRAINTS =
            "Event start date should only contain numbers in the format YYYY-MM-DD. Eg. 2008-11-30";

    public static final String DATE_VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    public final String startDate;

    /**
     * Constructs an {@code EventStartDate}.
     *
     * @param startDate A valid startDate.
     */

    public EventStartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(isValidDate(startDate), MESSAGE_START_DATE_CONSTRAINTS);
        this.startDate = startDate;
    }

    /**
     * Returns true if a given string is a valid event start date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startDate;
    }
}
```
###### /java/seedu/address/model/CalendarEvent/EventEndTime.java
``` java

/**
 * Represents the end time of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */

public class EventEndTime {

    public static final String MESSAGE_END_TIME_CONSTRAINTS =
            "Event end time should only contain numbers in the HH:MM format. Eg. 11:30";

    public static final String INVALID_END_TIME_MESSAGE = "End Time cannot be earlier than Start Time!";

    public static final String TIME_VALIDATION_REGEX = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public final String endTime;

    /**
     * Constructs an {@code EventEndTime}.
     *
     * @param endTime A valid endTime.
     */

    public EventEndTime(String endTime) {
        requireNonNull(endTime);
        checkArgument(isValidTime(endTime), MESSAGE_END_TIME_CONSTRAINTS);
        this.endTime = endTime;
    }

    /**
     * Returns true if a given string is a valid event end time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return endTime;
    }
}
```
###### /java/seedu/address/model/CalendarEvent/EventStartTime.java
``` java

/**
 * Represents the start time of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */

public class EventStartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS =
            "Event start time should only contain numbers in the HH:MM format. Eg. 11:30";

    public static final String TIME_VALIDATION_REGEX = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public final String startTime;

    /**
     * Constructs an {@code EventStartTime}.
     *
     * @param startTime A valid startTime.
     */

    public EventStartTime(String startTime) {
        requireNonNull(startTime);
        checkArgument(isValidTime(startTime), MESSAGE_START_TIME_CONSTRAINTS);
        this.startTime = startTime;
    }

    /**
     * Returns true if a given string is a valid event start time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startTime;
    }
}
```
###### /java/seedu/address/GoogleCalendar/AddEventManager.java
``` java

    @Subscribe
    public void handleNewAddCalendarEvent(AddCalendarEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        CalendarEvent newEvent = event.getCalendarEvent();
        GoogleCalendarApi.createEvent(newEvent);
    }
}
```
###### /java/seedu/address/GoogleCalendar/GoogleCalendarApi.java
``` java

/**Google calendar Api*/
public class GoogleCalendarApi {

    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Calendar API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
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
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GoogleCalendarApi.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
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
    public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**Creates a new Calendar Event*/
    public static void createEvent(CalendarEvent newEvent) throws IOException {

        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        String calendarId = "primary";
        String eventName = newEvent.getEventName().toString();
        String eventStartDate = newEvent.getEventStartDate().toString();
        String eventStartTime = newEvent.getEventStartTime().toString();
        String eventEndDate = newEvent.getEventEndDate().toString();
        String eventEndTime = newEvent.getEventEndTime().toString();

        Event event = new Event()
                .setSummary(eventName);

        DateTime startDateTime = new DateTime(eventStartDate + "T" + eventStartTime + ":00+08:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(eventEndDate + "T" + eventEndTime + ":00+08:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        event = service.events().insert(calendarId, event).execute();

        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    /**Starts a google calendar service*/
    public static void startCalendar() throws IOException {
        com.google.api.services.calendar.Calendar service =
                getCalendarService();
    }

}
```
