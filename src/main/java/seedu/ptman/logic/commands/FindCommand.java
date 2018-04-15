package seedu.ptman.logic.commands;

import seedu.ptman.model.employee.NameContainsKeywordsPredicate;

/**
 * Finds and lists all employees in ptman book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String COMMAND_FORMAT = "KEYWORD [MORE_KEYWORDS]...";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all employees whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + COMMAND_FORMAT
            + "\nExample: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredEmployeeList(predicate);
        return new CommandResult(getMessageForEmployeeListShownSummary(model.getFilteredEmployeeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
