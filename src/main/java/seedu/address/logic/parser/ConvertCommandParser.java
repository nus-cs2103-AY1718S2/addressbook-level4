package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ConvertCommand object
 */
public class ConvertCommandParser implements Parser<ConvertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ConvertCommand
     * and returns an ConvertCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConvertCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        try {
            return new ConvertCommand(Double.parseDouble(trimmedArgs));
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }
    }
}
