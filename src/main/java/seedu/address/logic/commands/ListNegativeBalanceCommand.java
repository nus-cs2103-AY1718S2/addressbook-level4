package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Lists all persons with negative balances in the address book to the user.
 */
public class ListNegativeBalanceCommand extends Command {

    public static final String COMMAND_WORD = "debt";
    public static final String COMMAND_SHORTCUT = "de";

    public static final String MESSAGE_SUCCESS = "Listed all persons who you owe money";

    @Override
    public CommandResult execute() {

        model.updateFilteredPersonList(isNegativeBalance());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    public Predicate<Person> isNegativeBalance() {
        return p -> p.getMoney().balance < 0;
    }
}
