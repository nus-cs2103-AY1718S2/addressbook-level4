package seedu.address.logic.parser;
//@@author SuxianAlicia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.EntryTimeConstraintsUtil.checkCalendarEntryTimeConstraints;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;

/**
 * Parses input arguments and creates a new AddEntryCommand object
 */
public class AddEntryCommandParser implements Parser<AddEntryCommand> {

    public static final String STANDARD_START_TIME = "00:00"; //Start Time of event if StartTime not given


    /**
     * Parses the given {@code String} of arguments in the context of the AddEntryCommand
     * and returns an AddEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddEntryCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_ENTRY_TITLE, PREFIX_START_DATE, PREFIX_END_DATE,
                        PREFIX_START_TIME, PREFIX_END_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ENTRY_TITLE, PREFIX_END_DATE, PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));
        }

        try {

            EntryTitle entryTitle = ParserUtil.parseEventTitle(argMultimap.getValue(PREFIX_ENTRY_TITLE)).get();
            EndDate endDate = ParserUtil.parseEndDate(argMultimap.getValue(PREFIX_END_DATE)).get();
            StartDate startDate;

            // If no Start Date is given, Start Date will be the same date as End Date
            if (!argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
                startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_END_DATE)).get();
            } else {
                startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_START_DATE)).get();
            }

            EndTime endTime = ParserUtil.parseEndTime(argMultimap.getValue(PREFIX_END_TIME)).get();
            StartTime startTime;

            // If no Start Time is given, Start Time will be 00:00
            if (!argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
                startTime = ParserUtil.parseStartTime(STANDARD_START_TIME);
            } else {
                startTime = ParserUtil.parseStartTime(argMultimap.getValue(PREFIX_START_TIME)).get();
            }

            checkCalendarEntryTimeConstraints(startDate, endDate, startTime, endTime);

            CalendarEntry calendarEntry = new CalendarEntry(entryTitle, startDate, endDate, startTime, endTime);
            return new AddEntryCommand(calendarEntry);

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
