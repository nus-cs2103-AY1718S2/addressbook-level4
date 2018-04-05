package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SearchAllCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SearchAllCommand object
 */
public class SearchAllCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchAllCommand
     * and returns an SearchAllCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchAllCommand parse(String args) throws ParseException {
        try {
            String inputName = ParserUtil.parseSearchName(args);
            return new SearchAllCommand(inputName);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchAllCommand.MESSAGE_USAGE));
        }
    }
}
