package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.EventCommand;
import seedu.address.model.activity.Event;

//@@author Kyomian
/**
 * A utility class for Event.
 */
public class EventUtil {
    /**
     * Returns an event command string for adding the {@code event}.
     */
    public static String getEventCommand(Event event) {
        return EventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName().fullName + " ");
        sb.append(PREFIX_START_DATETIME + event.getStartDateTime().toString() + " ");
        sb.append(PREFIX_END_DATETIME + event.getEndDateTime().toString() + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().toString() + " ");
        sb.append(PREFIX_REMARK + event.getRemark().value + " ");
        event.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
