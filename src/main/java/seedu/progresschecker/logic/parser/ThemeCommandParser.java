package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.ThemeCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author Livian1107
/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        try {
            String theme = ParserUtil.parseTheme(args);
            return new ThemeCommand(theme);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }
    }
}
