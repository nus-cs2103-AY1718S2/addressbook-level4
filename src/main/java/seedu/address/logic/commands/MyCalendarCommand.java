package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMyCalendarEvent;

/**
 * Show my own calendar
 * */
public class MyCalendarCommand extends Command {

    public static final String COMMAND_WORD = "myCalendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": show my own calendar. ";

    public static final String MESSAGE_SUCCESS = "Your calendar is loaded.";

    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new ShowMyCalendarEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
