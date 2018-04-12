package seedu.address.logic.parser;
//@@author SuxianAlicia

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_DATE;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CalendarJumpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalendarJumpCommand object
 */
public class CalendarJumpCommandParser implements Parser<CalendarJumpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CalendarJumpCommand
     * and returns an CalendarJumpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CalendarJumpCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_TARGET_DATE);

        if (!isPrefixPresent(argMultimap, PREFIX_TARGET_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarJumpCommand.MESSAGE_USAGE));
        }

        try {
            String dateString = argMultimap.getValue(PREFIX_TARGET_DATE).get();
            LocalDate date = ParserUtil.parseTargetDate(dateString);
            return new CalendarJumpCommand(date, dateString);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    private boolean isPrefixPresent(ArgumentMultimap argMultimap, Prefix prefixTargetDate) {
        return argMultimap.getValue(prefixTargetDate).isPresent();
    }
}
