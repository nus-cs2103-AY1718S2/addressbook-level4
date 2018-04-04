package seedu.organizer.logic.commands;

import seedu.organizer.model.task.DeadlineContainsKeywordsPredicate;

//@@author guekling
/**
 * Finds and lists all persons in PrioriTask whose deadline contains any of the argument keywords.
 * Keyword should be in the format of YYYY-MM-DD.
 */
public class FindDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "findd";
    public static final String COMMAND_ALIAS = "fd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose deadline contain any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers. Keywords "
            + "should be in the format of YYYY-MM-DD. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 2018-03-17 2018-05-03";

    private final DeadlineContainsKeywordsPredicate predicate;

    public FindDeadlineCommand(DeadlineContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(predicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDeadlineCommand // instanceof handles nulls
                && this.predicate.equals(((FindDeadlineCommand) other).predicate)); // state check
    }
}
