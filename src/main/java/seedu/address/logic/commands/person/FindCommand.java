package seedu.address.logic.commands.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    //@@author KevinCJH
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " " + PREFIX_NAME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose"
            + " NAME or SKILL "
            + "contains any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME_KEYWORDS [MORE_NAME_KEYWORDS] or s/SKILL_KEYWORDS [MORE_SKILL_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " n/Alice Bob\n"
            + "Example: " + COMMAND_WORD + " s/accountant manager";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }
    //@@author

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
