package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author KevinChuangCH
/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        String[] splitArgs = args.split(", ");
        if (splitArgs.length == 1) {
            String platform = "all";
            try {
                String inputName = ParserUtil.parseSearchName(splitArgs[0]);
                return new SearchCommand(platform, inputName);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
            }
        } else if (splitArgs.length == 2) {
            try {
                String platform = ParserUtil.parsePlatformToSearch(splitArgs[0]);
                String inputName = ParserUtil.parseSearchName(splitArgs[1]);
                return new SearchCommand(platform, inputName);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
    }
}
