package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.commands.UpdatePasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdatePasswordCommand object
 */
public class UpdatePasswordCommandParser implements Parser<UpdatePasswordCommand> {
    /**
     * * Parses the given {@code String} of arguments in the context of the UpdatePasswordCommand
     * *  and returns an UpdatePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdatePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD, PREFIX_NEW_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_PASSWORD, PREFIX_NEW_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdatePasswordCommand.MESSAGE_USAGE));
        } else {
            String inputPassword1 = argMultimap.getValue(PREFIX_PASSWORD).get();
            String inputPassword2 = argMultimap.getValue(PREFIX_NEW_PASSWORD).get();
            return new UpdatePasswordCommand(inputPassword1, inputPassword2);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
