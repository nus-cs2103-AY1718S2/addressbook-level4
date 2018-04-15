package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX_OR_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.GoToTaskUrlCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author EdwardKSG
/**
 * Parses input arguments and creates a new GoToTaskUrlCommand object
 */
public class GoToTaskUrlCommandParser implements Parser<GoToTaskUrlCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GoToTaskUrlCommand
     * and returns a GoToTaskUrlCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GoToTaskUrlCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTaskIndex(args);
            return new GoToTaskUrlCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_INDEX_OR_FORMAT, GoToTaskUrlCommand.MESSAGE_USAGE));
        }
    }

}
