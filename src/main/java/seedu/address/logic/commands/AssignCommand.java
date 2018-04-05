package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.customer.LateInterest;
import seedu.address.model.person.customer.MoneyBorrowed;
import seedu.address.model.person.customer.StandardInterest;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.runner.Runner;
import seedu.address.model.tag.Tag;

//@@author melvintzw

/**
 * Adds customers to a runner's customer list , list must contain unique elements
 */
public class AssignCommand extends UndoableCommand implements PopulatableCommand {

    public static final String COMMAND_WORD = "assign";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": assigns customers to a runner "
            + "by the index number used in the last person listing.\n"
            + "Parameters: RUNNER-INDEX (positive integer) "
            + PREFIX_CUSTOMERS + " CUSTOMER INDEX (positive integer) "
            + "[ CUSTOMER 2 INDEX...]\n"
            + "Example: " + COMMAND_WORD + " 5 " + PREFIX_CUSTOMERS + " 2 ";

    public static final String MESSAGE_ASSIGN_PERSON_SUCCESS = "Successfully assigned!\nUpdated Runner Info:\n%1$s";
    // message

    private final Index runnerIndex;
    private final Index[] customerIndex;

    private EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param runnerIndex of the Runner in the filtered person list to edit
     * @param customerIndex ... of the customers to add to Runner's customer list
     */
    public AssignCommand(Index runnerIndex, Index... customerIndex) {
        requireNonNull(runnerIndex);
        requireNonNull(customerIndex);

        this.runnerIndex = runnerIndex;
        this.customerIndex = customerIndex;
    }

    /**
     * For call in PopulatePrefixRequestEvent class, to assign string values.
     */
    public AssignCommand() {
        runnerIndex = null;
        customerIndex = null;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
            //TODO: model currently updates runners but does not update relevant customers with new change
            //notable case when updating customer: if customer already has an assigned runner --> override old runner w
            //new?
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (runnerIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(runnerIndex.getZeroBased());

        if (!(personToEdit instanceof Runner)) {
            throw new CommandException(String.format("Person at index %d is not a Runner", runnerIndex.getOneBased()));
        }

        makeEditRunnerDescriptorFromCustIndices(); //modifies editPersonDescriptor

        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns an {@code EditPersonDescriptor} with new customers from customerIndex...
     * the created EditPersonDescriptor is to be used to create editedPerson.
     */
    private void makeEditRunnerDescriptorFromCustIndices() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person runnerToBeEdited = lastShownList.get(runnerIndex.getZeroBased());
        assert (runnerToBeEdited instanceof Runner);

        editPersonDescriptor.setName(runnerToBeEdited.getName());
        editPersonDescriptor.setPhone(runnerToBeEdited.getPhone());
        editPersonDescriptor.setEmail(runnerToBeEdited.getEmail());
        editPersonDescriptor.setAddress(runnerToBeEdited.getAddress());
        editPersonDescriptor.setTags(runnerToBeEdited.getTags());

        //the following list contains all UNIQUE customers that should be in Runner's customer list after AssignCommand
        //command is executed
        List<Person> updatedCustomers = new ArrayList<>();
        updatedCustomers.addAll(((Runner) runnerToBeEdited).getCustomers());

        List<Person> customersToBeAdded = new ArrayList<>();
        for (Index index: customerIndex) {
            Person p = lastShownList.get(index.getZeroBased());
            if (!(p instanceof Customer)) {
                throw new CommandException("invalid customer index");
            }
            if (updatedCustomers.indexOf(p) >= 0) {
                throw new CommandException(String.format("customer at index %d, already assigned to runner",
                        index.getOneBased()));
            }
            customersToBeAdded.add((Customer) p);
        }
        updatedCustomers.addAll(customersToBeAdded); //add new unique customers to current list of customers
        editPersonDescriptor.setCustomers(updatedCustomers);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     * This method is borrowed from EditCommand
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) throws
            CommandException {

        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        if (personToEdit instanceof Customer) {

            MoneyBorrowed moneyBorrowed = editPersonDescriptor.getMoneyBorrowed().orElse(((Customer) personToEdit)
                    .getMoneyBorrowed());
            Date oweStartDate = editPersonDescriptor.getOweStartDate().orElse(((Customer) personToEdit)
                    .getOweStartDate());
            Date oweDueDate = editPersonDescriptor.getOweDueDate().orElse(((Customer) personToEdit)
                    .getOweDueDate());
            StandardInterest standardInterest = editPersonDescriptor.getStandardInterest()
                    .orElse(((Customer) personToEdit).getStandardInterest());
            LateInterest lateInterest = editPersonDescriptor.getLateInterest().orElse(((Customer) personToEdit)
                    .getLateInterest());
            Runner runner = editPersonDescriptor.getRunner().orElse(((Customer) personToEdit)
                    .getRunner());

            return new Customer(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, moneyBorrowed,
                    oweStartDate, oweDueDate, standardInterest, lateInterest, runner);

        } else if (personToEdit instanceof Runner) {

            List<Person> customers = editPersonDescriptor.getCustomers().orElse(((Runner) personToEdit)
                    .getCustomers());

            return new Runner(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, customers);

        } else {

            throw new CommandException("Error: Invalid Person");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        // state check
        AssignCommand e = (AssignCommand) other;
        return runnerIndex.equals(e.runnerIndex)
                && customerIndex.equals(e.customerIndex)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + "  " + PREFIX_NAME + "  " + PREFIX_PHONE + "  "
                + PREFIX_EMAIL + "  " + PREFIX_ADDRESS + "  " + PREFIX_TAG + " ";
    }

    @Override
    public int getCaretIndex() {
        return (COMMAND_WORD + " ").length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }


    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        //Customer fields
        private MoneyBorrowed moneyBorrowed;
        private Date oweStartDate;
        private Date oweDueDate;
        private StandardInterest standardInterest;
        private LateInterest lateInterest;
        private Runner runner;

        //Runner fields
        private List<Person> customers;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);

            setMoneyBorrowed(toCopy.moneyBorrowed);
            setOweStartDate(toCopy.oweStartDate);
            setOweDueDate(toCopy.oweDueDate);
            setStandardInterest(toCopy.standardInterest);
            setLateInterest(toCopy.lateInterest);
            setRunner(toCopy.runner);

            setCustomers(toCopy.customers);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.tags,
                    this.moneyBorrowed, this.oweStartDate, this.oweDueDate, this.standardInterest, this.lateInterest,
                    this.runner);
        }

        public void setName(Name name) {
            this.name = name;
        }
        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }
        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }
        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }
        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setMoneyBorrowed(MoneyBorrowed moneyBorrowed) {
            this.moneyBorrowed = moneyBorrowed;
        }
        public Optional<MoneyBorrowed> getMoneyBorrowed() {
            return Optional.ofNullable(moneyBorrowed);
        }

        public void setOweStartDate(Date oweStartDate) {
            this.oweStartDate = oweStartDate;
        }
        public Optional<Date> getOweStartDate() {
            return Optional.ofNullable(oweStartDate);
        }

        public void setOweDueDate(Date oweDueDate) {
            this.oweDueDate = oweDueDate;
        }
        public Optional<Date> getOweDueDate() {
            return Optional.ofNullable(oweDueDate);
        }

        public void setStandardInterest(StandardInterest standardInterest) {
            this.standardInterest = standardInterest;
        }
        public Optional<StandardInterest> getStandardInterest() {
            return Optional.ofNullable(standardInterest);
        }

        public void setLateInterest(LateInterest lateInterest) {
            this.lateInterest = lateInterest;
        }
        public Optional<LateInterest> getLateInterest() {
            return Optional.ofNullable(lateInterest);
        }

        public void setRunner(Runner runner) {
            this.runner = runner;
        }
        public Optional<Runner> getRunner() {
            return Optional.ofNullable(runner);
        }

        public void setCustomers(List<Person> customers) {
            this.customers = customers;
        }
        public Optional<List<Person>> getCustomers() {
            return Optional.ofNullable(customers);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
            //TODO: add .equals for Runner and Customer
        }
    }
}
