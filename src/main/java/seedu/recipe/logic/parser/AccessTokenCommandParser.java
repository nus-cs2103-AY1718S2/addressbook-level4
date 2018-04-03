package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recipe.logic.commands.AccessTokenCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AccessTokenCommand object
 */
public class AccessTokenCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AccessTokenCommand
     * and returns an AccessTokenCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AccessTokenCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccessTokenCommand.MESSAGE_USAGE));
        }
        // Check if access token is valid here also in the future
        return new AccessTokenCommand(trimmedArgs);
    }
}
