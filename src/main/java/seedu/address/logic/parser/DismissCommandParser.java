//@@author IzHoBX
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX_NON_POSITIVE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DismissCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DismissCommand object
 */
public class DismissCommandParser implements Parser<DismissCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DismissCommand
     * and returns an DismissCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DismissCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DismissCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_NOTIFICATION_CARD_INDEX_NON_POSITIVE);
        }
    }
}
