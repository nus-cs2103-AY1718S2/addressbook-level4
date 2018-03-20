package seedu.address.logic.parser;

import java.util.stream.Stream;

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
    public PasswordCommand parse(String args) throws ParseException {
        args = args.trim();
        if (args.equals("")) {
            throw new ParseException(PasswordCommand.INVALID_PASSWORD);
        }
        return new PasswordCommand(args);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
