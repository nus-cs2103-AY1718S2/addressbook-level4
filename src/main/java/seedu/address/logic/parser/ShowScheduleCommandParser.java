//@@author ifalluphill

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ShowScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowScheduleCommand object
 */
public class ShowScheduleCommandParser implements Parser<ShowScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SCHEDULE_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_SCHEDULE_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowScheduleCommand.MESSAGE_USAGE));
        }


        try {
            String selectedDateAsString = ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_SCHEDULE_DATE).orElse(""));
            LocalDate selectedDate = convertFriendlyDateTimeToDateTime(selectedDateAsString);

            return new ShowScheduleCommand(selectedDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Converts a human-readable date time string into a usable date time string
     */
    private LocalDate convertFriendlyDateTimeToDateTime(String datetime) {
        List<Date> dates = new com.joestelmach.natty.Parser().parse(datetime).get(0).getDates();
        Date inputDate = dates.get(0);
        Instant instant = inputDate.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

        return localDate;
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
