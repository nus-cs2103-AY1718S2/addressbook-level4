//@@author jaronchan
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoadDirectionsEvent;

/**
 * Displays directions between locations scheduled for the day based on specified events of the day.
 */
public class NavigateCommand extends Command {

    public static final String COMMAND_WORD = "navigate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates Daily Scheduler map to display navigation between locations.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Displaying directions between...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new LoadDirectionsEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    //    @Override
    //    public boolean equals(Object other) {
    //        return other == this // short circuit if same object
    //                || (other instanceof SwitchCommand // instanceof handles nulls
    //                && this.featureTarget.equals(((SwitchCommand) other).featureTarget)); // state check
    //    }
}
