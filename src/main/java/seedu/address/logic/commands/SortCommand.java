package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all persons in the address book "
            + "sorted in alphabetical order with index numbers as a list.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book sorted successfully!";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sortAllPersons();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> personList = model.getAddressBook().getPersonList();
        if (personList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_ADDRESS_BOOK_EMPTY);
        }
    }
}
