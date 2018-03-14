package seedu.address.logic.commands;

import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "ft";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "KEYWORD "
            + "[MORE KEYWORDS]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends colleagues neighbours";

    private final TagsContainKeywordsPredicate tagsContainKeywordsPredicate;

    public FindTagCommand(TagsContainKeywordsPredicate predicate) {
        this.tagsContainKeywordsPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(tagsContainKeywordsPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.tagsContainKeywordsPredicate.equals
                (((FindTagCommand) other).tagsContainKeywordsPredicate)); // state check
    }
}
