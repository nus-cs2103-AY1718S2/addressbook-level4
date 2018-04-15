package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCalendarCardTask;

import org.junit.Test;

import guitests.guihandles.CalendarTaskCardHandle;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author JoonKai1995
public class CalendarTaskCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Task normalTask = new TaskBuilder().build();
        CalendarTaskCard taskCard = new CalendarTaskCard(normalTask);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, normalTask);
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        CalendarTaskCard calendarTaskCard = new CalendarTaskCard(task);

        // same person, same index -> returns true
        CalendarTaskCard copy = new CalendarTaskCard(task);
        assertTrue(calendarTaskCard.equals(copy));

        // same object -> returns true
        assertTrue(calendarTaskCard.equals(calendarTaskCard));

        // null -> returns false
        assertFalse(calendarTaskCard.equals(null));

        // different types -> returns false
        assertFalse(calendarTaskCard.equals(0));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CalendarTaskCard taskCard, Task expectedTask) {
        guiRobot.pauseForHuman();

        CalendarTaskCardHandle calendarTaskCardHandle = new CalendarTaskCardHandle(taskCard.getRoot());

        // verify person details are displayed correctly
        assertCalendarCardTask(expectedTask, calendarTaskCardHandle);
    }
}
