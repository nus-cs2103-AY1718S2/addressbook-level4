package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.Theme;
import seedu.address.commons.exceptions.InvalidThemeException;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ThemeCommand object.
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns a ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public ThemeCommand parse(String args) throws ParseException {
        String themeName = args.trim();
        if (themeName.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }

        try {
            Theme newTheme = Theme.getThemeByName(themeName);
            return new ThemeCommand(newTheme);
        } catch (InvalidThemeException e) {
            throw new ParseException(String.format(ThemeCommand.MESSAGE_INVALID_THEME, themeName));
        }
    }
}
