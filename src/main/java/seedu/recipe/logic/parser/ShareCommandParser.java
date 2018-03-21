package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.logic.commands.SelectCommand;
import seedu.recipe.logic.commands.ShareCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

//@@author RyanAngJY
/**
 * Parses input arguments and creates a new ShareCommand object
 */
public class ShareCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ShareCommand
     * and returns an ShareCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ShareCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShareCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
}
//@@author