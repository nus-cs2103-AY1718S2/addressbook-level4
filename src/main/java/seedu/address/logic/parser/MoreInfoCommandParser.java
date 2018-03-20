package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MoreInfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MoreInfoCommand object
 */
public class MoreInfoCommandParser implements Parser<MoreInfoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MoreInfoCommand
     * and returns a MoreInfoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoreInfoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MoreInfoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoreInfoCommand.MESSAGE_USAGE));
        }
    }

}

