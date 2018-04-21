package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RatingDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kexiaowen
/**
 * Parses input arguments and creates a new RatingDeleteCommand object
 */
public class RatingDeleteCommandParser implements Parser<RatingDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RatingDeleteCommand
     * and returns a RatingDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RatingDeleteCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingDeleteCommand.MESSAGE_USAGE));
        }
        return new RatingDeleteCommand(index);
    }
}
