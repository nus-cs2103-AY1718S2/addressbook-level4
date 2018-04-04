package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.CloseIssueCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author adityaa1998
/**
 * Parses input arguments and creates a new CloseIssueCommand object
 */
public class CloseIssueCommandParser implements Parser<CloseIssueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CloseIssueCommand
     * and returns an CloseIssueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloseIssueCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CloseIssueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseIssueCommand.MESSAGE_USAGE));
        }
    }
}

