//@@author chenchongsong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ItemShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ItemShowCommand object
 */
public class ItemShowCommandParser implements Parser<ItemShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ItemShowCommand
     * and returns an ItemShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemShowCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ItemShowCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemShowCommand.MESSAGE_USAGE));
        }
    }
}
