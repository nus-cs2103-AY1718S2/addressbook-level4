//@@author LeonidAgarth
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * Parses input arguments and creates a new ScheduleGroupCommand object
 */
public class ScheduleGroupCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleGroupCommand
     * and returns an ScheduleGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleGroupCommand parse(String args) throws ParseException {
        try {
            Information information = ParserUtil.parseInformation(args);
            Group group = new Group(information);
            return new ScheduleGroupCommand(group);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleGroupCommand.MESSAGE_USAGE));
        }
    }
}
