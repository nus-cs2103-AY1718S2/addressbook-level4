package seedu.organizer.logic.commands;

//@@author guekling
/**
 * Shows the view of the month before the currently viewed month.
 */
public class PreviousMonthCommand extends Command {

    public static final String COMMAND_WORD = "pmonth";
    public static final String COMMAND_ALIAS = "pm";

    public static final String MESSAGE_SUCCESS = "Shows previous month";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
