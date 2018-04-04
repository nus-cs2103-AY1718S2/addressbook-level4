package seedu.address.logic.commands;

//@@author Yoochard

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Change existing theme
 */
public class ChangeThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changeTheme";

    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String BRIGHT_THEME_CSS_FILE_NAME = "view/BrightTheme.css";

    public static final String[] THEME_NAMES = {"dark", "bright"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the theme of the application. "
            + "Parameters: " + COMMAND_WORD + " "
            + "[THEME]\n"
            + "Example: " + COMMAND_WORD + " bright";

    public static final String MESSAGE_INVALID_THEME_NAME = "%1$s theme is not supported. "
            + "You can change your theme to one of these: "
            + Arrays.toString(THEME_NAMES);

    public static final String MESSAGE_CHANGE_THEME_SUCCESS = "You have successfully changed your theme.";

    private final String theme;

    /**
     * Constructor
     * @param theme the new theme
     */
    public ChangeThemeCommand(String theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

    /**
     * Returns true if the input string is valid
     */
    public static boolean isValidThemeName(String theme) {
        return Arrays.asList(THEME_NAMES).contains(theme);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        // Check whether a given theme name is valid
        if (!isValidThemeName(theme)) {
            throw new CommandException(String.format(MESSAGE_INVALID_THEME_NAME, theme));
        }

        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));

        return new CommandResult(String.format(MESSAGE_CHANGE_THEME_SUCCESS, theme));
    }
}
