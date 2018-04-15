//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CompleteOneOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompleteOneOrderCommand object
 */
public class CompleteOneOrderCommandParser implements Parser<CompleteOneOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteOneOrderCommand
     * and returns an CompleteOneOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteOneOrderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CompleteOneOrderCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteOneOrderCommand.MESSAGE_USAGE));
        }
    }

}

