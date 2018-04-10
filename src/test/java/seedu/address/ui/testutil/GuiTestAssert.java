package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;

//@@author jasmoon
/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {

    /**
     * Asserts that {@code actualTaskCard} displays the same values as {@code expectedTaskCard}.
     */
    public static void assertTaskCardEquals(TaskCardHandle expectedTaskCard, TaskCardHandle actualTaskCard) {
        assertEquals(expectedTaskCard.getId(), actualTaskCard.getId());
        assertEquals(expectedTaskCard.getName(), actualTaskCard.getName());
        assertEquals(expectedTaskCard.getDateTime(), actualTaskCard.getDateTime());
        assertEquals(expectedTaskCard.getRemark(), actualTaskCard.getRemark());
        assertEquals(expectedTaskCard.getTags(), actualTaskCard.getTags());
    }

    /**
     * Asserts that {@code actualEventCard} displays the same values as {@code expectedEventCard}.
     */
    public static void assertCardEqualsEvent(EventCardHandle expectedEventCard, EventCardHandle actualEventCard) {
        assertEquals(expectedEventCard.getId(), actualEventCard.getId());
        assertEquals(expectedEventCard.getName(), actualEventCard.getName());
        assertEquals(expectedEventCard.getStartDateTime(), actualEventCard.getStartDateTime());
        assertEquals(expectedEventCard.getEndDateTime(), actualEventCard.getEndDateTime());
        assertEquals(expectedEventCard.getLocation(), actualEventCard.getLocation());
        assertEquals(expectedEventCard.getRemark(), actualEventCard.getRemark());
        assertEquals(expectedEventCard.getTags(), actualEventCard.getTags());
    }

    /**
     * Asserts that {@code actualTaskCard} displays the details of {@code expectedActivity}.
     */
    public static void assertCardDisplaysTask(Activity expectedTask, TaskCardHandle actualTaskCard) {
        assertEquals(expectedTask.getName().fullName, actualTaskCard.getName());
        assertEquals(expectedTask.getDateTime().getLocalDateTime().toString(), actualTaskCard.getDateTime().toString());
        assertEquals(expectedTask.getRemark().value, actualTaskCard.getRemark());
        assertEquals(expectedTask.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualTaskCard.getTags());
    }

    /**
     * Asserts that {@code actualEventCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(Activity expectedActivity, EventCardHandle actualEventCard) {
        Event expectedEvent = (Event) expectedActivity;
        assertEquals(expectedEvent.getName().fullName, actualEventCard.getName());
        assertEquals(expectedEvent.getStartDateTime().toString(), actualEventCard.getStartDateTime());
        assertEquals(expectedEvent.getEndDateTime().toString(), actualEventCard.getEndDateTime());
        assertEquals(expectedEvent.getLocation(), actualEventCard.getLocation());
        assertEquals(expectedEvent.getRemark().value, actualEventCard.getRemark());
        assertEquals(expectedEvent.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualEventCard.getTags());
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code tasks} correctly and
     * in the correct order.
     */
    public static void assertTaskListMatching(TaskListPanelHandle taskListPanelHandle, Activity... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            assertCardDisplaysTask(tasks[i], taskListPanelHandle.getTaskCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code events} correctly and
     * in the correct order.
     */
    public static void assertEventListMatching(EventListPanelHandle eventListPanelHandle, Activity... activities) {
        for (int i = 0; i < activities.length; i++) {
            Event event = (Event) activities[i];
            assertCardDisplaysEvent(event, eventListPanelHandle.getEventCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code activities} correctly and
     * in the correct order.
     */
    public static void assertTaskListMatching(TaskListPanelHandle taskListPanelHandle, List<Activity> activities) {
        assertTaskListMatching(taskListPanelHandle, activities.toArray(new Activity[0]));
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code activities} correctly and
     * in the correct order.
     */
    public static void assertEventListMatching(EventListPanelHandle eventListPanelHandle, List<Activity> activities) {
        assertEventListMatching(eventListPanelHandle, activities.toArray(new Activity[0]));
    }

    /**
     * Asserts the size of the list in {@code taskListPanelHandle} equals to {@code size}.
     */
    public static void assertTaskListSize(TaskListPanelHandle taskListPanelHandle, int size) {
        int numberOfPeople = taskListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the size of the list in {@code eventListPanelHandle} equals to {@code size}.
     */
    public static void assertEventListSize(EventListPanelHandle eventListPanelHandle, int size) {
        int numberOfPeople = eventListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    //@@author
    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
