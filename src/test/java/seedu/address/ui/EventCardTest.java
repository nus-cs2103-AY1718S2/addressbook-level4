package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import java.time.LocalDateTime;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

//@@author x3tsunayh

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {

        // past event
        Event pastEvent = new EventBuilder().withTitle("Event Title").build();
        EventCard pastEventCard = new EventCard(pastEvent, 1);
        uiPartRule.setUiPart(pastEventCard);
        assertCardDisplay(pastEventCard, pastEvent, 1);

        // present event
        String presentDatetime = LocalDateTime.now().toString().substring(0, 10) + " 2000";
        Event presentEvent = new EventBuilder().withDatetime(presentDatetime).build();
        EventCard presentEventCard = new EventCard(presentEvent, 1);
        uiPartRule.setUiPart(presentEventCard);
        assertCardDisplay(presentEventCard, presentEvent, 1);

        // incoming event (within 3 days)
        String incomingDatetime = LocalDateTime.now().plusDays(1).toString().substring(0, 10) + " 2000";
        Event incomingEvent = new EventBuilder().withDatetime(incomingDatetime).build();
        EventCard incomingEventCard = new EventCard(incomingEvent, 1);
        uiPartRule.setUiPart(incomingEventCard);
        assertCardDisplay(incomingEventCard, incomingEvent, 1);

        // future event
        Event futureEvent = new EventBuilder().withDatetime("2020-05-21 1800").build();
        EventCard futureEventCard = new EventCard(futureEvent, 1);
        uiPartRule.setUiPart(futureEventCard);
        assertCardDisplay(futureEventCard, futureEvent, 1);

    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different event, same index -> returns false
        Event differentEvent = new EventBuilder().withTitle("differentEvent").build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 0)));

        // same event, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, Event expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
