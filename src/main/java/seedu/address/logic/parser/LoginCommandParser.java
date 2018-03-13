package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LoginCommandParser implements Parser<LoginCommand> {
    public static final int USERNAME_INDEX = 0;
    public static final int PASSWORD_INDEX = 1;
    public static final String SPACE_REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split(SPACE_REGEX);
        if (splitArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }
        String username = splitArgs[USERNAME_INDEX];
        String password = splitArgs[PASSWORD_INDEX];

        return new LoginCommand(username, password);
    }
}
