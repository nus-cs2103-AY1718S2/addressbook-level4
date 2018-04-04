package seedu.address.logic.commands;

//@@author jas5469

import seedu.address.model.person.TagContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag name is of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ListGroupMembersCommand extends Command {

    public static final String COMMAND_WORD = "ListGroupMembers";
    public static final String COMMAND_ALIAS = "lgm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose groups contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS1010";


    private final TagContainKeywordsPredicate predicate;

    public ListGroupMembersCommand(TagContainKeywordsPredicate predicate) {
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
                || (other instanceof ListGroupMembersCommand // instanceof handles nulls
                && this.predicate.equals(((ListGroupMembersCommand) other).predicate)); // state check
    }
}
