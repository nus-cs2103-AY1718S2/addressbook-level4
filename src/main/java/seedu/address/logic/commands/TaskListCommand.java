package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

//@@author CYX28
/**
 * Lists all tasks in the address book to the user.
 */
public class TaskListCommand extends Command {

    public static final String COMMAND_WORD = "task-list";
    public static final String COMMAND_ALIAS = "tl";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks in Address Book.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
