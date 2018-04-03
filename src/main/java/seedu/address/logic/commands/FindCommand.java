package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.FindResults;
import seedu.address.model.person.Person;

//@@author tanhengyeow
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
            + "Option 2 (Search by prefix): /n[KEYWORD] [MORE_KEYWORDS] /p...\n"
            + "Example: " + COMMAND_WORD + " n/Alex Bernice p/999 555";

    public static final int LEVENSHTEIN_DISTANCE_THRESHOLD = 2;

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        FindResults.getInstance().clearResults(); // clear existing results if any (e.g. when undo command is executed)
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getFindMessageForPersonListShownSummary());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
