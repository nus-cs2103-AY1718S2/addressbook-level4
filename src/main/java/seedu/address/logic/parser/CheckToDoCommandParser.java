//@@author nhatquang3112
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CheckToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CheckToDoCommand object
 */
public class CheckToDoCommandParser implements Parser<CheckToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckToDoCommand
     * and returns an CheckToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckToDoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CheckToDoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));
        }
    }
}
