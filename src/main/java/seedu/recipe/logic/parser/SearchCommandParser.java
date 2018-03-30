//@@author kokonguyen191
package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recipe.logic.commands.SearchCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SearchCommand} object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCOmmand
     * and returns an {@code SearchCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new SearchCommand(String.join("+", keywords));
    }

}
