//@@author nhatquang3112
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysToDo;

import org.junit.Test;

import guitests.guihandles.ToDoCardHandle;
import seedu.address.model.todo.ToDo;
import seedu.address.testutil.ToDoBuilder;

public class ToDoCardTest extends GuiUnitTest {

    @Test
    public void display() {
        ToDo toDo = new ToDoBuilder().build();
        ToDoCard toDoCard = new ToDoCard(toDo, 1);
        uiPartRule.setUiPart(toDoCard);
        assertCardDisplay(toDoCard, toDo, 1);
    }

    @Test
    public void equals() {
        ToDo toDo = new ToDoBuilder().build();
        ToDoCard toDoCard = new ToDoCard(toDo, 0);

        // same to-do, same index -> returns true
        ToDoCard copy = new ToDoCard(toDo, 0);
        assertTrue(toDoCard.equals(copy));

        // same object -> returns true
        assertTrue(toDoCard.equals(toDoCard));

        // null -> returns false
        assertFalse(toDoCard.equals(null));

        // different types -> returns false
        assertFalse(toDoCard.equals(0));

        // different to-do, same index -> returns false
        ToDo differentToDo = new ToDoBuilder().withContent("different content").build();
        assertFalse(toDoCard.equals(new ToDoCard(differentToDo, 0)));

        // same to-do, different index -> returns false
        assertFalse(toDoCard.equals(new ToDoCard(toDo, 1)));
    }

    /**
     * Asserts that {@code toDoCard} displays the details of {@code expectedToDo} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ToDoCard toDoCard, ToDo expectedToDo, int expectedId) {
        guiRobot.pauseForHuman();

        ToDoCardHandle toDoCardHandle = new ToDoCardHandle(toDoCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", toDoCardHandle.getId());

        // verify to-do details are displayed correctly
        assertCardDisplaysToDo(expectedToDo, toDoCardHandle);
    }
}
