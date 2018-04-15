# jaronchan
###### /java/seedu/address/ui/PersonDetailsPanelTest.java
``` java
package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertTableDisplaysPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonDetailsPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class PersonDetailsPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private PersonDetailsPanel personDetailsPanel;
    private PersonDetailsPanelHandle personDetailsPanelHandle;


    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> personDetailsPanel = new PersonDetailsPanel());
        uiPartRule.setUiPart(personDetailsPanel);

        personDetailsPanelHandle = new PersonDetailsPanelHandle(personDetailsPanel.getRoot());
    }

    @Test
    public void display() {
        //default
        assertTableDisplay(personDetailsPanel, null);

        // associated table of a person
        postNow(selectionChangedEventStub);
        assertTableDisplay(personDetailsPanel, selectionChangedEventStub.getNewSelection().person);

    }

    /**
     * Asserts that {@code personDetailsPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertTableDisplay(PersonDetailsPanel personDetailsPanel, Person expectedPerson) {
        guiRobot.pauseForHuman();

        PersonDetailsPanelHandle personDetailsPanelHandle = new PersonDetailsPanelHandle(personDetailsPanel.getRoot());

        // verify person details are displayed correctly
        assertTableDisplaysPerson(expectedPerson, personDetailsPanelHandle);
    }
}
```
###### /java/seedu/address/ui/DailySchedulerTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import guitests.guihandles.DailySchedulerPanelHandle;
import seedu.address.commons.events.ui.DailyScheduleShownChangedEvent;
import seedu.address.commons.events.ui.UpdateNumberOfButtonsEvent;

public class DailySchedulerTest extends GuiUnitTest {
    private DailyScheduleShownChangedEvent dailyScheduleShownChangedEventStub;
    private UpdateNumberOfButtonsEvent updateNumberOfButtonsEventStub;

    private DailySchedulerPanel dailySchedulerPanel;
    private DailySchedulerPanelHandle dailySchedulerPanelHandle;

    private List<Event> eventsListForTesting;


    @Before
    public void setUp() {

        eventsListForTesting = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            eventsListForTesting.add(generateValidEvents());
        }

        dailyScheduleShownChangedEventStub = new DailyScheduleShownChangedEvent(eventsListForTesting);
        updateNumberOfButtonsEventStub = new UpdateNumberOfButtonsEvent((eventsListForTesting.size() - 1));

        guiRobot.interact(() -> dailySchedulerPanel = new DailySchedulerPanel());
        uiPartRule.setUiPart(dailySchedulerPanel);

        dailySchedulerPanelHandle = new DailySchedulerPanelHandle(dailySchedulerPanel.getRoot());
    }

    @Test
    public void display() {
        postNow(dailyScheduleShownChangedEventStub);
        postNow(updateNumberOfButtonsEventStub);

        dailySchedulerPanelHandle = new DailySchedulerPanelHandle(dailySchedulerPanel.getRoot());
        assertChangedEventsShown(dailySchedulerPanelHandle, eventsListForTesting);
        assertNumberOfButtonsAdded(dailySchedulerPanelHandle, eventsListForTesting);

    }

    /**
     * Asserts that {@code dailySchedulerPanel} displays correct number of scheduled cards.
     */
    private void assertChangedEventsShown(DailySchedulerPanelHandle dailySchedulerPanelHandle,
            List<Event> eventsListForTesting) {
        assertEquals(eventsListForTesting.size(), dailySchedulerPanelHandle.getNumOfEventsShown());
    }

    /**
     * Asserts that {@code dailySchedulerPanel} displays the number of buttons correctly.
     */
    private void assertNumberOfButtonsAdded(DailySchedulerPanelHandle dailySchedulerPanelHandle,
            List<Event> eventsListForTesting) {
        if (eventsListForTesting.size() != 0) {
            assertEquals(eventsListForTesting.size() - 1, dailySchedulerPanelHandle.getNumOfButtons());
        } else {
            assertEquals(eventsListForTesting.size(), dailySchedulerPanelHandle.getNumOfButtons());
        }
    }

    /**
     * Created a valid event with a location.
     * @returns Event
     */
    private Event generateValidEvents() {
        Event validEvent = new Event()
                .setSummary("Test Calendar Event")
                .setLocation("One Sansome St., San Francisco, CA 94104");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-08:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        validEvent.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        validEvent.setEnd(end);

        return validEvent;
    }
}
```
###### /java/seedu/address/ui/ScheduledEventCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.logic.OAuthManager;

public class ScheduledEventCardTest extends GuiUnitTest {

    private static final String EVENT_NUM_HEADER = "EVENT NUM: ";
    private static final String EVENT_TITLE_HEADER = "TITLE: ";
    private static final String EVENT_TIMING_HEADER = "TIME: ";
    private static final String EVENT_LOCATION_HEADER = "LOCATION: ";
    private static final String EVENT_PERSON_HEADER = "NAME: ";
    private static final String EVENT_CONDITION_HEADER = "CONDITION: ";
    private static final String EVENT_MOBILE_HEADER = "MOBILE: ";
    private static final String FUTURE_IMPLEMENTATION = "TO BE IMPLEMENTED IN 2.0";
    private static final String EVENT_DIVIDER = "================================ \n";

    private ScheduledEventCard scheduledEventCard;

    private Event eventToTest;
    private int eventIndex;

    @Before
    public void setUp() {

        eventToTest = new Event()
                .setSummary("Test Calendar Event")
                .setLocation("One Sansome St., San Francisco, CA 94104");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToTest.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToTest.setEnd(end);

        eventIndex = 1;

        String expectedEventAsString = "Test Calendar Event From: 2018-04-30 09:00 AM To: 2018-04-30 05:00 PM"
                + " @ One Sansome St., San Francisco, CA 94104";

        guiRobot.interact(() -> scheduledEventCard = new ScheduledEventCard(eventToTest, eventIndex));
        uiPartRule.setUiPart(scheduledEventCard);
    }

    @Test
    public void formatScheduledEvent_allFieldsPresent_success() {
        Event eventToFormat = new Event()
                .setSummary("Test Calendar Event")
                .setLocation("One Sansome St., San Francisco, CA 94104");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToFormat.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        eventToFormat.setEnd(end);

        int eventIndexToFormat = 1;

        DateTime startAsDateTime = eventToFormat.getStart().getDateTime();
        DateTime endAsDateTime = eventToFormat.getEnd().getDateTime();

        String startAsString = OAuthManager.getDateTimeAsHumanReadable(startAsDateTime);
        String endAsString = OAuthManager.getDateTimeAsHumanReadable(endAsDateTime);

        ScheduledEventCard eventCardToFormat = new ScheduledEventCard(eventToFormat, eventIndexToFormat);

        String eventAsString = EVENT_NUM_HEADER + "1" + "\n"
                + EVENT_TITLE_HEADER + eventToFormat.getSummary() + "\n"
                + EVENT_TIMING_HEADER + startAsString + " - " + endAsString + "\n"
                + EVENT_DIVIDER
                + EVENT_LOCATION_HEADER + eventToFormat.getLocation() + "\n"
                + EVENT_PERSON_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_MOBILE_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_CONDITION_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_DIVIDER + EVENT_DIVIDER;

        assertEquals(eventAsString, eventCardToFormat.getFormattedScheduledEvent());
    }

    @Test
    public void formatScheduledEvent_nullLocation_success() {
        Event nullLocationEventToFormat = new Event()
                .setSummary("Test Calendar Event");

        DateTime startDateTime = new DateTime("2018-04-30T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles");
        nullLocationEventToFormat.setStart(start);

        DateTime endDateTime = new DateTime("2018-04-30T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles");
        nullLocationEventToFormat.setEnd(end);

        int eventIndexToFormat = 1;

        DateTime startAsDateTime = nullLocationEventToFormat.getStart().getDateTime();
        DateTime endAsDateTime = nullLocationEventToFormat.getEnd().getDateTime();

        String startAsString = OAuthManager.getDateTimeAsHumanReadable(startAsDateTime);
        String endAsString = OAuthManager.getDateTimeAsHumanReadable(endAsDateTime);

        ScheduledEventCard eventCardToFormat = new ScheduledEventCard(nullLocationEventToFormat, eventIndexToFormat);

        String eventAsString = EVENT_NUM_HEADER + "1" + "\n"
                + EVENT_TITLE_HEADER + nullLocationEventToFormat.getSummary() + "\n"
                + EVENT_TIMING_HEADER + startAsString + " - " + endAsString + "\n"
                + EVENT_DIVIDER
                + EVENT_LOCATION_HEADER + "No Location Specified" + "\n"
                + EVENT_PERSON_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_MOBILE_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_CONDITION_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_DIVIDER + EVENT_DIVIDER;

        assertEquals(eventAsString, eventCardToFormat.getFormattedScheduledEvent());
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code personDetailsPanel} displays the details of {@code expectedPerson}.
     */
    public static void assertTableDisplaysPerson(Person expectedPerson,
            PersonDetailsPanelHandle actualPersonDetailsPanelHandle) {
        if (expectedPerson != null) {
            assertEquals(expectedPerson.getName().fullName, actualPersonDetailsPanelHandle.getName());
            assertEquals(expectedPerson.getPhone().value, actualPersonDetailsPanelHandle.getPhone());
            assertEquals(expectedPerson.getEmail().value, actualPersonDetailsPanelHandle.getEmail());
            assertEquals(expectedPerson.getAddress().value, actualPersonDetailsPanelHandle.getAddress());
        } else {
            assertEquals("", actualPersonDetailsPanelHandle.getName());
            assertEquals("", actualPersonDetailsPanelHandle.getPhone());
            assertEquals("", actualPersonDetailsPanelHandle.getEmail());
            assertEquals("", actualPersonDetailsPanelHandle.getAddress());
        }
    }
}
```
###### /java/seedu/address/ui/testutil/EventsCollectorRule.java
``` java
        /**
         * Returns the most recent list of events collected
         */
        public List<BaseEvent> getMostRecentList() {
            if (events.isEmpty()) {
                return null;
            }

            return events;
        }

        /**
         * Returns the specified recent event collected
         */
        public BaseEvent getEventByIndex(int index) {
            if (events.isEmpty()) {
                return null;
            }

            return events.get(index);
        }
```
###### /java/seedu/address/logic/parser/NavigateCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see SelectCommandParserTest
 */

public class NavigateCommandParserTest {

    private NavigateCommandParser parser = new NavigateCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(ParserUtil.MESSAGE_INVALID_EVENT_INDEX));
    }
}
```
###### /java/seedu/address/logic/parser/SwitchCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SwitchCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SwitchCommandParserTest {

    private SwitchCommandParser parser = new SwitchCommandParser();

    @Test
    public void parse_validArgs_returnsSwitchCommand() {
        assertParseSuccess(parser, "details", new SwitchCommand("details"));
        assertParseSuccess(parser, "calendar", new SwitchCommand("calendar"));
        assertParseSuccess(parser, "scheduler", new SwitchCommand("scheduler"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "feature",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "detail",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/SwitchCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.SwitchFeatureEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SwitchCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_switchFeature_success() {
        CommandResult result = new SwitchCommand("details").execute();
        assertEquals("Switched to details tab", result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getEventByIndex(0) instanceof SwitchFeatureEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getEventByIndex(1) instanceof RemoveMapPanelEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof LoadMapPanelEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 3);
    }
}
```
###### /java/guitests/guihandles/DailySchedulerPanelHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class DailySchedulerPanelHandle extends NodeHandle<Node> {

    public static final String DAILY_SCHEDULER_PLACEHOLDER = "#dailySchedulerPlaceholder";
    private static final String EVENTS_LIST_STACK_FIELD_ID = "#eventsListStack";
    private static final String BUTTON_STACK_FIELD_ID = "#buttonStack";

    private final VBox eventsListStack;
    private final VBox buttonStack;

    private int numOfEventsShown;
    private int numOfButtons;

    public DailySchedulerPanelHandle(Node dailySchedulerPanelNode) {
        super(dailySchedulerPanelNode);

        this.eventsListStack = getChildNode(EVENTS_LIST_STACK_FIELD_ID);
        this.buttonStack = getChildNode(BUTTON_STACK_FIELD_ID);

        this.numOfEventsShown = this.eventsListStack.getChildren().size();
        this.numOfButtons = this.buttonStack.getChildren().size();
    }

    public int getNumOfEventsShown() {
        return numOfEventsShown;
    }

    public int getNumOfButtons() {
        return numOfButtons;
    }
}
```
###### /java/guitests/guihandles/PersonDetailsPanelHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class PersonDetailsPanelHandle extends NodeHandle<Node> {

    public static final String PERSON_DETAILS_PANEL_PLACEHOLDER = "#personDetailsPlaceholder";
    private static final String NAME_FIELD_ID = "#nameLabel";
    private static final String ADDRESS_FIELD_ID = "#addressLabel";
    private static final String PHONE_FIELD_ID = "#phoneNumberLabel";
    private static final String EMAIL_FIELD_ID = "#emailLabel";

    private final Label nameLabel;
    private final Label phoneNumberLabel;
    private final Label emailLabel;
    private final Label addressLabel;

    public PersonDetailsPanelHandle(Node personDetailsPanelNode) {
        super(personDetailsPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneNumberLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneNumberLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }
}
```
