package seedu.organizer.logic.commands;

import seedu.organizer.model.task.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in organizer book whose name contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindNameCommand extends Command {

    public static final String COMMAND_WORD = "findn";
    public static final String COMMAND_ALIAS = "fn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " cs2103 cs2101 es2660";

    private final NameContainsKeywordsPredicate predicate;

    public FindNameCommand(NameContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindNameCommand // instanceof handles nulls
                && this.predicate.equals(((FindNameCommand) other).predicate)); // state check
    }
}
