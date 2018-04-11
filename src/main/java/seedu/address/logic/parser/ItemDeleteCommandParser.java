//@@author chenchongsong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ItemDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ItemDeleteCommand object.
 * {@code indexPerson} represents the index of the person whose item the user want to delete.
 * {@code indexItem} represents the index of the item that the user want to delete.
 */
public class ItemDeleteCommandParser implements Parser<ItemDeleteCommand> {

    private Index indexPerson;
    private Index indexItem;

    /**
     * Parses the given {@code String} of arguments in the context of the ItemDeleteCommand
     * and returns an ItemDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemDeleteCommand parse(String args) throws ParseException {
        try {
            parsePersonItemIndices(args);
            return new ItemDeleteCommand(indexPerson, indexItem);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of two indices into indexPerson and indexItem.
     * @param args A string of two indices separated by a whitespace
     * @throws IllegalValueException
     */
    private void parsePersonItemIndices(String args) throws IllegalValueException {
        String[] indices = args.trim().split(" ");
        if (indices.length != 2) {
            throw new IllegalValueException(ParserUtil.MESSAGE_INVALID_INDEX);
        }
        indexPerson = ParserUtil.parseIndex(indices[0]);
        indexItem = ParserUtil.parseIndex(indices[1]);
    }

}
