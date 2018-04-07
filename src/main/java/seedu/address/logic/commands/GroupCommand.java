package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonContainsGroupsPredicate;

/**
 * Clears the address book.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String COMMAND_WORD_ALIAS = "g";
    public static final String MESSAGE_SUCCESS = "Groups listed!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Group all persons whose group attribute is"
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Priority";

    private final PersonContainsGroupsPredicate predicate;

    public GroupCommand(PersonContainsGroupsPredicate predicate) {
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
                || (other instanceof GroupCommand // instanceof handles nulls
                && this.predicate.equals(((GroupCommand) other).predicate)); // state check
    }
}
