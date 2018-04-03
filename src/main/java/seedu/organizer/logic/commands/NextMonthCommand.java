package seedu.organizer.logic.commands;

//@@author guekling
/**
 * Shows the view of the month after the currently viewed month.
 */
public class NextMonthCommand extends Command {

    public static final String COMMAND_WORD = "nmonth";
    public static final String COMMAND_ALIAS = "nm";

    public static final String MESSAGE_SUCCESS = "Shows next month";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
