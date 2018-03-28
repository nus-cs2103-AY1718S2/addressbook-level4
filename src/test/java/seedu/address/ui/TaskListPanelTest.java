package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

public class TaskListPanelTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());

    private TaskListPanelHandle taskListPanelHandle;

    @Before
    public void setUp() {
        TaskListPanel taskListPanel = new TaskListPanel(TYPICAL_TASKS);
        uiPartRule.setUiPart(taskListPanel);

        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
                TaskListPanelHandle.TASK_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
            Task expectedTask = TYPICAL_TASKS.get(i);
            TaskCardHandle actualCard = taskListPanelHandle.getTaskCardHandle(i);

            assertCardDisplaysTask(expectedTask, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getTaskId());
        }
    }
}
