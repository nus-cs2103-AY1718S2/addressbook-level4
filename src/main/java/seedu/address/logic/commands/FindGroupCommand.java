package seedu.address.logic.commands;

import seedu.address.model.person.GroupsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose groups contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroupCommand extends Command {

    public static final String COMMAND_WORD = "groupfind";
    public static final String COMMAND_ALIAS = "gf";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "KEYWORD "
            + "[MORE KEYWORDS]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose groups contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends colleagues neighbours";

    private final GroupsContainKeywordsPredicate groupsContainKeywordsPredicate;

    public FindGroupCommand(GroupsContainKeywordsPredicate predicate) {
        this.groupsContainKeywordsPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(groupsContainKeywordsPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindGroupCommand // instanceof handles nulls
                && this.groupsContainKeywordsPredicate.equals
                (((FindGroupCommand) other).groupsContainKeywordsPredicate)); // state check
    }
}
