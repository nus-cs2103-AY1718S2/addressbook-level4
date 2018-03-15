package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    private final Theme theme;

    public ChangeThemeCommand(Theme theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

    @Override
    public CommandResult execute() {
        String result = "";
        return new CommandResult(result);
    }
}
