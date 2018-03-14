package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.google.common.collect.Ordering;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Displays the current list of persons in the address book to the user sorted alphabetically.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the current list of persons in the "
            + "address book sorted in alphabetical order with index numbers.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book sorted successfully!";
    public static final String MESSAGE_FAILURE = "Address book has already been sorted.";

    private List<Person> personList;

    /**
     * Returns true if person list is sorted.
     */
    private boolean isListSorted() {
        return Ordering.from(Person.nameComparator()).isOrdered(personList);
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sortAllPersons();
        model.getFilteredPersonList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        personList = model.getAddressBook().getPersonList();
        if (personList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_ADDRESS_BOOK_EMPTY);
        }
        if (isListSorted()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
