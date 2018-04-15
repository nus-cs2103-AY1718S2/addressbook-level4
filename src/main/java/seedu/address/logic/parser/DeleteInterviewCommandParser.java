//@@author deeheenguyen
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteJobCommand object
 */
public class DeleteInterviewCommandParser implements Parser<DeleteInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteInterviewCommand
     * and returns an DeleteInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteInterviewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteInterviewCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInterviewCommand.MESSAGE_USAGE));
        }
    }

}

