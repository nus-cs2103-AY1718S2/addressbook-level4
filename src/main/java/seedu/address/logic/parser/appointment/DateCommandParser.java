package seedu.address.logic.parser.appointment;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.DateCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author trafalgarandre
/**
 * Parses input arguments and creates a new DateCommand object
 */
public class DateCommandParser implements Parser<DateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DateCommand
     * and returns an DateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DateCommand parse(String args) throws ParseException {
        try {
            LocalDate date = ParserUtil.parseDate(args);
            return new DateCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
        }
    }
}
