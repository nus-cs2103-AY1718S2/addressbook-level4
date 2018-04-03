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
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Date;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.Price;

/**
 * Edits an existing policy of a person in the address book.
 */
public class EditPolicyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the policy of a person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_BEGINNING_DATE + "BEGINNING (DD/MM/YYYY)] "
            + "[" + PREFIX_EXPIRATION_DATE + "EXPIRATION (DD/MM/YYYY)] "
            + "[" + PREFIX_PRICE + "MONTHLY_PRICE] "
            + "[" + PREFIX_ISSUE + "ISSUE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPIRATION_DATE + "02/04/2020 "
            + PREFIX_ISSUE + "theft "
            + PREFIX_ISSUE + "car_damage";

    public static final String MESSAGE_POLICY_EDITED_SUCCESS = "Edited policy";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_PERSON_NOT_ENROLLED = "This person did not enroll in a policy yet "
            + "(use add_policy to make it happen)";

    private final Index index;
    private final EditPolicyDescriptor editPolicyDescriptor;

    private Person personToEnroll;
    private Person editedPerson;

    /**
     * @param index  of the person in the filtered person list to edit
     * @param editPolicyDescriptor details to edit the policy with
     */
    public EditPolicyCommand(Index index, EditPolicyDescriptor editPolicyDescriptor) {
        requireNonNull(index);
        requireNonNull(editPolicyDescriptor);

        this.index = index;
        this.editPolicyDescriptor = new EditPolicyDescriptor(editPolicyDescriptor);
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
        return new CommandResult(String.format(MESSAGE_POLICY_EDITED_SUCCESS));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEnroll = lastShownList.get(index.getZeroBased());
        if(!personToEnroll.getPolicy().isPresent()) {
            throw new CommandException(MESSAGE_PERSON_NOT_ENROLLED);
        }

        Policy policyToEdit = personToEnroll.getPolicy().get();
        Policy editedPolicy = createEditedPolicy(policyToEdit, editPolicyDescriptor);
        editedPerson = createPersonWithPolicy(personToEnroll, editedPolicy);
    }

    /**
     * Creates and returns a {@code Policy} with the details of {@code policyToEdit}
     * edited with {@code editPolicyDescriptor}.
     */
    private static Policy createEditedPolicy(Policy policyToEdit, EditPolicyDescriptor editPolicyDescriptor) {
        assert policyToEdit != null;

        Date updatedBeginning = editPolicyDescriptor.getBeginning().orElse(policyToEdit.getBeginning());
        Date updatedExpiration = editPolicyDescriptor.getExpiration().orElse(policyToEdit.getExpiration());
        Price updatedPrice = editPolicyDescriptor.getPrice().orElse(policyToEdit.getPrice());
        Coverage updatedCoverage = editPolicyDescriptor.getCoverage().orElse(policyToEdit.getCoverage());

        return new Policy(updatedPrice, updatedCoverage, updatedBeginning, updatedExpiration);
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

        if (!(other instanceof EditPolicyCommand)) {
            return false;
        }

        EditPolicyCommand e = (EditPolicyCommand) other;
        return index.equals(e.index)
                && editPolicyDescriptor.equals(e.editPolicyDescriptor)
                && Objects.equals(personToEnroll, e.personToEnroll);
    }

    /**
     * Stores the details to edit the policy with. Each non-empty field value will replace the
     * corresponding field value of the policy.
     */
    public static class EditPolicyDescriptor {
        private Date beginning;
        private Date expiration;
        private Price price;
        private Coverage coverage;

        public EditPolicyDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPolicyDescriptor(EditPolicyDescriptor toCopy) {
            setBeginning(toCopy.beginning);
            setExpiration(toCopy.expiration);
            setPrice(toCopy.price);
            setCoverage(toCopy.coverage);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.beginning, this.expiration, this.price, this.coverage);
        }

        public void setBeginning(Date beginning) {
            this.beginning = beginning;
        }

        public Optional<Date> getBeginning() {
            return Optional.ofNullable(beginning);
        }

        public void setExpiration(Date expiration) { this.expiration = expiration; }

        public Optional<Date> getExpiration() { return Optional.ofNullable(expiration); }

        public void setPrice(Price price) { this.price = price; }

        public Optional<Price> getPrice() { return Optional.ofNullable(price); }

        public void setCoverage(Coverage coverage) { this.coverage = coverage; }

        public Optional<Coverage> getCoverage() { return Optional.ofNullable(coverage); }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPolicyDescriptor)) {
                return false;
            }

            EditPolicyDescriptor e = (EditPolicyDescriptor) other;

            return getBeginning().equals(e.getBeginning())
                    && getExpiration().equals(e.getExpiration())
                    && getPrice().equals(e.getPrice())
                    && getCoverage().equals(e.getCoverage());
        }
    }
}
