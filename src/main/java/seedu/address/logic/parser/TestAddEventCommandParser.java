package seedu.address.logic.parser;
//@@author crizyli
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Scanner;
import java.util.stream.Stream;

import com.google.api.client.util.DateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TestAddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TestAddEventCommand object
 */
public class TestAddEventCommandParser implements Parser<TestAddEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TestAddEventCommand
     * and returns a TestAddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TestAddEventCommand parse(String args) throws ParseException {

        requireNonNull(args);
        Scanner sc = new Scanner(args);
        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(((Integer) sc.nextInt()).toString());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
        }

        String temp = args.trim();
        int i;
        for (i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == 32) {
                break;
            }
        }
        String behindArgs = temp.substring(i);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(behindArgs, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_STARTTIME,
                        PREFIX_ENDTIME, PREFIX_DESCCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_STARTTIME, PREFIX_ENDTIME,
                PREFIX_DESCCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
        }


        String title = argMultimap.getValue(PREFIX_TITLE).get();
        String location = argMultimap.getValue(PREFIX_LOCATION).get();
        String sTime = argMultimap.getValue(PREFIX_STARTTIME).get();
        String eTime = argMultimap.getValue(PREFIX_ENDTIME).get();
        try {
            DateTime.parseRfc3339(sTime);
        } catch (NumberFormatException n) {
            throw new ParseException("Invalid date/time format: " + sTime);
        }

        try {
            DateTime.parseRfc3339(eTime);
        } catch (NumberFormatException n) {
            throw new ParseException("Invalid date/time format: " + eTime);
        }

        String description = argMultimap.getValue(PREFIX_DESCCRIPTION).get();

        return new TestAddEventCommand(index, title, location, sTime, eTime, description);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
