package seedu.organizer.ui;

import static org.junit.Assert.assertEquals;
import static seedu.organizer.testutil.EventsUtil.postNow;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.organizer.testutil.TypicalTasks.getTypicalTasks;
import static seedu.organizer.ui.testutil.GuiTestAssert.assertCardDisplaysTask;
import static seedu.organizer.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.commons.events.ui.JumpToListRequestEvent;
import seedu.organizer.model.task.Task;

public class TaskListPanelTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS =
            FXCollections.observableList(getTypicalTasks());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_TASK);

    private TaskListPanelHandle taskListPanelHandle;

    @Before
    public void setUp() {
        TaskListPanel taskListPanel = new TaskListPanel(TYPICAL_TASKS);
        uiPartRule.setUiPart(taskListPanel);

        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
                TaskListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
            taskListPanelHandle.navigateToCard(TYPICAL_TASKS.get(i));
            Task expectedTask = TYPICAL_TASKS.get(i);
            TaskCardHandle actualCard = taskListPanelHandle.getTaskCardHandle(i);

            assertCardDisplaysTask(expectedTask, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TaskCardHandle expectedCard = taskListPanelHandle.getTaskCardHandle(INDEX_SECOND_TASK.getZeroBased());
        TaskCardHandle selectedCard = taskListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
