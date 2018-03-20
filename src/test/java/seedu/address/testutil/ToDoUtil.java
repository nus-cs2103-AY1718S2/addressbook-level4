package seedu.address.testutil;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.todo.ToDo;

/**
 * A utility class for ToDo.
 */
public class ToDoUtil {

    /**
     * Returns an addToDo command string for adding the {@code todo}.
     */
    public static String getAddToDoCommand(ToDo todo) {
        return AddToDoCommand.COMMAND_WORD + " " + getToDoDetails(todo);
    }

    /**
     * Returns the part of command string for the given {@code todo}'s details.
     */
    public static String getToDoDetails(ToDo todo) {
        StringBuilder sb = new StringBuilder();
        sb.append(todo.getContent().value);
        return sb.toString();
    }
}
