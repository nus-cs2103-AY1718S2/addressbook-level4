//@@author Kai Yong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemovePatientQueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemovePatientQueueCommand object
 */
public class RemovePatientQueueCommandParser implements Parser<RemovePatientQueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePatientQueueCommand
     * and returns an RemovePatientQueueCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemovePatientQueueCommand parse(String args) throws ParseException {

        if (args.isEmpty()) {
            return new RemovePatientQueueCommand();
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemovePatientQueueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePatientQueueCommand.MESSAGE_USAGE));
        }
    }
}
