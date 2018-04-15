package seedu.address.logic.parser.appointment;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.Year;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.appointment.YearCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author trafalgarandre
/**
 * Parses input arguments and creates a new YearCommand object
 */
public class YearCommandParser implements Parser<YearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the YearCommand
     * and returns an YearCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public YearCommand parse(String args) throws ParseException {
        try {
            Year year = ParserUtil.parseYear(args);
            return new YearCommand(year);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, YearCommand.MESSAGE_USAGE));
        }
    }
}
