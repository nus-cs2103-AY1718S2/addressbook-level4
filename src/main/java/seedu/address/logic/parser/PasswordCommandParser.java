package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PasswordCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class PasswordCommandParser implements Parser<PasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PasswordCommand parse(String arguments) throws ParseException {
        String args = arguments.trim();
        if ("".equals(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.INVALID_PASSWORD,
                            PasswordCommand.MESSAGE_USAGE));
        }
        return new PasswordCommand(args);
    }
}
