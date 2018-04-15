package seedu.address.logic.commands;

import seedu.address.model.person.SearchContainsKeywordsPredicate;
import seedu.address.ui.util.ListPanelController;

/**
 * Finds and lists all persons in address book that contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons that contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final SearchContainsKeywordsPredicate predicate;

    public FindCommand(SearchContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    //@@author Zhu-Jiahui
    @Override
    public CommandResult execute() {
        if (ListPanelController.isCurrentDisplayActiveList()) {
            model.updateFilteredStudentList(predicate);
            model.updateFilteredTutorList(predicate);
            return new CommandResult(getMessageForClientListShownSummary(
                    model.getFilteredStudentList().size(), model.getFilteredTutorList().size()));
        } else {
            model.updateFilteredClosedStudentList(predicate);
            model.updateFilteredClosedTutorList(predicate);
            return new CommandResult(getMessageForClientListShownSummary(
                    model.getFilteredClosedStudentList().size(), model.getFilteredClosedTutorList().size()));
        }
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
