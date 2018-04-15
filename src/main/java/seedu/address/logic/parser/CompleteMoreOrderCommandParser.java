//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CompleteMoreOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompleteMoreOrderCommand object
 */
public class CompleteMoreOrderCommandParser implements Parser<CompleteMoreOrderCommand> {

    private static final String NUMBER_FRONT_OF_QUEUE = "1";

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteMoreOrderCommand
     * and returns an CompleteMoreOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteMoreOrderCommand parse(String args) throws ParseException {
        try {
            Index numberOfTimes = ParserUtil.parseIndex(args);
            Index index = ParserUtil.parseIndex(NUMBER_FRONT_OF_QUEUE);
            return new CompleteMoreOrderCommand(index, numberOfTimes);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteMoreOrderCommand.MESSAGE_USAGE));
        }
    }

}


