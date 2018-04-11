//@@author chenchongsong
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ItemAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ItemAddCommand object
 */
public class ItemAddCommandParser implements Parser<ItemAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ItemAddCommand
     * and returns an ItemAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MONEY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MONEY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));
        }

        try {
            return new ItemAddCommand(index,
                    argMultimap.getValue(PREFIX_NAME).get(), argMultimap.getValue(PREFIX_MONEY).get());
        } catch (IllegalArgumentException iae) {
            throw new ParseException(ItemAddCommand.MESSAGE_INVALID_ARGUMENT);
        }
    }

}
