//@@author kush1509
package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.job.JobMatchCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new JobMatchCommand object
 */
public class JobMatchCommandParser implements Parser<JobMatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JobMatchCommand
     * and returns an JobMatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JobMatchCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new JobMatchCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobMatchCommand.MESSAGE_USAGE));
        }
    }

}
