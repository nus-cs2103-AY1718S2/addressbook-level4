//@@author deeheenguyen
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interview.InterviewMatchInterviewee;
import seedu.address.model.person.Name;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindInterviewCommandParser implements Parser<FindInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindInterviewCommand
     * and returns an FindInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindInterviewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInterviewCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        if (keywords.length > 1 || keywords.length == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInterviewCommand.MESSAGE_USAGE));
        }
        if (!Name.isValidName(keywords[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInterviewCommand.MESSAGE_USAGE));
        }
        return new FindInterviewCommand(new InterviewMatchInterviewee(keywords[0]));
    }
}
