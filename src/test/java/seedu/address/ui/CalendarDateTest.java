//@@author LeonidAgarth
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertDateDisplaysEvent;

import org.junit.Test;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class CalendarDateTest extends GuiUnitTest {

    @Test
    public void display() {
        Event event = new EventBuilder().build();
        CalendarDate date = new CalendarDate(event);
        uiPartRule.setUiPart(date);
        assertCardDisplay(date, event);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        CalendarDate date = new CalendarDate(event);

        // same to-do, same index -> returns true
        CalendarDate copy = new CalendarDate(event);
        assertEquals(date, copy);

        // same object -> returns true
        assertEquals(date, date);

        // null -> returns false
        assertNotEquals(date, null);

        // different types -> returns false
        assertNotEquals(date, 0);

        // different to-do, same index -> returns false
        Event differentEvent = new EventBuilder().withDate("21/12/2012").build();
        assertNotEquals(date, new CalendarDate(differentEvent));
    }

    /**
     * Asserts that {@code toDoCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CalendarDate date, Event expectedEvent) {
        guiRobot.pauseForHuman();
        // verify to-do details are displayed correctly
        assertDateDisplaysEvent(expectedEvent, date);
    }
}
