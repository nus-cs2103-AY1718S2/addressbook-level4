//@@author Kyholmes
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPatientQueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddPatientQueueCommand object
 */
public class AddPatientQueueCommandParser implements Parser<AddPatientQueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPatientQueueCommandParser
     * and returns an AddPatientQueueCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPatientQueueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new AddPatientQueueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPatientQueueCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                   String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientQueueCommand.MESSAGE_USAGE));
        }
    }
}
