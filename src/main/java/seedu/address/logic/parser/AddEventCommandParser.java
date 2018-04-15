package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_TIME;
import static seedu.address.model.CalendarEvent.EventEndDate.INVALID_END_DATE_MESSAGE;
import static seedu.address.model.CalendarEvent.EventEndTime.INVALID_END_TIME_MESSAGE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarEvent.CalendarEvent;
import seedu.address.model.CalendarEvent.EventEndDate;
import seedu.address.model.CalendarEvent.EventEndTime;
import seedu.address.model.CalendarEvent.EventName;
import seedu.address.model.CalendarEvent.EventStartDate;
import seedu.address.model.CalendarEvent.EventStartTime;

//@@author wayneong95

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_START_DATE, PREFIX_EVENT_START_TIME,
                        PREFIX_EVENT_END_DATE, PREFIX_EVENT_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_NAME, PREFIX_EVENT_START_DATE, PREFIX_EVENT_START_TIME,
                PREFIX_EVENT_END_DATE, PREFIX_EVENT_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        try {
            EventName eventName =
                    ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME)).get();
            EventStartDate eventStartDate =
                    ParserUtil.parseEventStartDate(argMultimap.getValue(PREFIX_EVENT_START_DATE)).get();
            EventStartTime eventStartTime =
                    ParserUtil.parseEventStartTime(argMultimap.getValue(PREFIX_EVENT_START_TIME)).get();
            EventEndDate eventEndDate =
                    ParserUtil.parseEventEndDate(argMultimap.getValue(PREFIX_EVENT_END_DATE)).get();
            EventEndTime eventEndTime =
                    ParserUtil.parseEventEndTime(argMultimap.getValue(PREFIX_EVENT_END_TIME)).get();

            dateRestrictions(eventStartDate, eventEndDate);

            //If event is on same day, check for time restrictions

            if (eventStartDate.toString().equals(eventEndDate.toString())) {
                try {
                    timeRestrictions(eventStartTime, eventEndTime);
                }
                catch (Exception e) {
                    throw new ParseException(INVALID_END_TIME_MESSAGE);
                }
            }

            CalendarEvent calendarEvent = new CalendarEvent(eventName, eventStartDate, eventStartTime,
                    eventEndDate, eventEndTime);

            return new AddEventCommand(calendarEvent);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        catch (Exception e) {
            throw new ParseException(INVALID_END_DATE_MESSAGE);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if eventEndDate is earlier then eventStartDate and throws an exception if so.
     */

    public void dateRestrictions (EventStartDate eventStartDate, EventEndDate eventEndDate) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = sdf.parse(eventStartDate.toString());
            Date date2 = sdf.parse(eventEndDate.toString());
            if (date2.before(date1)) {
                throw new Exception("End Date cannot be earlier than Start Date!");
            }
        } catch (java.text.ParseException e) {
            System.out.println("ParseException");
        }
    }

    /**
     * Checks if eventEndTime is earlier then eventStartTime if events are
     * on the same day and throws an exception if so.
     */

    public void timeRestrictions (EventStartTime eventStartTime, EventEndTime eventEndTime) throws Exception {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");

        try {
            Date time1 = timeFormat.parse(eventStartTime.toString());
            Date time2 = timeFormat.parse(eventEndTime.toString());
            if (time2.before(time1)) {
                throw new Exception("End Time cannot be earlier than Start Time!");
            }
        } catch (java.text.ParseException e) {
            System.out.println("ParseException");
        }
    }

}
