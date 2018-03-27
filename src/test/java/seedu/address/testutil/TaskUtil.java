package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.TaskCommand;
import seedu.address.model.activity.Task;

//@@author Kyomian
/**
 * A utility class for Task.
 */
public class TaskUtil {
    /**
     * Returns a task command string for adding the {@code task}.
     */
    public static String getTaskCommand(Task task) {
        return TaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().fullName + " ");
        sb.append(PREFIX_DATE_TIME + task.getDueDateTime().toString() + " ");
        sb.append(PREFIX_REMARK + task.getRemark().value + " ");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
