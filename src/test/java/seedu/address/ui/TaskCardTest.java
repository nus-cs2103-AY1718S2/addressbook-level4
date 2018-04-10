package seedu.address.ui;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.activity.Activity;
import seedu.address.testutil.TaskBuilder;

//@@author jasmoon
public class TaskCardTest extends GuiUnitTest {

    /*
    @Test
    public void display() {
        // no tags
        Activity activityWithNoTags = new TaskBuilder().withTags(new String[0]).build();
        TaskCard taskCard = new TaskCard(activityWithNoTags, 1);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, activityWithNoTags, 1);

        // with tags
        Activity activityWithTags = new TaskBuilder().build();
        taskCard = new TaskCard(activityWithTags, 2);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, activityWithTags, 2);
    }*/

    @Test
    public void equals() {
        Activity activity = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(activity, 0);

        // same activity, same index -> returns true
        TaskCard copy = new TaskCard(activity, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different activity, same index -> returns false
        Activity differentActivity = new TaskBuilder().withName("differentName").build();
        assertFalse(taskCard.equals(new TaskCard(differentActivity, 0)));

        // same activity, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(activity, 1)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedActivity} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, Activity expectedActivity, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify activity details are displayed correctly
        assertCardDisplaysTask(expectedActivity, taskCardHandle);
    }

}
