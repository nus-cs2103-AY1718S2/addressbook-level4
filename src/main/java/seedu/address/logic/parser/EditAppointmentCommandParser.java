package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_TEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_INTERVAL;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.calendarfx.model.Interval;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.AppointmentEntry;

/**
 * Parses input arguments and creates a new EditAppointmentCommand object
 */
public class EditAppointmentCommandParser implements Parser<EditAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAppointCommand
     * and returns an ditAppointCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SEARCH_TEXT, PREFIX_NAME,
                        PREFIX_START_INTERVAL, PREFIX_END_INTERVAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_SEARCH_TEXT, PREFIX_NAME,
                PREFIX_START_INTERVAL, PREFIX_END_INTERVAL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            String searchText = ParserUtil.parseString(argMultimap.getValue(PREFIX_SEARCH_TEXT)).get();
            String appointmentTitle = ParserUtil.parseString(argMultimap.getValue(PREFIX_NAME)).get();
            LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_INTERVAL)).get();
            LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_INTERVAL)).get();
            Interval interval = new Interval(startDateTime, endDateTime);


            AppointmentEntry appointmentEntry = new AppointmentEntry(appointmentTitle, interval);

            return new EditAppointmentCommand(searchText, appointmentEntry);
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
