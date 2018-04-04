package seedu.organizer.logic.commands;

import seedu.organizer.model.task.DescriptionContainsKeywordsPredicate;

/**
 * Finds and lists all persons in organizer book whose name contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindDescriptionCommand extends Command {

    public static final String COMMAND_WORD = "finddes";
    public static final String COMMAND_ALIAS = "fdes";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose description contain any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " update script OP1";

    private final DescriptionContainsKeywordsPredicate predicate;

    public FindDescriptionCommand(DescriptionContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindDescriptionCommand // instanceof handles nulls
                && this.predicate.equals(((FindDescriptionCommand) other).predicate)); // state check
    }
}
