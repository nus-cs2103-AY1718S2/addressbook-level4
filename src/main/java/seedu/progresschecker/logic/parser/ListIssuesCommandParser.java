package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.ListIssuesCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author adityaa1998
/**
 * Parses input arguments and creates a new ListIssuesCommand object
 */
public class ListIssuesCommandParser implements Parser<ListIssuesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListIssueCommand
     * and returns an ListIssueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListIssuesCommand parse(String args) throws ParseException {
        try {
            String issueState = ParserUtil.parseStateType(args);
            return new ListIssuesCommand(issueState);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListIssuesCommand.MESSAGE_USAGE));
        }
    }
}
