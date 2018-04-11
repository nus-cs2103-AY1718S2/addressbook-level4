package seedu.organizer.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.ui.testutil.GuiTestAssert.assertEntryCardDisplaysName;

import org.junit.Test;

import guitests.guihandles.EntryCardHandle;
import seedu.organizer.model.task.Task;
import seedu.organizer.testutil.TaskBuilder;
import seedu.organizer.ui.calendar.EntryCard;

//@@author guekling
public class EntryCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Task task = new TaskBuilder().build();
        EntryCard entryCard = new EntryCard(task);
        uiPartRule.setUiPart(entryCard);
        assertCardDisplay(entryCard, task);
    }

    @Test
    public void getTask() {
        Task task = new TaskBuilder().build();
        EntryCard entryCard = new EntryCard(task);
        assertTaskEquals(task, entryCard.getTask());
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        EntryCard entryCard = new EntryCard(task);

        // same task, same index -> returns true
        EntryCard copy = new EntryCard(task);
        assertTrue(entryCard.equals(copy));

        // same object -> returns true
        assertTrue(entryCard.equals(entryCard));

        // null -> returns false
        assertFalse(entryCard.equals(null));

        // different types -> returns false
        assertFalse(entryCard.equals(0));
    }

    /**
     * Asserts that {@code entryCard} displays the name of {@code expectedTask} correctly.
     */
    private void assertCardDisplay(EntryCard entryCard, Task expectedTask) {
        guiRobot.pauseForHuman();

        EntryCardHandle entryCardHandle = new EntryCardHandle(entryCard.getRoot());

        // verify task name is displayed correctly
        assertEntryCardDisplaysName(expectedTask, entryCardHandle);
    }

    /**
     * Asserts that {@code actualTask} equals to that of {@code expectedTask}.
     */
    private void assertTaskEquals(Task expectedTask, Task actualTask) {
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getUpdatedPriority(), actualTask.getUpdatedPriority());
        assertEquals(expectedTask.getDeadline(), actualTask.getDeadline());
        assertEquals(expectedTask.getDateAdded(), actualTask.getDateAdded());
        assertEquals(expectedTask.getDateCompleted(), actualTask.getDateCompleted());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getStatus(), actualTask.getStatus());
        assertEquals(expectedTask.getTags(), actualTask.getTags());
        assertEquals(expectedTask.getSubtasks(), actualTask.getSubtasks());
    }
}
