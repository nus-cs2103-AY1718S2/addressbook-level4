package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowScheduleEvent;

/**
 * @@author demitycho
 * Displays the user's schedule.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views your weekly schedule";

    public static final String MESSAGE_SUCCESS = "Schedule displayed";

    public ScheduleCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(
                new ShowScheduleEvent(model.getSchedule(), model.getAddressBook()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
