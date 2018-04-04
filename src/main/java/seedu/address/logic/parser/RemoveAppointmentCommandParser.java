package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_TEXT;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveAppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yuxiangSg
/**
 * Parses input arguments and creates a new RemoveAppointmentsCommand object
 */
public class RemoveAppointmentCommandParser implements Parser<RemoveAppointmentsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveAppointmentsCommand
     * and returns a RemoveAppointsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemoveAppointmentsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEARCH_TEXT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SEARCH_TEXT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveAppointmentsCommand.MESSAGE_USAGE));
        }

        try {
            String searchText = ParserUtil.parseString(argMultimap.getValue(PREFIX_SEARCH_TEXT)).get();
            return new RemoveAppointmentsCommand(searchText);
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
