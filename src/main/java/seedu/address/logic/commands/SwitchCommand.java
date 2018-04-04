package seedu.address.logic.commands;

/**
 * Switches between Calendar view and Timetable view
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final String MESSAGE_SUCCESS_CALENDAR = "View is switched to calendar";
    public static final String MESSAGE_SUCCESS_TIMETABLE = "View is switched to timetable";


    @Override
    public CommandResult execute() {
        if (model.calendarIsViewed()) {
            model.indicateTimetableChanged();
            model.switchView();
            return new CommandResult(MESSAGE_SUCCESS_TIMETABLE);
        }
        model.indicateCalendarChanged();
        model.switchView();
        return new CommandResult(MESSAGE_SUCCESS_CALENDAR);
    }
}
