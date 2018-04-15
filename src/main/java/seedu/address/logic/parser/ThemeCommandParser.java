package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author tiny767
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns a ThemeCommand object for execution.
         * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String trimmedArgs = args.trim();
            if (!trimmedArgs.equalsIgnoreCase(ThemeCommand.LIGHT_THEME)
                && !trimmedArgs.equalsIgnoreCase(ThemeCommand.DARK_THEME)
                && !trimmedArgs.equalsIgnoreCase(ThemeCommand.MORNING_THEME)) {
                throw new IllegalValueException("");

            } else {
                return new ThemeCommand(trimmedArgs);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ThemeCommand.MESSAGE_USAGE));
        }

    }

}
