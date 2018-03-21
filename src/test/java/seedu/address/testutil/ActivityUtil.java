package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.TaskCommand;
import seedu.address.model.activity.Activity;

/**
 * A utility class for Activity.
 */
//TODO : Add in comment to add in
public class ActivityUtil {

    /**
     * Returns an add command string for adding the {@code activity}.
     */
    public static String getTaskCommand(Activity activity) {
        return TaskCommand.COMMAND_WORD + " " + getActivityDetails(activity);
    }

    /**
     * Returns the part of command string for the given {@code activity}'s details.
     */
    public static String getActivityDetails(Activity activity) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + activity.getName().fullName + " ");
        sb.append(PREFIX_PHONE + activity.getDateTime().toString() + " ");
        sb.append(PREFIX_ADDRESS + activity.getRemark().value + " ");
        activity.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
