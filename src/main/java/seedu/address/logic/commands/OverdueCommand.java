package seedu.address.logic.commands;

import seedu.address.logic.OverdueChecker;

//@@author Kyomian
/**
 * Lists all overdue tasks
 */
public class OverdueCommand extends Command {

    public static final String COMMAND_WORD = "overdue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of tasks that remain uncompleted after their respective due dates.";

    public static final String SHOWN_OVERDUE_MESSAGE = "Number of overdue tasks: %d";

    @Override
    public CommandResult execute() {
        int numOverdueTasks = OverdueChecker.getNumOverdueTasks();
        return new CommandResult(String.format(SHOWN_OVERDUE_MESSAGE, numOverdueTasks));
    }


}
