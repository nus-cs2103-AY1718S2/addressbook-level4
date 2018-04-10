package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.predicates.TaskByStatusPredicate;

//@@author dominickenn
/**
 * Lists all completed tasks in the organizer to the user.
 */
public class ListCompletedTasksCommand extends Command {

    public static final String COMMAND_WORD = "completed";
    public static final String COMMAND_ALIAS = "com";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        Status completed = new Status(true);
        TaskByStatusPredicate completedStatusPredicate = new TaskByStatusPredicate(completed);
        model.updateFilteredTaskList(completedStatusPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
