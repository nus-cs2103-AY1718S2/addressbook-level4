//package seedu.address.ui;
//
//import static org.junit.Assert.assertEquals;
//import static seedu.address.testutil.EventsUtil.postNow;
//import static seedu.address.testutil.TypicalActivities.getTypicalTask;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
//import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;
//import static seedu.address.ui.testutil.GuiTestAssert.assertTaskCardEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import guitests.guihandles.TaskCardHandle;
//import guitests.guihandles.TaskListPanelHandle;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.address.commons.events.ui.JumpToListRequestEvent;
//
//import seedu.address.model.activity.Activity;
//import seedu.address.model.activity.Task;
//
//public class TaskListPanelTest extends GuiUnitTest {
//     private static final ObservableList<Activity> TYPICAL_TASKS =
//             FXCollections.observableList(getTypicalTask());
//
//     private static final JumpToListRequestEvent JUMP_TO_FIRST_EVENT =
//             new JumpToListRequestEvent(INDEX_FIRST_ACTIVITY);
//
//     private TaskListPanelHandle taskListPanelHandle;
//
//     public void setUp() {
//         TaskListPanel taskListPanel = new TaskListPanel(TYPICAL_TASKS);
//         uiPartRule.setUiPart(taskListPanel);
//
//         taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
//                 TaskListPanelHandle.TASK_LIST_VIEW_ID));
//     }
//
//     public void display() {
//         for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
//             Task task = (Task) TYPICAL_TASKS.get(i);
//             taskListPanelHandle.navigateToCard(task);
//             Task expectedTask = (Task) TYPICAL_TASKS.get(i);
//             TaskCardHandle actualCard = taskListPanelHandle.getTaskCardHandle(i);
//
//             assertCardDisplaysTask(expectedTask, actualCard);
//             assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
//         }
//     }
//
//     public void handleJumpToListRequestEvent() {
//         postNow(JUMP_TO_FIRST_EVENT);
//         guiRobot.pauseForHuman();
//
//         TaskCardHandle expectedCard = taskListPanelHandle.getTaskCardHandle(INDEX_FIRST_ACTIVITY
//                 .getZeroBased());
//         TaskCardHandle selectedCard = taskListPanelHandle.getHandleToSelectedCard();
//         assertTaskCardEquals(expectedCard, selectedCard);
//     }
// }
