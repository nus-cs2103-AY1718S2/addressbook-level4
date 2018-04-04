//@@author ifalluphill

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_LINK_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAL_START_DATE_TIME;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CalendarAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalendarAddCommand object
 */
public class CalendarAddCommandParser implements Parser<CalendarAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CAL_EVENT_NAME, PREFIX_CAL_START_DATE_TIME,
                        PREFIX_CAL_END_DATE_TIME, PREFIX_CAL_LOCATION, PREFIX_CAL_LINK_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_CAL_EVENT_NAME, PREFIX_CAL_START_DATE_TIME,
                PREFIX_CAL_END_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarAddCommand.MESSAGE_USAGE));
        }


        try {
            String eventName = ParserUtil.parseEventName(
                    argMultimap.getValue(PREFIX_CAL_EVENT_NAME).orElse(""));
            String startDateTime = ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_CAL_START_DATE_TIME).orElse(""));
            String endDateTime = ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_CAL_END_DATE_TIME).orElse(""));
            String location = ParserUtil.parseLocation(
                    argMultimap.getValue(PREFIX_CAL_LOCATION).orElse(""));

            Event newEvent = new Event();
            newEvent.setSummary(eventName);
            EventDateTime start = new EventDateTime().setDateTime(convertFriendlyDateTimeToDateTime(startDateTime));
            newEvent.setStart(start);
            EventDateTime end = new EventDateTime().setDateTime(convertFriendlyDateTimeToDateTime(endDateTime));
            newEvent.setEnd(end);

            if (location != null) {
                newEvent.setLocation(location);
            }

            return new CalendarAddCommand(newEvent);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Converts a human-readable date time string into a usable date time string
     */
    private DateTime convertFriendlyDateTimeToDateTime(String datetime) {
        List<Date> dates = new com.joestelmach.natty.Parser().parse(datetime).get(0).getDates();
        return new DateTime(dates.get(0));
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
