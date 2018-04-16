package seedu.address.logic.commands;

import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

//@@author CYX28
/**
 * Finds and lists all tasks in address book whose task name contains any of the argument keywords.
 * Keyword matching is case insensitive and allows partial word matching.
 */
public class TaskFindCommand extends Command {

    public static final String COMMAND_WORD = "task-find";
    public static final String COMMAND_ALIAS = "tf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of the "
            + "specified keywords (case-insensitive and partial word matching) and displays them as a list with "
            + "index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " agenda buy meeting";

    private final TaskNameContainsKeywordsPredicate predicate;

    public TaskFindCommand(TaskNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(predicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskFindCommand
                && this.predicate.equals(((TaskFindCommand) other).predicate));
    }

}
