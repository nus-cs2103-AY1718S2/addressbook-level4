//@@author jaronchan
package seedu.address.logic.commands;

import java.util.List;

import com.google.api.services.calendar.model.Event;

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

    public static final String MESSAGE_SUCCESS = "Displaying directions between Event Num: %1$s and Event Num: %2$s";
    public static final String MESSAGE_INVALID_RANGE = "The INDEX provided is invalid.\n"
            + "INDEX must more than ZERO and less than the number of planned events for the day.";
    public static final String MESSAGE_NO_EVENT = "There is either zero or one event planned for the day.\n"
            + "No directions will be listed.";
    private final List<Event> eventPair;
    private final int targetIndex;

    //@@author ifalluphill
    public NavigateCommand(List<Event> eventPair, int targetIndex) {
        this.eventPair = eventPair;
        this.targetIndex = targetIndex;
    }

    //@@author jaronchan
    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(
            new LoadDirectionsEvent(
                this.eventPair.get(0).getLocation(),
                this.eventPair.get(1).getLocation()
            )
        );
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex, targetIndex + 1));
    }
}
