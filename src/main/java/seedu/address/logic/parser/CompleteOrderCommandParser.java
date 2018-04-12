//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CompleteOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompleteOrderCommand object
 */
public class CompleteOrderCommandParser implements Parser<CompleteOrderCommand> {

    private static String FRONT_OF_QUEUE = "1";

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteOrderCommand
     * and returns an CompleteOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteOrderCommand parse(String args) throws ParseException {
        try {
            Index numberOfTimes = ParserUtil.parseIndex(args);
            Index index = ParserUtil.parseIndex(FRONT_OF_QUEUE);
            return new CompleteOrderCommand(index,numberOfTimes);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteOrderCommand.MESSAGE_USAGE));
        }
    }

}


