package seedu.address.logic.parser.appointment;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.DateTimeCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author trafalgarandre
/**
 * Parses input arguments and creates a new DateCommand object
 */
public class DateTimeCommandParser implements Parser<DateTimeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DateTimeCommand
     * and returns an DateTimeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DateTimeCommand parse(String args) throws ParseException {
        try {
            LocalDateTime date = ParserUtil.parseDateTime(args);
            return new DateTimeCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateTimeCommand.MESSAGE_USAGE));
        }
    }
}
