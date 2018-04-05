package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.ReopenIssueCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author adityaa1998
/**
 * Parses input arguments and creates a new CloseIssueCommand object
 */
public class ReopenIssueCommandParser implements Parser<ReopenIssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReopenIssueCommand
     * and returns an ReopenIssueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReopenIssueCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReopenIssueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReopenIssueCommand.MESSAGE_USAGE));
        }
    }
}

