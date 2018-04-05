//@author ValerianRey

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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

/**
 * Removes a policy from a person identified using it's last displayed index from the address book.
 */
public class RemovePolicyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the policy of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_POLICY_REMOVED_SUCCESS = "Removed Policy";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private Person target;
    private Person editedPerson;

    public RemovePolicyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(target);

        try {
            model.updatePerson(target, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_POLICY_REMOVED_SUCCESS));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        target = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = personWithoutPolicy(target);
    }

    /**
     * Creates and returns a {@code Person} with the policy removed
     */
    private static Person personWithoutPolicy(Person personToEdit) {
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
                Optional.empty());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemovePolicyCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemovePolicyCommand) other).targetIndex) // state check
                && Objects.equals(this.target, ((RemovePolicyCommand) other).target));
    }
}
