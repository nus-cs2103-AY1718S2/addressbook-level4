//@@author chenchongsong
package seedu.address.logic.commands;

import static java.lang.Math.round;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Split a bill evenly among selected people.
 */
public class SplitCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "split";
    public static final String COMMAND_SHORTCUT = "sp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Split a bill evenly among specified people. "
            + "Parameters: INDEX1 [INDEX2...] "
            + PREFIX_MONEY + "MONEY\n"
            + "Example1: " + COMMAND_WORD + " 1 2 "
            + PREFIX_MONEY + "200\n"
            + "Example2: " + COMMAND_SHORTCUT + " 1 2 3 "
            + PREFIX_MONEY + "400.00";

    public static final String MESSAGE_SPLIT_BILL_SUCCESS = "Bill Split Successfully Among Selected People!";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate Indices Found in Parameters!";

    private final ArrayList<Index> indices;
    private ArrayList<Person> peopleToEdit;
    private ArrayList<Person> editedPeople;
    private final double bill;

    /**
     * @param indices of people in the filtered person list to settle the bill
     */
    public SplitCommand(ArrayList<Index> indices, double bill) {
        requireNonNull(indices);
        this.indices = indices;
        peopleToEdit = new ArrayList<>();
        editedPeople = new ArrayList<>();
        this.bill = bill;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            for (int i = 0; i < indices.size(); i++) {
                model.updatePerson(peopleToEdit.get(i), editedPeople.get(i));
            }
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target people cannot be duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target people cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SPLIT_BILL_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {

        if (!CollectionUtil.elementsAreUnique(indices.stream()
                .map(Index->Index.getZeroBased())
                .collect(Collectors.toList()))) {
            throw new CommandException(MESSAGE_DUPLICATE_INDEX);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person person = lastShownList.get(index.getZeroBased());
            peopleToEdit.add(person);
            editedPeople.add(getSettledPerson(person));
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * but with a updated balance
     */
    private Person getSettledPerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Money money = getSettledMoney(personToEdit.getMoney());
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, address, money, tags);
    }

    /**
     * Create and returns an updated Money Info
     * The updated Money would be rounded to 2 decimal places
     */
    private Money getSettledMoney(Money moneyToEdit) {
        assert moneyToEdit != null;
        double updatedBalance = moneyToEdit.toDouble() + bill / indices.size();
        updatedBalance = round(updatedBalance * 100.00) / 100.00;
        return new Money(Double.toString(updatedBalance));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SplitCommand)) {
            return false;
        }

        // state check
        SplitCommand o = (SplitCommand) other;

        return bill == o.bill
                && indices.equals(o.indices);
    }

}
