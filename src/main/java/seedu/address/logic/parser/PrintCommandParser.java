package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PrintCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrintCommand object
 */
public class PrintCommandParser implements Parser<PrintCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PrintCommand
     * and returns an PrintCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PrintCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PrintCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
        }
    }
}
