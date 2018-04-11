//@@author jas5469
package seedu.address.logic.commands;

import seedu.address.model.person.TagContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag name is of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ListTagMembersCommand extends Command {

    public static final String COMMAND_WORD = "listTagMembers";
    public static final String COMMAND_ALIAS = "ltm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS1010";


    private final TagContainKeywordsPredicate predicate;

    public ListTagMembersCommand(TagContainKeywordsPredicate predicate) {
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
                || (other instanceof ListTagMembersCommand // instanceof handles nulls
                && this.predicate.equals(((ListTagMembersCommand) other).predicate)); // state check
    }
}
