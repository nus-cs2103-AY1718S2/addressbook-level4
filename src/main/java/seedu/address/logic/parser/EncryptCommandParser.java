package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.commands.EncryptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author tinyjy94
/**
 * Parses input arguments and creates a new EncryptCommand object
 */
public class EncryptCommandParser implements Parser<EncryptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EncryptCommand
     * and returns an EncryptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EncryptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EncryptCommand.MESSAGE_USAGE));
        }
        String password = args.trim();

        return new EncryptCommand(password);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
