//@@author jaronchan
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
