package seedu.address.logic.commands;

import seedu.address.ui.UiTheme;

//@@author tiny767
/**
 * Change the theme of addressbook
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";
    public static final String LIGHT_THEME = "light";
    public static final String DARK_THEME = "dark";
    public static final String MORNING_THEME = "morning";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes theme to. "
            + "Parameters: morning/light/dark";


    public static final String MESSAGE_SUCCESS = "Theme has been changed successfully";

    private final String theme;

    /**
     * Creates a ThemeCommand to change the theme to the specified {@code theme}
     */
    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute() {
        if (theme.equalsIgnoreCase(ThemeCommand.LIGHT_THEME)) {
            UiTheme.getInstance().setToLightTheme();
        } else if (theme.equalsIgnoreCase(ThemeCommand.DARK_THEME)) {
            UiTheme.getInstance().setToDarkTheme();
        } else if (theme.equalsIgnoreCase(ThemeCommand.MORNING_THEME)) {
            UiTheme.getInstance().setToMorningTheme();
        }

        return new CommandResult(MESSAGE_SUCCESS);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ThemeCommand)) {
            return false;
        }

        // state check
        ThemeCommand t = (ThemeCommand) other;
        return theme.equals(t.theme);
    }
}
