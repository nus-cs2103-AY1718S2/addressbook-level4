package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SearchFacebookCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author KevinChuangCH
/**
 * Parses input arguments and creates a new SearchFacebookCommand object
 */
public class SearchFacebookCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchFacebookCommand
     * and returns an SearchFacebookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchFacebookCommand parse(String args) throws ParseException {
        try {
            String inputName = ParserUtil.parseSearchName(args);
            return new SearchFacebookCommand(inputName);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchFacebookCommand.MESSAGE_USAGE));
        }
    }
}
