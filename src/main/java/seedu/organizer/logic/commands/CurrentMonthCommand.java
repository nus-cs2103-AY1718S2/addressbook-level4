package seedu.organizer.logic.commands;

//@@author guekling
/**
 * Shows the view of the current month.
 */
public class CurrentMonthCommand extends Command {

    public static final String COMMAND_WORD = "cmonth";
    public static final String COMMAND_ALIAS = "cm";

    public static final String MESSAGE_SUCCESS = "Shows current month";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
