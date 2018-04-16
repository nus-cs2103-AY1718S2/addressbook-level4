package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;

import seedu.address.logic.commands.TaskAddCommand;
import seedu.address.model.task.Task;

//@@author CYX28
/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns a taskAdd command string for adding the {@code task}.
     */
    public static String getTaskAddCommand(Task task) {
        return TaskAddCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TASK_NAME + task.getTaskName().value + " ");
        sb.append(PREFIX_TASK_PRIORITY + task.getTaskPriority().value + " ");
        sb.append(PREFIX_TASK_DESCRIPTION + task.getTaskDescription().value + " ");
        sb.append(PREFIX_TASK_DUE_DATE + task.getTaskDueDate().value + " ");
        sb.append(PREFIX_TASK_STATUS + task.getTaskStatus().value + " ");
        task.getTaskCategories().stream().forEach(s -> sb.append(PREFIX_TASK_CATEGORY
                + s.taskCategoryName.toLowerCase() + " "));
        return sb.toString();
    }

}
