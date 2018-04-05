//@@author ifalluphill
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

    public static final String MESSAGE_SUCCESS = "Displaying directions between...";
    public static final String MESSAGE_FAILURE = "FAILURE";
    private final List<Event> eventPair;

    public NavigateCommand(List<Event> eventPair) {
        this.eventPair = eventPair;
    };

    //@@author jaronchan

    @Override
    public CommandResult execute() {
        if (this.eventPair.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FAILURE));
        }
//        System.out.print("Retrieved: ");
//        System.out.print(this.eventPair.size());
//        System.out.println(" events.");
//        System.out.println(this.eventPair.toString());
        EventsCenter.getInstance().post(
            new LoadDirectionsEvent(
                    this.eventPair.get(0).getLocation(),
                    this.eventPair.get(1).getLocation()
            )
        );
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    //@@author jaronchan
    //    @Override
    //    public boolean equals(Object other) {
    //        return other == this // short circuit if same object
    //                || (other instanceof SwitchCommand // instanceof handles nulls
    //                && this.featureTarget.equals(((SwitchCommand) other).featureTarget)); // state check
    //    }
}

//@@author
