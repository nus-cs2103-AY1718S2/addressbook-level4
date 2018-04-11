package seedu.progresschecker.logic.commands;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.events.ui.ChangeThemeEvent;

//@@author: Livian1107
/**
 * Changes the thmem of ProgressChecker.
 */
public class ThemeCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + " THEME";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change theme of ProgressChecker.\n"
            + "Parameters: " + "Theme(either 'day' or 'night')\n"
            + "Example: " + COMMAND_WORD + "day";

    public static final String MESSAGE_SUCCESS = "Change to theme %1$s";

    public final String theme;

    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));
        return new CommandResult(String.format(MESSAGE_SUCCESS, theme));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.theme.equals(((ThemeCommand) other).theme)); // state check
    }
}
