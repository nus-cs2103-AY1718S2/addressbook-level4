package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.model.theme.Theme;

//@@author aquarinte
/**
 * Change the theme of Medeina
 */
public class ChangeThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change Medeina's theme to the"
            + "specified theme name (case-insensitive)\n"
            + "Parameters: THEME NAME\n"
            + "Example: " + COMMAND_WORD + " light";

    private String MESSAGE_SUCCESS;

    private final Theme theme;

    public ChangeThemeCommand(Theme theme) {
        requireNonNull(theme);
        this.theme = theme;
        MESSAGE_SUCCESS = "Current theme: " + theme.getThemeName();
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(theme));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && theme.equals(((ChangeThemeCommand) other).theme));
    }
}
