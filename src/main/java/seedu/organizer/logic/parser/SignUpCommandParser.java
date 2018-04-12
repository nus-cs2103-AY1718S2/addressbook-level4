package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_REPEATED_SAME_PREFIXES;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.SignUpCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Parses input arguments and creates a new SignUpCommand object
 */
public class SignUpCommandParser implements Parser<SignUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SignUpCommand
     * and returns an SignUpCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SignUpCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE));
        }

        if (arePrefixesRepeated(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_SAME_PREFIXES, SignUpCommand.MESSAGE_USAGE));
        }

        try {
            String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            String password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();
            User user = new User(username, password);
            return new SignUpCommand(user);
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

    //@@author guekling
    /**
     * Returns true if any of the prefixes contains multiple values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesRepeated(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> (argumentMultimap.getSize(prefix) > 1));
    }
    //@@author
}
