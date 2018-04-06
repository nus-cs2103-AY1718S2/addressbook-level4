package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.TeamName;

//@@author jordancjq
/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser implements Parser<RemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveComand
     * and returns a RemoveCommand object for execution.
     * @throws ParseException if the user input des not conform to the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {
        try {
            TeamName teamToRemove = ParserUtil.parseTeamName(args);
            return new RemoveCommand(teamToRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }
    }
}
