# wayneong95
###### /java/seedu/address/ui/CalendarWindowTest.java
``` java

public class CalendarWindowTest extends GuiUnitTest {
    private ShowCalendarEvent showCalendarEventstub;

    private CalendarWindow calendarWindow;
    private CalendarWindowHandle calendarWindowHandle;

    @Before
    public void setUp() {
        showCalendarEventstub = new ShowCalendarEvent();
        guiRobot.interact(() -> calendarWindow = new CalendarWindow());
        uiPartRule.setUiPart(calendarWindow);

        calendarWindowHandle = new CalendarWindowHandle(calendarWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        // updated default window
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertFalse(expectedDefaultPageUrl.equals(calendarWindowHandle.getLoadedUrl()));

        /*Does not work on Travis. Use for local testing.
        // calendar window
        postNow(showCalendarEventstub);
        URL expectedCalendarWindow = new URL(CalendarWindow.CALENDAR_PAGE_URL);
        waitUntilCalendarLoaded(calendarWindowHandle);
        assertEquals(expectedCalendarWindow, calendarWindowHandle.getLoadedUrl());
        */
    }
}
```
###### /java/seedu/address/logic/parser/AddEventCommandParserTest.java
``` java

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CalendarEvent expectedEvent = new EventBuilder().withEventName(VALID_EVENT_NAME)
                .withStartDate(VALID_START_DATE)
                .withStartTime(VALID_START_TIME)
                .withEndDate(VALID_END_DATE)
                .withEndTime(VALID_END_TIME)
                .build();
        try {
            Command actual = parser.parse(PREAMBLE_WHITESPACE + EVENT_NAME_DESC + EVENT_START_DATE_DESC
                    + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC);
            Command expected = new AddEventCommand(expectedEvent);
            expected.equals(actual);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_invalidEndDate_failure() {
        String expectedMessage = String.format(INVALID_END_DATE_MESSAGE);
        //end date earlier than start date
        assertParseFailure(parser, EVENT_NAME_DESC + " sd/2018-05-20" + EVENT_START_TIME_DESC
                + " ed/2018-05-19" + EVENT_END_TIME_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidEndTime_failure() {
        String expectedMessage = String.format(INVALID_END_TIME_MESSAGE);
        //end time earlier than start time
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + " st/11:30"
                + EVENT_END_DATE_DESC + " et/10:30", expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing eventName prefix
        assertParseFailure(parser, VALID_EVENT_NAME + EVENT_START_DATE_DESC + EVENT_START_TIME_DESC
                        + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventStartDate prefix
        assertParseFailure(parser, EVENT_NAME_DESC + VALID_START_DATE + EVENT_START_TIME_DESC
                + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventStartTime prefix
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + VALID_START_TIME
                + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventEndDate prefix
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + EVENT_START_TIME_DESC
                + VALID_END_DATE + EVENT_END_TIME_DESC, expectedMessage);

        // missing eventEndTime prefix
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC + EVENT_START_TIME_DESC
                + EVENT_END_DATE_DESC + VALID_END_TIME, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME + VALID_START_DATE + VALID_START_TIME
                + VALID_END_DATE + VALID_END_TIME, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid eventName
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_START_DATE_DESC
                + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // invalid eventStartDate
        assertParseFailure(parser, EVENT_NAME_DESC + INVALID_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventStartDate.MESSAGE_START_DATE_CONSTRAINTS);

        // invalid eventStartTime
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + INVALID_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventStartTime.MESSAGE_START_TIME_CONSTRAINTS);

        // invalid eventEndDate
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + EVENT_START_TIME_DESC + INVALID_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventEndDate.MESSAGE_END_DATE_CONSTRAINTS);

        // invalid eventEndTime
        assertParseFailure(parser, EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + INVALID_END_TIME_DESC,
                EventEndTime.MESSAGE_END_TIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + INVALID_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC + EVENT_START_DATE_DESC
                        + EVENT_START_TIME_DESC + EVENT_END_DATE_DESC + EVENT_END_TIME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/AddEventCommandTest.java
``` java

/**Test for AddEvent command*/
public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }
    /* Works only on manual testing as it requires authentication by the user.
    @Test
    public void execute_event_addSuccessful() throws Exception {
        CalendarEvent validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
    } */

    /**
     * Tests for same command created by the same event.
     */
    @Test
    public void addEvent_sameEvent() {
        CalendarEvent event1 = new EventBuilder().withEventName("Weights Training").build();
        AddEventCommand command1 = new AddEventCommand(event1);
        AddEventCommand command2 = new AddEventCommand(event1);

        //Tests if command1 is equals to command2.
        assertTrue(command1.equals(command2));
    }
    /**
     * Generates a new AddEventCommand with the details of the given CalendarEvent.
     */
    private AddEventCommand getAddEventCommandForEvent(CalendarEvent event) {
        AddEventCommand command = new AddEventCommand(event);
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/CalendarCommandTest.java
``` java

public class CalendarCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_calendar_success() {
        CommandResult result = new CalendarCommand().execute();
        assertEquals(SHOWING_CALENDAR_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowCalendarEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/model/person/WeightTest.java
``` java

public class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        String invalidWeight = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    public void isValidWeight() {
        // null weight
        Assert.assertThrows(NullPointerException.class, () -> Weight.isValidWeight(null));

        // invalid weight
        assertFalse(Weight.isValidWeight("")); // empty string
        assertFalse(Weight.isValidWeight(" ")); // spaces only
        assertFalse(Weight.isValidWeight("weight")); // non-numeric
        assertFalse(Weight.isValidWeight("9p.2")); // alphabets within digits
        assertFalse(Weight.isValidWeight("9 3")); // spaces within digits

        // valid weight numbers
        assertTrue(Weight.isValidWeight("91.1")); // exactly 3 numbers
        assertTrue(Weight.isValidWeight("95"));
        assertTrue(Weight.isValidWeight("105")); // heavy weight
    }
}
```
###### /java/seedu/address/model/CalendarEvent/EventTimeTest.java
``` java

public class EventTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventStartTime(null));
        Assert.assertThrows(NullPointerException.class, () -> new EventEndTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidEventTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventStartTime(invalidEventTime));
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventEndTime(invalidEventTime));
    }

    @Test
    public void isValidEventTime() {
        // null event time
        Assert.assertThrows(NullPointerException.class, () -> EventStartTime.isValidTime(null));
        Assert.assertThrows(NullPointerException.class, () -> EventEndTime.isValidTime(null));

        // invalid event time
        assertFalse(EventStartTime.isValidTime("")); // empty string
        assertFalse(EventStartTime.isValidTime("17:MM")); // contains non-numeric characters
        assertFalse(EventStartTime.isValidTime("17:%%")); // contains non-alphanumeric characters
        assertFalse(EventStartTime.isValidTime("17:")); // missing minutes field
        assertFalse(EventStartTime.isValidTime(":30")); // missing hours field
        assertFalse(EventStartTime.isValidTime(":")); // missing both fields
        assertFalse(EventEndTime.isValidTime("")); // empty string
        assertFalse(EventEndTime.isValidTime("17:MM")); // contains non-numeric characters
        assertFalse(EventEndTime.isValidTime("17:%%")); // contains non-alphanumeric characters
        assertFalse(EventEndTime.isValidTime("17:")); // missing minutes field
        assertFalse(EventEndTime.isValidTime(":30")); // missing hours field
        assertFalse(EventEndTime.isValidTime(":")); // missing both fields

        // valid eventName
        assertTrue(EventStartTime.isValidTime("17:30")); // correct format
        assertTrue(EventStartTime.isValidTime("15:30")); // correct format
        assertTrue(EventEndTime.isValidTime("17:30")); // correct format
        assertTrue(EventEndTime.isValidTime("15:30")); // correct format
    }
}
```
###### /java/seedu/address/model/CalendarEvent/CalendarEventTest.java
``` java

public class CalendarEventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CalendarEvent(null,
                null, null, null, null));
    }

    @Test
    public void calendarEventNameTest () {
        //Default calendar event built by event builder
        CalendarEvent calendarEvent1 = new EventBuilder().build();
        //Calendar event built by event builder with individual fields provided
        CalendarEvent calendarEvent2 = new EventBuilder().withEventName(VALID_EVENT_NAME)
                .withStartDate(VALID_START_DATE).withStartTime(VALID_START_TIME)
                .withEndDate(VALID_END_DATE).withEndTime(VALID_END_TIME).build();

        assertTrue(calendarEvent1.toString().equals(calendarEvent2.toString()));

    }
}
```
###### /java/seedu/address/model/CalendarEvent/EventNameTest.java
``` java

public class EventNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidEventName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventName(invalidEventName));
    }

    @Test
    public void isValidEventName() {
        // null eventName
        Assert.assertThrows(NullPointerException.class, () -> EventName.isValidEventName(null));

        // invalid eventName
        assertFalse(EventName.isValidEventName("")); // empty string
        assertFalse(EventName.isValidEventName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidEventName("Workout*")); // contains non-alphanumeric characters

        // valid eventName
        assertTrue(EventName.isValidEventName("weights training")); // alphabets only
        assertTrue(EventName.isValidEventName("12345")); // numbers only
        assertTrue(EventName.isValidEventName("2nd weight training")); // alphanumeric characters
        assertTrue(EventName.isValidEventName("Weight Training")); // with capital letters
        assertTrue(EventName.isValidEventName("Weight Training with Personal Trainer")); // long names
    }
}
```
###### /java/seedu/address/model/CalendarEvent/EventDateTest.java
``` java

public class EventDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventStartDate(null));
        Assert.assertThrows(NullPointerException.class, () -> new EventEndDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidEventDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventStartDate(invalidEventDate));
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventEndDate(invalidEventDate));
    }

    @Test
    public void isValidEventDate() {
        // null eventDate
        Assert.assertThrows(NullPointerException.class, () -> EventStartDate.isValidDate(null));
        Assert.assertThrows(NullPointerException.class, () -> EventEndDate.isValidDate(null));

        // invalid eventDate
        assertFalse(EventStartDate.isValidDate("")); // empty string
        assertFalse(EventStartDate.isValidDate("2018-MM-20")); // contains non-numeric characters
        assertFalse(EventStartDate.isValidDate("2018-%%-20")); // contains non-alphanumeric characters
        assertFalse(EventStartDate.isValidDate("05-20")); // missing year field
        assertFalse(EventStartDate.isValidDate("2018-05")); // missing month/day field
        assertFalse(EventStartDate.isValidDate("2018")); // missing month and day field
        assertFalse(EventStartDate.isValidDate("18-05-20")); // invalid year format
        assertFalse(EventEndDate.isValidDate("")); // empty string
        assertFalse(EventEndDate.isValidDate("2018-MM-20")); // contains non-numeric characters
        assertFalse(EventEndDate.isValidDate("2018-%%-20")); // contains non-alphanumeric characters
        assertFalse(EventEndDate.isValidDate("05-20")); // missing year field
        assertFalse(EventEndDate.isValidDate("2018-05")); // missing month/day field
        assertFalse(EventEndDate.isValidDate("2018")); // missing month and day field
        assertFalse(EventEndDate.isValidDate("18-05-20")); // invalid year format


        // valid eventName
        assertTrue(EventStartDate.isValidDate("2018-05-20")); // correct format
        assertTrue(EventStartDate.isValidDate("2017-04-25")); // correct format
        assertTrue(EventEndDate.isValidDate("2018-05-20")); // correct format
        assertTrue(EventEndDate.isValidDate("2017-04-25")); // correct format
    }
}
```
###### /java/seedu/address/testutil/EventBuilder.java
``` java

/**
 * A utility class to help with building CalendarEvent objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Weights Training";
    public static final String DEFAULT_START_DATE = "2018-05-20";
    public static final String DEFAULT_START_TIME = "15:30";
    public static final String DEFAULT_END_DATE = "2018-05-20";
    public static final String DEFAULT_END_TIME = "16:30";


    private EventName eventName;
    private EventStartDate eventStartDate;
    private EventStartTime eventStartTime;
    private EventEndDate eventEndDate;
    private EventEndTime eventEndTime;

    public EventBuilder() {
        eventName = new EventName(DEFAULT_EVENT_NAME);
        eventStartDate = new EventStartDate(DEFAULT_START_DATE);
        eventStartTime = new EventStartTime(DEFAULT_START_TIME);
        eventEndDate = new EventEndDate(DEFAULT_END_DATE);
        eventEndTime = new EventEndTime(DEFAULT_END_TIME);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(CalendarEvent eventToCopy) {
        eventName = eventToCopy.getEventName();
        eventStartDate = eventToCopy.getEventStartDate();
        eventStartTime = eventToCopy.getEventStartTime();
        eventEndDate = eventToCopy.getEventEndDate();
        eventEndTime = eventToCopy.getEventEndTime();
    }

    /**
     * Sets the {@code EventName} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Sets the {@code EventStartDate} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withStartDate(String eventStartDate) {
        this.eventStartDate = new EventStartDate(eventStartDate);
        return this;
    }

    /**
     * Sets the {@code EventStartTime} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withStartTime(String eventStartTime) {
        this.eventStartTime = new EventStartTime(eventStartTime);
        return this;
    }

    /**
     * Sets the {@code EventEndDate} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withEndDate(String eventEndDate) {
        this.eventEndDate = new EventEndDate(eventEndDate);
        return this;
    }

    /**
     * Sets the {@code EventEndTime} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withEndTime(String eventEndTime) {
        this.eventEndTime = new EventEndTime(eventEndTime);
        return this;
    }

    /**
     * Builds a new CalendarEvent.
     * */
    public CalendarEvent build() {
        return new CalendarEvent(eventName, eventStartDate, eventStartTime,
                eventEndDate, eventEndTime);
    }

}
```
###### /java/guitests/guihandles/CalendarWindowHandle.java
``` java

/**
 * A handler for the {@code CalendarWindow} of the UI.
 */
public class CalendarWindowHandle extends NodeHandle<Node> {

    public static final String CALENDARWINDOW_ID = "#calendar";

    private boolean isWebViewLoaded = true;

    private URL lastRememberedUrl;

    public CalendarWindowHandle(Node calendarWindowNode) {
        super(calendarWindowNode);

        WebView webView = getChildNode(CALENDARWINDOW_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(CALENDARWINDOW_ID));
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberUrl() {
        lastRememberedUrl = getLoadedUrl();
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isUrlChanged() {
        return !lastRememberedUrl.equals(getLoadedUrl());
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
```
