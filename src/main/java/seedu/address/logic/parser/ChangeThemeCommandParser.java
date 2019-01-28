package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.theme.Theme;

//@@author aquarinte
/**
 * Parses input arguments and creates a new ChangeThemeCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns a ChangeThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ChangeThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }

        if (hasMoreThanOneArgument(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }

        if (!Theme.hasValidThemeName(trimmedArgs.toLowerCase())) {
            throw new ParseException(Theme.MESSAGE_THEME_CONSTRAINTS);
        }

        return new ChangeThemeCommand(new Theme(trimmedArgs.toLowerCase()));
    }

    /**
     * Returns true if {@code trimmedArgs} contains more than 1 argument/word (separated by space).
     */
    private boolean hasMoreThanOneArgument(String trimmedArgs) {
        String[] splitArgs = trimmedArgs.split(" ");
        if (splitArgs.length > 1) {
            return true;
        }
        return false;
    }

}
