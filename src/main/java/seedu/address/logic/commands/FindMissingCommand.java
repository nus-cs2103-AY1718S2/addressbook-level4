package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

//@@author aussiroth
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindMissingCommand extends Command {

    public static final String COMMAND_WORD = "findmissing";
    public static final String COMMAND_WORD_ALIAS = "fm";
    public static final String[] ATTRIBUTE_VALUES =
            new String[] {"phone", "email", "address", "price", "level", "role", "status", "subject"};
    public static final HashSet<String> SET_ATTRIBUTE_VALUES = new HashSet<>(Arrays.asList(ATTRIBUTE_VALUES));

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filter all persons whose fields have unentered values.\n"
            + "With parameters, only those with specified fields with unentered values will be shown."
            + "With no parameters, all persons with at least one field with unentered values will be shown"
            + "Parameters: [ATTRIBUTE_NAME]\n"
            + "Example: " + COMMAND_WORD + " email phone";

    public static final String MESSAGE_INVALID_ATTRIBUTE = "The attribute %s is invalid.\n"
            + "The valid attributes are: phone, email, address, price, level, role, status, subject.";

    private final Predicate<Person> predicate;

    public FindMissingCommand(Predicate<Person> predicate) {
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
                || (other instanceof FindMissingCommand // instanceof handles nulls
                && this.predicate.equals(((FindMissingCommand) other).predicate)); // state check
    }
}
