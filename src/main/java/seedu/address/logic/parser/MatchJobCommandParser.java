package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MatchJobCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/***
 * Parses input arguments in the context of the MatchJobCommand
 * and creates a new MatchJobCommand object
 */
public class MatchJobCommandParser implements Parser<MatchJobCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MatchJobCommand
     * and returns an MatchJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchJobCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MatchJobCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchJobCommand.MESSAGE_USAGE));
        }
    }

}
