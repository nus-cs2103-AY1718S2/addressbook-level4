package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CURRENT_TASKS;

//@@author WoodySIN
/**
 * Lists all tasks due by the current month stored in the address book.
 */
public class ListCurrentTaskCommand extends Command {

    public static final String COMMAND_WORD = "listCurrentTask";
    public static final String COMMAND_ALIAS = "lct";

    public static final String MESSAGE_SUCCESS = "Listed all tasks due this month";

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_CURRENT_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
