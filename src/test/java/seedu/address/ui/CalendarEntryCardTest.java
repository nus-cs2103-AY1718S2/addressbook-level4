package seedu.address.ui;
//@@author SuxianAlicia
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.Test;

import guitests.guihandles.CalendarEntryCardHandle;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.testutil.CalendarEntryBuilder;

public class CalendarEntryCardTest extends GuiUnitTest {
    @Test
    public void display() {
        CalendarEntry calendarEntry = new CalendarEntryBuilder().build();
        CalendarEntryCard calendarEntryCard = new CalendarEntryCard(calendarEntry, 1);
        uiPartRule.setUiPart(calendarEntryCard);
        assertCardDisplay(calendarEntryCard, calendarEntry, 1);
    }

    @Test
    public void equals() {
        CalendarEntry calendarEntry = new CalendarEntryBuilder().build();
        CalendarEntryCard calendarEntryCard = new CalendarEntryCard(calendarEntry, 0);

        // same calendar entry, same index -> returns true
        CalendarEntryCard entryCardCopy = new CalendarEntryCard(calendarEntry, 0);
        assertTrue(calendarEntryCard.equals(entryCardCopy));

        // same object -> returns true
        assertTrue(calendarEntryCard.equals(calendarEntryCard));

        // null -> returns false
        assertFalse(calendarEntryCard.equals(null));

        // different types -> returns false
        assertFalse(calendarEntryCard.equals(1));

        // different calendar entry, same index -> returns false
        CalendarEntry differentEntry = new CalendarEntryBuilder().withEntryTitle("differentTitle").build();
        assertFalse(calendarEntryCard.equals(new CalendarEntryCard(differentEntry, 0)));

        // same calendar entry, different index -> returns false
        assertFalse(calendarEntryCard.equals(new CalendarEntryCard(calendarEntry, 1)));

    }

    /**
     * Asserts that {@code calendarEntryCard} displays the details of {@code expectedEntry} correctly and
     * matches {@code expectedId}.
     */
    private void assertCardDisplay(CalendarEntryCard calendarEntryCard, CalendarEntry expectedEntry, int expectedId) {
        guiRobot.pauseForHuman();

        CalendarEntryCardHandle entryCardHandle = new CalendarEntryCardHandle(calendarEntryCard.getRoot());

        // verify that id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", entryCardHandle.getId());

        // verify calendar entry details are displayed correctly
        assertCardDisplaysEntry(expectedEntry, entryCardHandle);
    }
}
