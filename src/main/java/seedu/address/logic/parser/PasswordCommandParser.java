package seedu.address.logic.parser;

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
        if (args.equals("")) {
            throw new ParseException(PasswordCommand.INVALID_PASSWORD);
        }
        return new PasswordCommand(args);
    }
}
