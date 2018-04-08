package seedu.address.logic.commands;

/**
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
        String printableSchedule = model.printSchedule();
        return new CommandResult(MESSAGE_SUCCESS + printableSchedule);
    }
}
