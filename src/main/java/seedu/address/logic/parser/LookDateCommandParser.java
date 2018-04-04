package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_FOCUS;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LookDateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author yuxiangSg
/**
 * Parses input arguments and creates a new LookDateCommand object
 */
public class LookDateCommandParser implements Parser<LookDateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LookDateCommand
     * and returns anLookDateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LookDateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE_FOCUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE_FOCUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LookDateCommand.MESSAGE_USAGE));
        }

        try {

            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_FOCUS)).get();

            return new LookDateCommand(date);
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
