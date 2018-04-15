//@@author deeheenguyen
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interview.Date;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;

import seedu.address.model.person.Name;

/***
 * Parses input arguments and creates a new AddInterview1Command object
 */

public class AddInterviewCommandParser implements Parser<AddInterviewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddInterviewCommand
     * and returns an AddInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInterviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INTERVIEW, PREFIX_NAME, PREFIX_DATE, PREFIX_LOCATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INTERVIEW, PREFIX_DATE, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterviewCommand.MESSAGE_USAGE));
        }

        try {
            InterviewTitle title = ParserUtil.parseInterviewTitle(argMultimap.getValue(PREFIX_INTERVIEW)).get();
            Name interviewee = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            InterviewLocation location = ParserUtil.parseInterviewLocation(argMultimap.getValue(PREFIX_LOCATION)).get();

            Interview interview = new Interview(title, interviewee, date, location);
            return new AddInterviewCommand(interview);
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
//author@@
