package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_INTERVAL;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.stream.Stream;

import com.calendarfx.model.Interval;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.InsuranceCalendar.AppointmentEntry;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandPaser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_INTERVAL, PREFIX_END_INTERVAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_INTERVAL, PREFIX_END_INTERVAL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            String appointmentTitle = ParserUtil.parseString(argMultimap.getValue(PREFIX_NAME)).get();
            LocalDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_INTERVAL)).get();
            LocalDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_INTERVAL)).get();
            Interval interval = new Interval(startDate, LocalTime.MIN, endDate, LocalTime.MAX);


            AppointmentEntry appointmentEntry = new AppointmentEntry(appointmentTitle, interval);

            return new AddAppointmentCommand(appointmentEntry);
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
