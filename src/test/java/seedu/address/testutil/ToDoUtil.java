//@@author nhatquang3112
package seedu.address.testutil;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.todo.ToDo;

/**
 * A utility class for ToDo.
 */
public class ToDoUtil {

    /**
     * Returns an addToDo command string for adding the {@code toDo}.
     */
    public static String getAddToDoCommand(ToDo toDo) {
        return AddToDoCommand.COMMAND_WORD + " " + getToDoDetails(toDo);
    }

    /**
     * Returns the part of command string for the given {@code toDo}'s details.
     */
    public static String getToDoDetails(ToDo toDo) {
        StringBuilder sb = new StringBuilder();
        sb.append(toDo.getContent().value);
        return sb.toString();
    }
}
