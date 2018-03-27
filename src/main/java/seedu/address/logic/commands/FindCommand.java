package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keyphrases.
 * Keyphrase matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose:\n"
            + "name and/or tags "
            + "contains any of the specified keyphrases (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: find (n/NAME_KEYPHRASE | t/TAG_KEYPHRASE | r/RATING_KEYPHRASE)  [n/NAME_KEYPHRASE]... [t/TAG_KEYPHRASE]... [r/RATING_KEYPHRASE]...\n"
            + "Example: " + COMMAND_WORD + " n/Alice Bob n/Charlie t/Friends t/OwesMoney";

    private final Predicate<Person> predicate;

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
