//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ProcessMoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ProcessMoreCommand object
 */
public class ProcessMoreCommandParser implements Parser<ProcessMoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteMoreOrderCommand
     * and returns an CompleteMoreOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ProcessMoreCommand parse(String args) throws ParseException {
        try {
            int numberOfTimes = Integer.parseInt(args.trim());
            if (numberOfTimes <= 0) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProcessMoreCommand.MESSAGE_USAGE));
            }
            return new ProcessMoreCommand(numberOfTimes);
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Number must be numerical"));
        }
    }
}



