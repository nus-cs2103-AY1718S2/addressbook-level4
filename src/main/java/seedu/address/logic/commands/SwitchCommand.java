package seedu.address.logic.commands;

import seedu.address.ui.Calendar;

public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final String MESSAGE_SUCCESS_CALENDAR = "View is switched to calendar";
    public static final String MESSAGE_SUCCESS_TIMETABLE = "View is switched to timetable";


    @Override
    public CommandResult execute() {
        if (Calendar.isViewed) {
            model.indicateTimetableChanged();
            return new CommandResult(MESSAGE_SUCCESS_TIMETABLE);
        }
        model.indicateCalendarChanged();
        return new CommandResult(MESSAGE_SUCCESS_CALENDAR);
    }
}
