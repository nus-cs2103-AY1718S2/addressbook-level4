package seedu.organizer.logic.commands;

//@@author dominickenn

import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.predicates.TaskByStatusPredicate;

/**
 * Lists all completed tasks in the organizer to the user.
 */
public class ListCompletedTasksCommand extends Command {

    public static final String COMMAND_WORD = "completed";
    public static final String COMMAND_ALIAS = "com";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";


    @Override
    public CommandResult execute() {
        Status notDone = new Status(true);
        TaskByStatusPredicate uncompletedStatusPredicate = new TaskByStatusPredicate(notDone);
        model.updateFilteredTaskList(uncompletedStatusPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
