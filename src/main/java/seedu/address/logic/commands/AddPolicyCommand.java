package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGINNING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.policy.Policy;

/**
 * Add a policy to an existing person in the address book.
 */
public class AddPolicyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BEGINNING_DATE + "BEGINNING (DD/MM/YYYY) "
            + PREFIX_EXPIRATION_DATE + "EXPIRATION (DD/MM/YYYY) "
            + PREFIX_PRICE + "MONTHLY_PRICE "
            + "[" + PREFIX_ISSUE + "ISSUE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BEGINNING_DATE + "03/04/2018 "
            + PREFIX_EXPIRATION_DATE + "02/04/2020 "
            + PREFIX_PRICE + "150 "
            + PREFIX_ISSUE + "theft "
            + PREFIX_ISSUE + "car_damage";

    public static final String MESSAGE_POLICY_ADDED_SUCCESS = "Added policy";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Policy policy;

    private Person personToEnroll;
    private Person editedPerson;

    /**
     * @param index  of the person in the filtered person list to edit
     * @param policy policy to add to the person
     */
    public AddPolicyCommand(Index index, Policy policy) {
        requireNonNull(index);
        requireNonNull(policy);

        this.index = index;
        this.policy = policy;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEnroll, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_POLICY_ADDED_SUCCESS));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEnroll = lastShownList.get(index.getZeroBased());
        editedPerson = createPersonWithPolicy(personToEnroll, policy);
    }

    /**
     * Creates and returns a {@code Person} with the specified {@code policyToAdd}
     */
    private static Person createPersonWithPolicy(Person personToEdit, Policy policyToAdd) {
        assert personToEdit != null;

        return new Person(personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getIncome(),
                personToEdit.getActualSpending(),
                personToEdit.getExpectedSpending(),
                personToEdit.getAge(),
                Optional.of(policyToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddPolicyCommand)) {
            return false;
        }

        AddPolicyCommand e = (AddPolicyCommand) other;
        return index.equals(e.index)
                && policy.equals(e.policy)
                && Objects.equals(personToEnroll, e.personToEnroll);
    }
}
