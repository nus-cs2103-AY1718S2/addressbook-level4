package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Lists all persons with positive balances in the address book to the user.
 */
public class ListPositiveBalanceCommand extends Command {

    public static final String COMMAND_WORD = "lend";
    public static final String COMMAND_SHORTCUT = "le";

    public static final String MESSAGE_SUCCESS = "Listed all persons who owe you money";

    @Override
    public CommandResult execute() {

        model.updateFilteredPersonList(isPositiveBalance());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public Predicate<Person> isPositiveBalance() {
        return a -> a.getMoney().balance >= 0;
    }
}
