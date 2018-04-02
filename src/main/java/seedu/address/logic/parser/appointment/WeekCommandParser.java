package seedu.address.logic.parser.appointment;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.MonthCommand;
import seedu.address.logic.commands.appointment.WeekCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.Year;
import java.time.YearMonth;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author trafalgarandre
/**
 * Parses input arguments and creates a new WeekCommand object
 */
public class WeekCommandParser implements Parser<WeekCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WeekCommand
     * and returns an WeekCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WeekCommand parse(String args) throws ParseException {
        try {
            Year year = ParserUtil.parseYearOfWeek(args);
            int week = ParserUtil.parseWeek(args);
            return new WeekCommand(year, week);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeekCommand.MESSAGE_USAGE));
        }
    }
}
