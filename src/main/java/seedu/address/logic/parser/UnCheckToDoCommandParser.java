//@@author nhatquang3112
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnCheckToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnCheckToDoCommand object
 */
public class UnCheckToDoCommandParser implements Parser<UnCheckToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnCheckToDoCommand
     * and returns an UnCheckToDoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnCheckToDoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnCheckToDoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));
        }
    }
}
