package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EventTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    public static final String DATE_VALIDATION_FORMAT = "dd-MM-yyyy"; // legal dates
    public static final String TIME_VALIDATION_FORMAT = "HH:mm"; // legal time format
    public static final String STANDARD_START_TIME = "00:00"; //Start Time of event if StartTime not given
    public static final String START_AND_END_DATE_CONSTRAINTS = "Start Date cannot be later than End Date.";
    public static final String START_AND_END_TIME_CONSTRAINTS =
            "Start Time cannot be later than End Time if Event ends on same date.";

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddEventCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT_TITLE, PREFIX_START_DATE, PREFIX_END_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_TITLE, PREFIX_END_DATE, PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {

            EventTitle eventTitle = ParserUtil.parseEventTitle(argMultimap.getValue(PREFIX_EVENT_TITLE)).get();
            EndDate endDate = ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_END_DATE)).get();
            StartDate startDate;

            // If no Start Date is given, Start Date will be the same date as End Date
            if (!argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
                startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_END_DATE)).get();
            } else {
                startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_START_DATE)).get();
            }

            if (startDateLaterThanEndDate(startDate, endDate)) {
                throw new IllegalValueException(START_AND_END_DATE_CONSTRAINTS);
            }

            EndTime endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            StartTime startTime;

            // If no Start Time is given, Start Time will be 00:00
            if (!argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
                startTime = ParserUtil.parseStartTime(STANDARD_START_TIME);
            } else {
                startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            }

            if (startDate.toString().equals(endDate.toString()) && startTimeLaterThanEndTime(startTime, endTime)) {
                throw new IllegalValueException(START_AND_END_TIME_CONSTRAINTS);
            }

            CalendarEvent calendarEvent = new CalendarEvent(eventTitle, startDate, endDate, startTime, endTime);
            return new AddEventCommand(calendarEvent);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if given start time is later than end time.
     * Start time cannot be later than End time if event ends on the same date.
     */
    private boolean startTimeLaterThanEndTime(StartTime startTime, EndTime endTime) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_VALIDATION_FORMAT);
        format.setLenient(false);

        Date formattedStartTime;
        Date formattedEndTime;

        try {
            formattedStartTime = format.parse(startTime.toString());
            formattedEndTime = format.parse(endTime.toString());
        } catch (java.text.ParseException pe) {
            throw new AssertionError("StartTime and EndTime should have valid format.");
        }

        if (formattedStartTime.compareTo(formattedEndTime) > 0) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if given start date is later than end date.
     * Start Date cannot be later than End Date as it violates the meaning of the terms.
     */
    private static boolean startDateLaterThanEndDate(StartDate startDate, EndDate endDate) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_VALIDATION_FORMAT);
        format.setLenient(false);

        Date formattedStartDate;
        Date formattedEndDate;

        try {
            formattedStartDate = format.parse(startDate.toString());
            formattedEndDate = format.parse(endDate.toString());

        } catch (java.text.ParseException pe) {
            throw new AssertionError("StartDate and EndDate should have valid format.");
        }

        if (formattedStartDate.compareTo(formattedEndDate) > 0) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
