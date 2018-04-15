package seedu.address.logic.commands;

import seedu.address.model.person.PersonNameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in event planner whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindPersonCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonNameContainsKeywordsPredicate predicate;

    public FindPersonCommand(PersonNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPersonCommand // instanceof handles nulls
                && this.predicate.equals(((FindPersonCommand) other).predicate)); // state check
    }
}
