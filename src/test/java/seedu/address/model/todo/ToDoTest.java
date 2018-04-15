//@@author nhatquang3112
package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.ToDoBuilder;

public class ToDoTest {
    @Test
    public void equals() {
        ToDo toDoA = new ToDoBuilder().withContent("Something to do").withStatus("undone").build();
        ToDo toDoB = new ToDoBuilder().withContent("Something to do").withStatus("undone").build();
        ToDo toDoC = new ToDoBuilder().withContent("Something to do").withStatus("done").build();

        // different types -> returns false
        assertFalse(toDoA.equals(1));

        // same content -> returns true
        assertTrue(toDoA.hashCode() == toDoB.hashCode());

        // same content, different status -> returns true
        assertTrue(toDoA.equals(toDoC));
    }
}
