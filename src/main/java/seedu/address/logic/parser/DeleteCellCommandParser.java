//@@author sarahgoh97
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCellCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given input and gives a new DeleteCellCommand object.
 */
public class DeleteCellCommandParser implements Parser<DeleteCellCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of DeleteCellCommand
     * and returns a DeleteCellCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteCellCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCellCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCellCommand.MESSAGE_USAGE));
        }
    }
}
