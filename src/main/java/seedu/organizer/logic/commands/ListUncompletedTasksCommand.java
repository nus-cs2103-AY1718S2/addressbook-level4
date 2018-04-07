package seedu.organizer.logic.commands;

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
        Status notDone = new Status(false);
        TaskByStatusPredicate uncompletedStatusPredicate = new TaskByStatusPredicate(notDone);
        model.updateFilteredTaskList(uncompletedStatusPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
