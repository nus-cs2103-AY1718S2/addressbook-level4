package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.DeleteSubtaskCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteSubtaskCommandParser implements Parser<DeleteSubtaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSubtaskCommand parse(String args) throws ParseException {
        try {
            Index[] indexs = ParserUtil.parseSubtaskIndex(args);
            return new DeleteSubtaskCommand(indexs[0], indexs[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSubtaskCommand.MESSAGE_USAGE));
        }
    }
}
