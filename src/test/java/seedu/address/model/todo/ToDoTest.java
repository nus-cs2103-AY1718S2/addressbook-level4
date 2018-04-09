//@@author nhatquang3112
package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.ToDoBuilder;

public class ToDoTest {
    @Test
    public void equals() {
        ToDo todoA = new ToDoBuilder().withContent("Something to do").withStatus("undone").build();
        ToDo todoB = new ToDoBuilder().withContent("Something to do").withStatus("undone").build();
        ToDo todoC = new ToDoBuilder().withContent("Something to do").withStatus("done").build();

        // different types -> returns false
        assertFalse(todoA.equals(1));

        // same content -> returns true
        assertTrue(todoA.hashCode() == todoB.hashCode());

        // same content, different status -> returns true
        assertTrue(todoA.equals(todoC));
    }
}
