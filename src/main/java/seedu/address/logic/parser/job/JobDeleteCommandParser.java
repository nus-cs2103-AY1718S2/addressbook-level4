package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.job.JobDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new JobDeleteCommand object
 */
public class JobDeleteCommandParser implements Parser<JobDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JobDeleteCommand
     * and returns an JobDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JobDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new JobDeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobDeleteCommand.MESSAGE_USAGE));
        }
    }
}
