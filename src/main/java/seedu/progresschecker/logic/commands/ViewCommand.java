package seedu.progresschecker.logic.commands;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.events.ui.PageLoadChangedEvent;

/**
 * View the web view of outcomes of a particular week.
 */
public class ViewCommand extends Command {

    public static final int MIN_WEEK_NUMBER = 2;
    public static final int MAX_WEEK_NUMBER = 13;

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            // TODO: change description and parameter range when appropriate
            + ": Toggle view to display outcomes in the specified week.\n"
            + "Parameters: INDEX (must be a positive integer ranging from "
            + MIN_WEEK_NUMBER + " to " + MAX_WEEK_NUMBER + ")\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Viewing week %1$s";

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new PageLoadChangedEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.targetIndex.equals(((ViewCommand) other).targetIndex)); // state check
    }
}
