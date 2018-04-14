//@@author ZhangYijiong
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import seedu.address.logic.commands.ProcessOrderCommand;
import seedu.address.model.task.Task;

/**
 * Implementation follows {@code PersonUtil}
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an addOrder command string for adding the {@code task}.
     */
    public static String getAddOrderCommand(Task task) {
        return ProcessOrderCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ORDER + task.getOrder().toString() + " ");
        sb.append(PREFIX_ADDRESS + task.getAddress().value + " ");
        sb.append(PREFIX_PRICE + task.getPrice().value + " ");
        sb.append(PREFIX_DISTANCE + task.getDistance().value + " ");
        sb.append(PREFIX_COUNT + task.getCount().value + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription() + " ");

        return sb.toString();
    }
}

