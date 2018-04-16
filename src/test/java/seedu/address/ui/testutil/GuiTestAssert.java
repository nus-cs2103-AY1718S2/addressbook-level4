package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(EventCardHandle expectedCard, EventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getLocation(), actualCard.getLocation());
        assertEquals(expectedCard.getDatetime(), actualCard.getDatetime());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(TaskCardHandle expectedCard, TaskCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getTaskTitle(), actualCard.getTaskTitle());
        assertEquals(expectedCard.getTaskDescription(), actualCard.getTaskDescription());
        assertEquals(expectedCard.getTaskDueDate(), actualCard.getTaskDueDate());
        assertEquals(expectedCard.getTaskPriority(), actualCard.getTaskPriority());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysPerson(Event expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getTitle(), actualCard.getTitle());
        assertEquals(expectedEvent.getDescription(), actualCard.getDescription());
        assertEquals(expectedEvent.getLocation(), actualCard.getLocation());
        assertEquals(expectedEvent.getDatetime().value, actualCard.getDatetime());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(ReadOnlyEvent expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getTitle(), actualCard.getTitle());
        assertEquals(expectedEvent.getDescription(), actualCard.getDescription());
        assertEquals(expectedEvent.getLocation(), actualCard.getLocation());
        assertEquals(expectedEvent.getDatetime().value, actualCard.getDatetime());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTask}.
     */
    public static void assertCardDisplaysTask(Task expectedTask, TaskCardHandle actualCard) {
        assertEquals(expectedTask.getTaskName().value, actualCard.getTaskTitle());
        assertEquals(expectedTask.getTaskDescription().value, actualCard.getTaskDescription());
        if (expectedTask.getTaskStatus().equals("undone")) {
            assertEquals(expectedTask.getTaskDueDate().value, actualCard.getTaskDueDate());
            assertEquals(expectedTask.getTaskPriority().value.toUpperCase(), actualCard.getTaskPriority());
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, Event... events) {
        for (int i = 0; i < events.length; i++) {
            assertCardDisplaysEvent(events[i], eventListPanelHandle.getEventCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code events} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, List<Event> events) {
        assertListMatching(eventListPanelHandle, events.toArray(new Event[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
