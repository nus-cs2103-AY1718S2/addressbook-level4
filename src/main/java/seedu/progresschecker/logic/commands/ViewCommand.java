package seedu.progresschecker.logic.commands;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.events.ui.TabLoadChangedEvent;

//@@author iNekox3
/**
 * Change view of the tab pane in main window based on categories.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_ALIAS = "v";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " TYPE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the tab view to profiles, tasks exercises, issues.\n"
            + "Parameters: TYPE (must be either 'profile', 'task', 'exercise', 'issues')\n"
            + "Example: " + COMMAND_WORD + " exercise";

    public static final String MESSAGE_SUCCESS = "Viewing tab %1$s";

    private final String type;

    public ViewCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new TabLoadChangedEvent(type));
        return new CommandResult(String.format(MESSAGE_SUCCESS, type));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.type.equals(((ViewCommand) other).type)); // state check
    }
}
