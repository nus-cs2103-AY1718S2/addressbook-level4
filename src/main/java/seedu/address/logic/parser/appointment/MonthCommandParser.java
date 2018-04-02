package seedu.address.logic.parser.appointment;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.MonthCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.YearMonth;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author trafalgarandre
/**
 * Parses input arguments and creates a new MonthCommand object
 */
public class MonthCommandParser implements Parser<MonthCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MonthCommand
     * and returns an MonthCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MonthCommand parse(String args) throws ParseException {
        try {
            YearMonth yearMonth = ParserUtil.parseYearMonth(args);
            return new MonthCommand(yearMonth);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_USAGE));
        }
    }
}
