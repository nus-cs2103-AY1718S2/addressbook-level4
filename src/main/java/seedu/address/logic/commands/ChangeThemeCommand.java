//@@author amad-person
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.theme.Theme.MESSAGE_THEME_CONSTRAINTS;
import static seedu.address.model.theme.Theme.isValidTheme;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.theme.Theme;

/**
 * Changes the theme of the application.
 */
public class ChangeThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of the application.\n"
            + "Parameters: THEME \n"
            + "Example: " + COMMAND_WORD + " light";

    public static final String MESSAGE_THEME_CHANGED_SUCCESS = "Theme changed to %1$s";
    public static final String MESSAGE_INVALID_THEME = "Theme %1$s not supported.\n" + MESSAGE_THEME_CONSTRAINTS;

    private final String themeVersion;

    public ChangeThemeCommand(String themeVersion) {
        requireNonNull(themeVersion);
        this.themeVersion = themeVersion;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!isValidTheme(themeVersion)) {
            throw new CommandException(String.format(MESSAGE_INVALID_THEME, themeVersion));
        }

        Theme.setCurrentTheme(themeVersion);

        EventsCenter.getInstance().post(new ChangeThemeEvent(themeVersion));
        return new CommandResult(String.format(MESSAGE_THEME_CHANGED_SUCCESS, themeVersion));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && this.themeVersion.equals(((ChangeThemeCommand) other).themeVersion)); // state check
    }
}
