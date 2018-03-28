package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class TaskCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no task categories
        Task taskWithNoCategories = new TaskBuilder().withTaskCategories(new String[0]).build();
        TaskCard taskCard = new TaskCard(taskWithNoCategories, 1);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, taskWithNoCategories, 1);

        // with task categories
        Task taskWithCategories = new TaskBuilder().build();
        taskCard = new TaskCard(taskWithCategories, 2);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, taskWithCategories, 2);
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 0);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        //different task, same index -> returns false
        Task differentTask = new TaskBuilder().withTaskName("differentTaskName").build();
        assertFalse(taskCard.equals(new TaskCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(task, 1)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedTask} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, Task expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle((taskCard.getRoot()));

        // Verify id is displaed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getTaskId());

        // Verify task details are displayed correctly
        assertCardDisplaysTask(expectedTask, taskCardHandle);
    }

}
