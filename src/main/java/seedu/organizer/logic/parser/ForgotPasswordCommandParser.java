package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.ForgotPasswordCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

//@@author dominickenn
/**
 * Parses input arguments and creates a new ForgotPasswordCommand object
 */
public class ForgotPasswordCommandParser implements Parser<ForgotPasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ForgotPasswordCommand
     * and returns a ForgotPasswordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ForgotPasswordCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ForgotPasswordCommand.MESSAGE_USAGE));
        }

        try {
            String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            return new ForgotPasswordCommand(username);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
