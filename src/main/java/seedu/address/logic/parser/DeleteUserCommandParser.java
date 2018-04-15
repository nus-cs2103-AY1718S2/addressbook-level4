//@@author zacci
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteUserCommand object
 */
public class DeleteUserCommandParser implements Parser<DeleteUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteUserCommand
     * and returns an AddUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteUserCommand.MESSAGE_USAGE));
        }

        String username = "";

        try {
            username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }


        return new DeleteUserCommand(username);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
