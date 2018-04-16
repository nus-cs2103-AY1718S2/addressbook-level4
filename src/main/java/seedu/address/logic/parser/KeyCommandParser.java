package seedu.address.logic.parser;

//@@author lohtianwei
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.KeyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input and creates KeyCommand object with password provided
 */
public class KeyCommandParser implements Parser<KeyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the KeyCommand
     * and returns a KeyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public KeyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        /*if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, KeyCommand.MESSAGE_USAGE));
        }*/

        String[] argKeywords = trimmedArgs.split("\\s+");
        if (argKeywords.length > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, KeyCommand.MESSAGE_USAGE));
        }

        return new KeyCommand(argKeywords[0]);
    }
}
