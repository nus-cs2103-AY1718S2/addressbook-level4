package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ListAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author wynonaK
/**
 * Parses input arguments and creates a new ListAppointmentCommand object
 */
public class ListAppointmentCommandParser implements Parser<ListAppointmentCommand> {

    private static final Pattern LIST_APPOINTMENT_COMMAND_FORMAT_YEAR = Pattern.compile("-(y)+(?<info>.*)");
    private static final Pattern LIST_APPOINTMENT_COMMAND_FORMAT_MONTH = Pattern.compile("-(m)+(?<info>.*)");
    private static final Pattern LIST_APPOINTMENT_COMMAND_FORMAT_WEEK = Pattern.compile("-(w)+(?<info>.*)");
    private static final Pattern LIST_APPOINTMENT_COMMAND_FORMAT_DAY = Pattern.compile("-(d)+(?<info>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ListAppointmentCommand
     * and returns an ListAppointmentCommand object for execution.
     * type changes depending on what pattern it matches
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListAppointmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        final Matcher matcherForYear = LIST_APPOINTMENT_COMMAND_FORMAT_YEAR.matcher(trimmedArgs);
        if (matcherForYear.matches()) {
            int type = 1;

            try {
                Year year = ParserUtil.parseYear(matcherForYear.group("info"));
                return new ListAppointmentCommand(type, year);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
            }
        }

        final Matcher matcherForMonth = LIST_APPOINTMENT_COMMAND_FORMAT_MONTH.matcher(trimmedArgs);
        if (matcherForMonth.matches()) {
            try {
                int type = 2;
                YearMonth yearMonth = ParserUtil.parseMonth(matcherForMonth.group("info"));
                return new ListAppointmentCommand(type, yearMonth);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
            }
        }

        final Matcher matcherForWeek = LIST_APPOINTMENT_COMMAND_FORMAT_WEEK.matcher(trimmedArgs);
        if (matcherForWeek.matches()) {
            try {
                int type = 3;
                LocalDate date = ParserUtil.parseDate(matcherForWeek.group("info"));
                return new ListAppointmentCommand(type, date);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
            }
        }

        final Matcher matcherForDay = LIST_APPOINTMENT_COMMAND_FORMAT_DAY.matcher(trimmedArgs);
        if (matcherForDay.matches()) {
            try {
                int type = 4;
                LocalDate date = ParserUtil.parseDate(matcherForDay.group("info"));
                return new ListAppointmentCommand(type, date);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
            }
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentCommand.MESSAGE_USAGE));
    }
}
