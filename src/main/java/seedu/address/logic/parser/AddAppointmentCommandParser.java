package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;

//@@author kengsengg
/**
 * Parses input arguments and creates a new AppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentCommand
     * and returns an AppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INFO, PREFIX_DATE, PREFIX_START_TIME,
                        PREFIX_END_TIME);

        if (!(argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_INFO, PREFIX_DATE, PREFIX_START_TIME,
                PREFIX_END_TIME)) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            String name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get().toString();
            String info = ParserUtil.parseInfo(argMultimap.getValue(PREFIX_INFO)).get();
            String date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            String startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            String endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            Appointment appointment = new Appointment(name, info, date, startTime, endTime);

            return new AddAppointmentCommand(appointment);
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
//@@author
