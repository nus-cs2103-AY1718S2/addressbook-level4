package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Activity;

/**
 * A utility class for Activity.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code activity}.
     */
    public static String getAddCommand(Activity activity) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(activity);
    }

    /**
     * Returns the part of command string for the given {@code activity}'s details.
     */
    public static String getPersonDetails(Activity activity) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + activity.getName().fullName + " ");
        sb.append(PREFIX_PHONE + activity.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + activity.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + activity.getAddress().value + " ");
        activity.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
