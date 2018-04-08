package seedu.organizer.logic.commands;

import seedu.organizer.model.task.predicates.MultipleFieldsContainsKeywordsPredicate;

//@@author guekling
/**
 * Finds and lists all tasks in organizer whose name, descriptions or deadline contains any of the argument
 * keywords.
 * Keyword matching is not case sensitive.
 */
public class FindMultipleFieldsCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names, descriptions or "
            + "deadlines contain any of the specified keywords (not case-sensitive) and displays them as a list with "
            + "index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " cs2103 2018-03-17 assignment";

    private final MultipleFieldsContainsKeywordsPredicate predicate;

    public FindMultipleFieldsCommand(MultipleFieldsContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindMultipleFieldsCommand // instanceof handles nulls
                && this.predicate.equals(((FindMultipleFieldsCommand) other).predicate)); // state check
    }
}
