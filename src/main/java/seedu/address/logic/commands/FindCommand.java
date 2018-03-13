package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose field contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Option 1 (Search all fields): KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alex david alexyeoh@example.com\n\n"
            + "Option 2 (Search by prefixs): /n[KEYWORD] [MORE_KEYWORDS] /p...\n"
            + "Example: " + COMMAND_WORD + " n/Alex Bernice p/999 555";

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

    public Predicate<Person> getPredicate() {
        return predicate;
    }
}
