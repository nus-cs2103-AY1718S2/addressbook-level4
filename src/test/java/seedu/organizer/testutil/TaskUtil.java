package seedu.organizer.testutil;

import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddCommand(Task task) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(task);
    }

    /**
     * Returns an add command string for adding the {@code task} using alias.
     */
    public static String getAddCommandAlias(Task task) {
        return AddCommand.COMMAND_ALIAS + " " + getPersonDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getPersonDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().fullName + " ");
        sb.append(PREFIX_PHONE + task.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + task.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + task.getAddress().value + " ");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
