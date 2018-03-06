package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class TaskCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        TaskCard taskCard = new TaskCard(personWithNoTags, 1);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        taskCard = new TaskCard(personWithTags, 2);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        TaskCard taskCard = new TaskCard(person, 0);

        // same person, same index -> returns true
        TaskCard copy = new TaskCard(person, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(taskCard.equals(new TaskCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(person, 1)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, taskCardHandle);
    }
}
