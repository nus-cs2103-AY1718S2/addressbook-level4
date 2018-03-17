package seedu.address.logic.commands;

import seedu.address.model.person.PreferencesContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose preferences contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPreferenceCommand extends Command {
    public static final String COMMAND_WORD = "findpref";
    public static final String COMMAND_ALIAS = "fp";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "KEYWORD "
            + "[MORE KEYWORDS]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose preferences contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " shoes computers videoGames";

    private final PreferencesContainKeywordsPredicate preferencesContainKeywordsPredicate;

    public FindPreferenceCommand(PreferencesContainKeywordsPredicate predicate) {
        this.preferencesContainKeywordsPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(preferencesContainKeywordsPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPreferenceCommand // instanceof handles nulls
                && this.preferencesContainKeywordsPredicate.equals
                (((FindPreferenceCommand) other).preferencesContainKeywordsPredicate)); // state check
    }

}
