package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose names, tags, addresses, or phones
 * contains any of the keywords or partial keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find persons whose "
            + "Names (n/) or Tags (t/) or Phones (p/) or Addresses (a/) contain any of "
            + "the keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [PREFIX]/KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie \n"
            + "Example: " + COMMAND_WORD + " t/friends"
            + "Example: " + COMMAND_WORD + " a/5th Avenue"
            + "Example: " + COMMAND_WORD + " p/98985000";

    private Predicate<Person> predicate;

    //@@author jill858
    public FindCommand(Predicate<Person> predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
