package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.joestelmach.natty.DateGroup;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InterviewCommand object
 */
public class InterviewCommandParser implements Parser<InterviewCommand> {

    public static final String MESSAGE_DATETIME_PARSE_FAIL = "Failed to parse the date time from the string: %1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the InterviewCommand
     * and returns an InterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InterviewCommand parse(String args) throws ParseException {
        try {
            // Parse the arguments
            String[] arguments = args.trim().split("\\s+", 2);
            if (arguments.length != 2) {
                throw new IllegalValueException("Invalid command, expected 2 arguments");
            }

            // Parse the index
            Index index = ParserUtil.parseIndex(arguments[0]);

            // Parse the date time
            LocalDateTime dateTime = parseDateFromNaturalLanguage(arguments[1]);

            return new InterviewCommand(index, dateTime);

        } catch (ParseException pe) {
            throw pe;

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given natural language {@code String} and returns a {@code LocalDateTime} object
     * that represents the English representation of the date and time
     * @throws ParseException if the phrase cannot be converted to date and time
     */
    private LocalDateTime parseDateFromNaturalLanguage(String naturalLanguage) throws ParseException {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();

        List<DateGroup> groups = parser.parse(naturalLanguage);
        if (groups.size() < 1) {
            throw new ParseException(String.format(MESSAGE_DATETIME_PARSE_FAIL, naturalLanguage));
        }

        List<Date> dates = groups.get(0).getDates();
        if (dates.size() < 1) {
            throw new ParseException(String.format(MESSAGE_DATETIME_PARSE_FAIL, naturalLanguage));
        }

        Date date = dates.get(0);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
