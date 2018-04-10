package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.predicates.TaskByStatusPredicate;

//@@author dominickenn
/**
 * Lists all uncompleted tasks in the organizer to the user.
 */
public class ListUncompletedTasksCommand extends Command {

    public static final String COMMAND_WORD = "uncompleted";
    public static final String COMMAND_ALIAS = "uncom";

    public static final String MESSAGE_SUCCESS = "Listed all uncompleted tasks";

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        Status notUncompleted = new Status(false);
        TaskByStatusPredicate uncompletedStatusPredicate = new TaskByStatusPredicate(notUncompleted);
        model.updateFilteredTaskList(uncompletedStatusPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
