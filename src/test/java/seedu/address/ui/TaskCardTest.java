package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import java.time.LocalDateTime;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author x3tsunayh

public class TaskCardTest extends GuiUnitTest {

    @Test
    public void display() {

        // overdue event
        Task pastTask = new TaskBuilder().withTaskDueDate("2017-05-21").build();
        TaskCard pastTaskCard = new TaskCard(pastTask, 1);
        uiPartRule.setUiPart(pastTaskCard);
        assertCardDisplay(pastTaskCard, pastTask, 1);

        // task duedate in 3 days
        String threeDaysToDatetime = LocalDateTime.now().plusDays(2).toString().substring(0, 10);
        Task threeDaysTask = new TaskBuilder().withTaskDueDate(threeDaysToDatetime).build();
        TaskCard threeDaysTaskCard = new TaskCard(threeDaysTask, 1);
        uiPartRule.setUiPart(threeDaysTaskCard);
        assertCardDisplay(threeDaysTaskCard, threeDaysTask, 1);

        // task duedate in 5 days
        String fiveDaysToDatetime = LocalDateTime.now().plusDays(4).toString().substring(0, 10);
        Task fiveDaysTask = new TaskBuilder().withTaskDueDate(fiveDaysToDatetime).build();
        TaskCard fiveDaysTaskCard = new TaskCard(fiveDaysTask, 1);
        uiPartRule.setUiPart(fiveDaysTaskCard);
        assertCardDisplay(fiveDaysTaskCard, fiveDaysTask, 1);

        // high priority task
        Task highPriorityTask = new TaskBuilder().withTaskPriority("high").build();
        TaskCard highPriorityTaskCard = new TaskCard(highPriorityTask, 1);
        uiPartRule.setUiPart(highPriorityTaskCard);
        assertCardDisplay(highPriorityTaskCard, highPriorityTask, 1);

        // low priority task
        Task lowPriorityTask = new TaskBuilder().withTaskPriority("low").build();
        TaskCard lowPriorityTaskCard = new TaskCard(lowPriorityTask, 1);
        uiPartRule.setUiPart(lowPriorityTaskCard);
        assertCardDisplay(lowPriorityTaskCard, lowPriorityTask, 1);

        // low priority task
        Task finishedTask = new TaskBuilder().withTaskStatus("done").build();
        TaskCard finishedTaskCard = new TaskCard(finishedTask, 1);
        uiPartRule.setUiPart(finishedTaskCard);
        assertCardDisplay(finishedTaskCard, finishedTask, 1);
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

        // different task, same index -> returns false
        Task differentTask = new TaskBuilder().withTaskName("DifferentTask").build();
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

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify task details are displayed correctly
        assertCardDisplaysTask(expectedTask, taskCardHandle);
    }
}
