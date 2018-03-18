package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteRatingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRatingCommand object
 */
public class DeleteRatingCommandParser implements Parser<DeleteRatingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRatingCommand
     * and returns a DeleteRatingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRatingCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRatingCommand.MESSAGE_USAGE));
        }
        return new DeleteRatingCommand(index);
    }
}
