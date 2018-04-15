# melvintzw
###### \java\seedu\address\commons\events\ui\FieldsChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a selection change in the Person List Panel
 */
public class FieldsChangedEvent extends BaseEvent {


    public final Person person;

    public FieldsChangedEvent(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getPerson() {
        return person;
    }
}
```
###### \java\seedu\address\logic\commands\AssignCommand.java
``` java

/**
 * Adds customers to a runner's customer list , list must contain unique elements
 */
public class AssignCommand extends UndoableCommand implements PopulatableCommand {

    public static final String COMMAND_WORD = "assign";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + " | assigns customers to a runner associated with the index number used in the last "
                    + "person listing."
                    + "\n\t"
                    + "Refer to the User Guide (press \"F1\") for detailed information about this command!"

                    + "\n\t"
                    + "Parameters:\t"
                    + COMMAND_WORD + " "
                    + "RUNNER-INDEX (positive integer) "
                    + PREFIX_CUSTOMERS + " CUSTOMER-INDEX (positive integer) "
                    + "[ CUSTOMER-INDEX] ..."

                    + "\n\t"
                    + "Example:\t\t"
                    + COMMAND_WORD + " 1 " + PREFIX_CUSTOMERS + " 2"

                    + "\n\t"
                    + "Example:\t\t"
                    + COMMAND_WORD + " 1 " + PREFIX_CUSTOMERS + " 2 5 8";

    public static final String MESSAGE_ASSIGN_PERSON_SUCCESS = "Successfully assigned!\nUpdated Runner Info:\n%1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The target person cannot be missing";
    public static final String MESSAGE_INVALID_CUSTOMER_INDEX = "invalid customer index";
    public static final String MESSAGE_NOT_A_RUNNER = "Person at index %d is not a Runner";
    // message

    private final Index runnerIndex;
    private final Index[] customerIndex;

    private List<Person> oldCustomers = new ArrayList<>(); //customers already in runner's list of customers
    private List<Person> newCustomers = new ArrayList<>(); //customers to be added to runner's list of customers
    private List<Person> updatedCustomers = new ArrayList<>(); //new customers that have been been updated with runner
    private List<EditPersonDescriptor> listOfEditedCustDesc = new ArrayList<>();

    private Person personToEdit;
    private Person editedPerson;
    private EditPersonDescriptor editRunnerDescriptor = new EditPersonDescriptor();

    /**
     * @param runnerIndex   of the Runner in the filtered person list to edit
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
            deletePrevRunnerCustomer();
            model.updatePerson(personToEdit, editedPerson);
            int i = 0;
            for (Person c : newCustomers) {
                model.updatePerson(c, updatedCustomers.get(i));
                i++;
            }
            EventsCenter.getInstance().post(new JumpToListRequestEvent(runnerIndex));
            EventsCenter.getInstance().post(new FieldsChangedEvent(editedPerson));

        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_PERSON_NOT_FOUND);
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
            throw new CommandException(String.format(MESSAGE_NOT_A_RUNNER, runnerIndex.getOneBased()));
        }
        //NOTE: it is important to call these methods in this order so that the appropriate resources are generated
        generateNewCustomerList();
        generateCustDescWithAssignedRunner();
        generateUpdatedCustomerList();
        makeEditRunnerDescriptorFromUpdatedCustList(); //modifies editRunnerDescriptor
        editedPerson = createEditedPerson(personToEdit, editRunnerDescriptor);
    }

    /**
     * Since each Customer should only have 1 Runner, if the customer had a runner previously assigned, then that
     * previous runner should have its association with this customer removed. The customer will now only be associated
     * with the newly assigned runner.
     */
    private void deletePrevRunnerCustomer() throws CommandException, PersonNotFoundException,
            DuplicatePersonException {
        List<Person> pl = model.getAddressBook().getPersonList();
        //List<Person> allCustomers = new ArrayList<>();
        //allCustomers.addAll(oldCustomers);
        //allCustomers.addAll(newCustomers);
        for (Person c : newCustomers) {
            Person r = ((Customer) c).getRunner(); //not getting a runner from pl but an incomplete copy
            int indexOfActualPerson = pl.indexOf(r);

            if (indexOfActualPerson >= 0) {
                //the conditional check is necessary so that I'm only modifying valid existing runners

                Person actualRunner = pl.get(indexOfActualPerson); //getting the actual complete runner from pl

                //generate editPersonDescriptor with c removed from runner's customer list
                EditPersonDescriptor runnerDescWCustRemoved = new EditPersonDescriptor();

                runnerDescWCustRemoved.setName(actualRunner.getName());
                runnerDescWCustRemoved.setPhone(actualRunner.getPhone());
                runnerDescWCustRemoved.setEmail(actualRunner.getEmail());
                runnerDescWCustRemoved.setAddress(actualRunner.getAddress());
                runnerDescWCustRemoved.setTags(actualRunner.getTags());

                List<Person> newList = ((Runner) actualRunner).getCustomers();
                newList.remove(c);
                runnerDescWCustRemoved.setCustomers(newList);

                Person editedPrevRunner = createEditedPerson((Runner) actualRunner, runnerDescWCustRemoved);
                model.updatePerson(actualRunner, editedPrevRunner);
            }
        }
    }

    /**
     * Edit each new customer with the runner to be assigned.
     * <p>
     * Requires an accompanying list of customer descriptors describing these new customers and reflecting the assigned
     * runner.
     *
     * @throws CommandException
     */
    private void generateUpdatedCustomerList() throws CommandException {
        int i = 0;
        for (Person c : newCustomers) {
            updatedCustomers.add(createEditedPerson(c, listOfEditedCustDesc.get(i)));
            i++;
        }
    }

    /**
     * Creates and returns an {@code EditPersonDescriptor} with new customers from customerIndex...
     * the created EditPersonDescriptor is to be used to create editedPerson.
     */
    private void makeEditRunnerDescriptorFromUpdatedCustList() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person runnerToBeEdited = lastShownList.get(runnerIndex.getZeroBased());
        assert (runnerToBeEdited instanceof Runner);

        editRunnerDescriptor.setName(runnerToBeEdited.getName());
        editRunnerDescriptor.setPhone(runnerToBeEdited.getPhone());
        editRunnerDescriptor.setEmail(runnerToBeEdited.getEmail());
        editRunnerDescriptor.setAddress(runnerToBeEdited.getAddress());
        editRunnerDescriptor.setTags(runnerToBeEdited.getTags());

        List<Person> allCustomers = new ArrayList<>();
        allCustomers.addAll(oldCustomers);
        allCustomers.addAll(updatedCustomers);
        editRunnerDescriptor.setCustomers(allCustomers);
    }

    /**
     * generates a list of new and unique customers to be assigned to the runner.
     *
     * @throws CommandException
     */
    private void generateNewCustomerList() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person runnerToBeEdited = lastShownList.get(runnerIndex.getZeroBased());
        oldCustomers.addAll(((Runner) runnerToBeEdited).getCustomers());

        for (Index index : customerIndex) {
            Person p = lastShownList.get(index.getZeroBased());
            if (!(p instanceof Customer)) {
                throw new CommandException(MESSAGE_INVALID_CUSTOMER_INDEX);
            }
            if (oldCustomers.indexOf(p) >= 0) {
                throw new CommandException(String.format("customer at %d already assigned to runner",
                        index.getOneBased()));
            }
            if (newCustomers.indexOf(p) >= 0) {
                throw new CommandException("cannot assign same customer twice");
            }
            newCustomers.add((Customer) p);
        }
    }

    /**
     * Generates a list of EditPersonDescriptors for the purpose of updating each customer with the assigned runner
     * This helper method is meant to be called in executeUndoableCommand().
     * references to each other.
     */
    private void generateCustDescWithAssignedRunner() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person runnerToBeEdited = lastShownList.get(runnerIndex.getZeroBased());
        assert (runnerToBeEdited instanceof Runner);
        for (Person c : newCustomers) {
            EditPersonDescriptor custDesc = new EditPersonDescriptor();

            custDesc.setRunner((Runner) runnerToBeEdited);

            custDesc.setName(c.getName());
            custDesc.setPhone(c.getPhone());
            custDesc.setEmail(c.getEmail());
            custDesc.setAddress(c.getAddress());
            custDesc.setTags(c.getTags());

            custDesc.setMoneyBorrowed(((Customer) c).getMoneyBorrowed());
            custDesc.setOweStartDate(((Customer) c).getOweStartDate());
            custDesc.setOweDueDate(((Customer) c).getOweDueDate());
            custDesc.setStandardInterest(((Customer) c).getStandardInterest());
            custDesc.setLateInterest(((Customer) c).getLateInterest());

            listOfEditedCustDesc.add(custDesc);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editRunnerDescriptor}.
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
            Person runner = editPersonDescriptor.getRunner().orElse(((Customer) personToEdit)
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssignCommand)) {
            return false;
        }
        AssignCommand that = (AssignCommand) o;
        return Objects.equals(runnerIndex, that.runnerIndex)
                && Arrays.equals(customerIndex, that.customerIndex)
                && Objects.equals(oldCustomers, that.oldCustomers)
                && Objects.equals(newCustomers, that.newCustomers)
                && Objects.equals(updatedCustomers, that.updatedCustomers)
                && Objects.equals(listOfEditedCustDesc, that.listOfEditedCustDesc)
                && Objects.equals(personToEdit, that.personToEdit)
                && Objects.equals(editedPerson, that.editedPerson)
                && Objects.equals(editRunnerDescriptor, that.editRunnerDescriptor);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(runnerIndex, oldCustomers, newCustomers, updatedCustomers, listOfEditedCustDesc,
                personToEdit, editedPerson, editRunnerDescriptor);
        result = 31 * result + Arrays.hashCode(customerIndex);
        return result;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + "  " + PREFIX_CUSTOMERS + " ";
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
        private Person runner;

        //Runner fields
        private List<Person> customers;

        public EditPersonDescriptor() {
        }

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

        public void setRunner(Person runner) {
            this.runner = runner;
        }

        public Optional<Person> getRunner() {
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
                    && getTags().equals(e.getTags())
                    && getMoneyBorrowed().equals(e.getMoneyBorrowed())
                    && getOweDueDate().equals(e.getOweDueDate())
                    && getOweStartDate().equals(e.getOweStartDate())
                    && getStandardInterest().equals(e.getStandardInterest())
                    && getLateInterest().equals(e.getLateInterest())
                    && getRunner().equals(e.getRunner())
                    && getCustomers().equals(e.getCustomers());

        }
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
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
            Person runner = editPersonDescriptor.getRunner().orElse(((Customer) personToEdit)
                    .getRunner());

            if (oweDueDate.compareTo(oweStartDate) < 0) {
                throw new CommandException("OWE_DUE_DATE cannot be before OWE_START_DATE");
            }

            return new Customer(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, moneyBorrowed,
                    oweStartDate, oweDueDate, standardInterest, lateInterest, runner);

        } else if (personToEdit instanceof Runner) {

            if (editPersonDescriptor.getStandardInterest().isPresent()
                    || editPersonDescriptor.getMoneyBorrowed().isPresent()
                    || editPersonDescriptor.getOweStartDate().isPresent()
                    || editPersonDescriptor.getOweDueDate().isPresent()) {
                throw new CommandException("Cannot edit Runner using Customer-only fields");
            }

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
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command implements PopulatableCommand {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final String COMMAND_TEMPLATE = COMMAND_WORD + " -";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + " | Finds all persons whose fields contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers."
            + "\n\t"
            + "Refer to the User Guide (press \"F1\") for detailed information about this command!"
            + "\n\t"
            + "Parameters:\t"
            + COMMAND_WORD + " "
            + "[SPECIFIER] KEYWORD [KEYWORD] ..."
            + "\n\t"
            + "Specifiers:\t\t"
            + "-all, -n, -p, -e, -a, -t : ALL, NAME, PHONE, EMAIL, ADDRESS and TAGS respectively."
            + "\n\t"
            + "Example:\t\t" + COMMAND_WORD + " -n alice bob charlie";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    /**
     * For call in PopulatePrefixRequestEvent class, to assign string values.
     */
    public FindCommand() {
        predicate = null;
    }


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate));
        // state check
    }

```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_MONEY_BORROWED, PREFIX_OWESTARTDATE, PREFIX_OWEDUEDATE,
                        PREFIX_INTEREST);

        //TODO: add test case
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()
                || !argMultimap.getValue(PREFIX_TYPE).get().matches("[cCrR]")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).orElse(new Phone());
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).orElse(new Email());
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).orElse(new Address());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            if (argMultimap.getValue(PREFIX_TYPE).get().matches("[cC]")) {
                Date oweStartDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_OWESTARTDATE)).orElse(new Date(0));
                Date oweDueDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_OWEDUEDATE)).orElse(new Date(0));

                if (oweDueDate.compareTo(oweStartDate) < 0) {
                    throw new ParseException("OWE_DUE_DATE cannot be before OWE_START_DATE");
                }

                MoneyBorrowed moneyBorrowed = ParserUtil.parseMoneyBorrowed(argMultimap.getValue(PREFIX_MONEY_BORROWED))
                        .orElse(new MoneyBorrowed());

                StandardInterest standardInterest = ParserUtil.parseStandardInterest(argMultimap
                        .getValue(PREFIX_INTEREST)).orElse(new StandardInterest());

                Customer customer = new Customer(name, phone, email, address, tagList, moneyBorrowed,
                        oweStartDate, oweDueDate, standardInterest, new LateInterest(), new Runner());

                return new AddCommand(customer);

            } else if (argMultimap.getValue(PREFIX_TYPE).get().matches("[rR]")) {
                if (argMultimap.getValue(PREFIX_MONEY_BORROWED).isPresent()
                        || argMultimap.getValue(PREFIX_OWEDUEDATE).isPresent()
                        || argMultimap.getValue(PREFIX_OWESTARTDATE).isPresent()
                        || argMultimap.getValue(PREFIX_INTEREST).isPresent()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddCommand.MESSAGE_INVALID_PREFIX));
                }
                Runner runner = new Runner(name, phone, email, address, tagList, new ArrayList<>());
                return new AddCommand(runner);

            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
```
###### \java\seedu\address\logic\parser\AssignCommandParser.java
``` java

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CUSTOMERS);

        Index runnerIndex; //parameter for AssignCommand
        Index[] customerIndexArray; //parameter for AssignCommand

        try {
            runnerIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        try {
            String customers = argMultimap.getValue(PREFIX_CUSTOMERS).get();
            List<Index> customerIndexList = parseCustIndex(customers);
            customerIndexArray = customerIndexList.toArray(new Index[customerIndexList.size()]);

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        return new AssignCommand(runnerIndex, customerIndexArray);
    }

    /**
     * Parses a string of customer numbers (representing indices) into a list of Index objects
     *
     * @param customers a string of numbers presenting indices
     */
    private static List<Index> parseCustIndex(String customers) throws IllegalValueException, NumberFormatException {
        String[] splitIndices = customers.split("\\s");
        List<Index> indexList = new ArrayList<>();
        for (String s : splitIndices) {
            int index = Integer.parseInt(s);
            indexList.add(fromOneBased(index));
        }
        if (indexList.size() < 1) {
            throw new IllegalValueException("no customer index has been specified");
        }
        return indexList;
    }

}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_MONEY_BORROWED, PREFIX_INTEREST, PREFIX_OWEDUEDATE, PREFIX_OWESTARTDATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            if (argMultimap.getValue(PREFIX_OWESTARTDATE).isPresent()) {
                Date oweStartDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_OWESTARTDATE).get());
                editPersonDescriptor.setOweStartDate(oweStartDate);
            }
            if (argMultimap.getValue(PREFIX_OWEDUEDATE).isPresent()) {
                Date oweDueDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_OWEDUEDATE).get());
                editPersonDescriptor.setOweDueDate(oweDueDate);
            }

            if (argMultimap.getValue(PREFIX_MONEY_BORROWED).isPresent()) {
                MoneyBorrowed moneyBorrowed = ParserUtil.parseMoneyBorrowed(argMultimap.getValue(PREFIX_MONEY_BORROWED)
                        .get());
                editPersonDescriptor.setMoneyBorrowed(moneyBorrowed);
            }

            if (argMultimap.getValue(PREFIX_INTEREST).isPresent()) {
                StandardInterest standardInterest = ParserUtil.parseStandardInterest(argMultimap.getValue
                        (PREFIX_INTEREST).get());
                editPersonDescriptor.setStandardInterest(standardInterest);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        String[] arguments = trimmedArgs.split("\\s+");
        String[] keywords;
        //check arguments[0] for specifier

        if (arguments[0].matches("\\p{Alnum}+.*+")) {
            return new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(arguments)));
        }

        switch (arguments[0]) {
        case "-all":
            keywords = Arrays.copyOfRange(arguments, 1, arguments.length);
            return new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "-n":
            keywords = Arrays.copyOfRange(arguments, 1, arguments.length);
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "-p":
            keywords = Arrays.copyOfRange(arguments, 1, arguments.length);
            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "-e":
            keywords = Arrays.copyOfRange(arguments, 1, arguments.length);
            return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "-a":
            keywords = Arrays.copyOfRange(arguments, 1, arguments.length);
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "-t":
            keywords = Arrays.copyOfRange(arguments, 1, arguments.length);
            return new FindCommand(new TagsContainsKeywordsPredicate(Arrays.asList(keywords)));
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) {
        requireNonNull(date);
        String trimmedDate = date.trim();
        com.joestelmach.natty.Parser dateParser = new Parser();
        List<DateGroup> dateGroups = dateParser.parse(trimmedDate);
        return dateGroups.get(0).getDates().get(0);
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Date> parseDate(Optional<String> date) {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    //TODO: add methods to parse Customer fields and Runner fields

    /**
     * Parses a {@code string double} into an {@code MoneyBorrowed}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code MoneyBorrowed} is invalid.
     */
    public static MoneyBorrowed parseMoneyBorrowed(String moneyBorrowed) throws IllegalValueException {
        requireNonNull(moneyBorrowed);
        try {
            return new MoneyBorrowed(Double.parseDouble(moneyBorrowed));
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(MoneyBorrowed.MESSAGE_MONEY_BORROWED_DOUBLE_ONLY);
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(MoneyBorrowed.MESSAGE_MONEY_BORROWED_NO_NEGATIVE);
        }
    }

    /**
     * Parses a {@code Optional<String> moneyBorrowed} into an {@code Optional<MoneyBorrowed>} if {@code moneyBorrowed}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MoneyBorrowed> parseMoneyBorrowed(Optional<String> moneyBorrowed) throws
            IllegalValueException {
        requireNonNull(moneyBorrowed);
        return moneyBorrowed.isPresent() ? Optional.of(parseMoneyBorrowed(moneyBorrowed.get())) : Optional.empty();
    }

    /**
     * Parses a {@code string double} into an {@code StandardInterest}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code StandardInterest} is invalid.
     */
    public static StandardInterest parseStandardInterest(String value) throws IllegalValueException {
        requireNonNull(value);

        value = value.trim();

        try {
            return new StandardInterest(Double.parseDouble(value));
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(StandardInterest.MESSAGE_STANDARD_INTEREST_DOUBLE_ONLY);
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(StandardInterest.MESSAGE_STANDARD_INTEREST_NO_NEGATIVE);
        }
    }

    /**
     * Parses a {@code Optional<String> standardInterest} into an {@code Optional<StandardInterest>} if {@code
     * value} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StandardInterest> parseStandardInterest(Optional<String> value) throws
            IllegalValueException {
        requireNonNull(value);
        return value.isPresent() ? Optional.of(parseStandardInterest(value.get())) : Optional.empty();
    }

    /**
     * Parses a {@code string double} into an {@code LateInterest}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code LateInterest} is invalid.
     */
    public static LateInterest parseLateInterest(String value) throws IllegalValueException {
        requireNonNull(value);

        value = value.trim();

        try {
            return new LateInterest(Double.parseDouble(value));
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(LateInterest.MESSAGE_LATE_INTEREST_DOUBLE_ONLY);
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(LateInterest.MESSAGE_LATE_INTEREST_NO_NEGATIVE);
        }
    }

    /**
     * Parses a {@code Optional<String> lateInterest} into an {@code Optional<LateInterest>} if {@code
     * value} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<LateInterest> parseLateInterest(Optional<String> value) throws IllegalValueException {
        requireNonNull(value);
        return value.isPresent() ? Optional.of(parseLateInterest(value.get())) : Optional.empty();
    }


}
```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    //test existence of keywords in person's address.
    public boolean test(Person person) {

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\customer\Customer.java
``` java
/**
 * Represents a customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer extends Person {

    private final MoneyBorrowed moneyBorrowed;
    private final Date oweStartDate;
    private final Date oweDueDate;
    private final StandardInterest standardInterest; //in percent
    private final LateInterest lateInterest; //in percent
    private final Person runner;

    /**
     * customer constructor
     */
    public Customer() {
        super();
        this.setType(PersonType.CUSTOMER);
        this.moneyBorrowed = new MoneyBorrowed();
        this.oweStartDate = new Date(0);
        this.oweDueDate = new Date(0);
        this.standardInterest = new StandardInterest();
        this.lateInterest = new LateInterest();
        this.runner = new Runner();
    }

    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                    MoneyBorrowed moneyBorrowed, Date oweStartDate, Date oweDueDate, StandardInterest
                            standardInterest, LateInterest lateInterest, Person runner) {
        super(name, phone, email, address, tags);
        this.setType(PersonType.CUSTOMER);
        this.moneyBorrowed = moneyBorrowed;
        this.standardInterest = standardInterest;
        this.lateInterest = lateInterest;
        this.oweStartDate = oweStartDate;
        this.oweDueDate = oweDueDate;
        this.runner = runner;
    }

    public MoneyBorrowed getMoneyBorrowed() {
        return moneyBorrowed;
    }

    public StandardInterest getStandardInterest() {
        return standardInterest;
    }

    public Date getOweStartDate() {
        return oweStartDate;
    }

    public Date getOweDueDate() {
        return oweDueDate;
    }

    public LateInterest getLateInterest() {
        return lateInterest;
    }

    public Person getRunner() {
        return runner;
    }

    /**
     * @return amount of money owed, after compounded standardInterest, based on num of weeks that has passed since
     * oweStartDate
     */
    public double getMoneyCurrentlyOwed() {
        final int numOfMsPerWeek = 60 * 60 * 24 * 7 * 1000; //10080 seconds per week; 1000 ms per second

        Date currentDate = new Date();
        long elapsedTime = currentDate.getTime() - oweStartDate.getTime();
        if (elapsedTime < 0) {
            return moneyBorrowed.value;
        }
        long elapsedWeeks = elapsedTime / numOfMsPerWeek;
        return moneyBorrowed.value * Math.pow(1 + standardInterest.value / 100, (double) elapsedWeeks);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherPerson = (Customer) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName() + ";")
                .append(" Phone: ")
                .append(getPhone() + ";")
                .append(" Email: ")
                .append(getEmail() + ";")
                .append(" Address: ")
                .append(getAddress() + ";")
                .append(" Tags: ");
        getTags().forEach(builder::append);

        SimpleDateFormat simpledate = new SimpleDateFormat("EEE, d MMM yyyy");
        String oweStartDate = simpledate.format(getOweStartDate());
        String oweDueDate = simpledate.format(getOweDueDate());

        builder.append("\nMoney Owed: ")
                .append(String.format("$%.2f", getMoneyCurrentlyOwed()))
                .append(" Weekly Interest Rate: ")
                .append(getStandardInterest() + "%" + ";")
                .append(" Start Date: ")
                .append(oweStartDate + ";")
                .append(" Due Date: ")
                .append(oweDueDate)
                .append("\nRunner Assigned: ")
                .append(runner.getName());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\person\customer\LateInterest.java
``` java

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a customer's late interest rate.
 * Guarantees: immutable;
 */
public class LateInterest {

    /*
    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    */
    public static final String MESSAGE_LATE_INTEREST_DOUBLE_ONLY =
            "MONEY_BORROWED can only contain numbers";
    public static final String MESSAGE_LATE_INTEREST_NO_NEGATIVE =
            "MONEY_BORROWED cannot be negative";

    public final double value;

    public LateInterest() {
        value = 0;
    }

    /**
     * Constructs a {@code Phone}.
     *
     * @param value an amount borrowed form the loanshark
     */
    public LateInterest(double value) {
        checkArgument(isValidInterest(value), MESSAGE_LATE_INTEREST_NO_NEGATIVE);
        this.value = value;
    }

    /**
     * Returns true if a give value is zero or positive, returns false otherwise
     */
    public static boolean isValidInterest(double test) {
        return (!(test < 0));
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LateInterest // instanceof handles nulls
                && this.value == ((LateInterest) other).value); // state check
    }

    @Override
    public int hashCode() {
        return new Double(value).hashCode();
    }

}
```
###### \java\seedu\address\model\person\customer\MoneyBorrowed.java
``` java

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a customer's amount of money that he/she borrowed.
 * Guarantees: immutable;
 */
public class MoneyBorrowed {

    public static final String MESSAGE_MONEY_BORROWED_DOUBLE_ONLY =
            "MONEY_BORROWED can only contain numbers";
    public static final String MESSAGE_MONEY_BORROWED_NO_NEGATIVE =
            "MONEY_BORROWED cannot be negative";

    public final double value;

    public MoneyBorrowed() {
        value = 0;
    }

    /**
     * Constructs a {@code Phone}.
     *
     * @param value an amount borrowed form the loanshark
     */
    public MoneyBorrowed(double value) {
        checkArgument(isValidMoneyBorrowed(value), MESSAGE_MONEY_BORROWED_NO_NEGATIVE);
        this.value = value;
    }

    /**
     * Returns true if a given value is zero or positive, returns false otherwise
     */
    public static boolean isValidMoneyBorrowed(double test) {
        return (!(test < 0));
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoneyBorrowed // instanceof handles nulls
                && this.value == ((MoneyBorrowed) other).value); // state check
    }

    @Override
    public int hashCode() {
        return new Double(value).hashCode();
    }

}
```
###### \java\seedu\address\model\person\customer\StandardInterest.java
``` java

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a customer's standard interest rate.
 * Guarantees: immutable;
 */
public class StandardInterest {


    public static final String MESSAGE_STANDARD_INTEREST_DOUBLE_ONLY =
            "MONEY_BORROWED can only contain numbers";
    public static final String MESSAGE_STANDARD_INTEREST_NO_NEGATIVE =
            "MONEY_BORROWED cannot be negative";


    public final double value;

    public StandardInterest() {
        value = 0;
    }

    /**
     * Constructs a {@code Phone}.
     *
     * @param value an amount borrowed form the loanshark
     */
    public StandardInterest(double value) {
        checkArgument(isValidInterest(value), MESSAGE_STANDARD_INTEREST_NO_NEGATIVE);
        this.value = value;
    }

    /**
     * Returns true if a give value is zero or positive, returns false otherwise
     */
    public static boolean isValidInterest(double test) {
        return (!(test < 0));
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StandardInterest // instanceof handles nulls
                && this.value == ((StandardInterest) other).value); // state check
    }

    @Override
    public int hashCode() {
        return new Double(value).hashCode();
    }

}
```
###### \java\seedu\address\model\person\EmailContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    //test existence of keywords in person's full name and tags.
    public boolean test(Person person) {
        //TODO: write helper method here or in Email class to extract values before '@' symbol in email address.
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    //test existence of keywords in person's full name and tags.
    public boolean test(Person person) {

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    //test existence of keywords in person's full name, address and tags.
    public boolean test(Person person) {

        String stringOfTags = getStringOfTags(person);

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(stringOfTags, keyword))
                || keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    private String getStringOfTags(Person person) {
        String stringOfTags = "";

        for (Tag x : person.getTags()) {
            stringOfTags = stringOfTags + " " + x.tagName;
        }
        return stringOfTags.trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    //test existence of keywords in person's phone.
    public boolean test(Person person) {

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\runner\Runner.java
``` java
/**
 * Represents a runner in the address book.
 */
public class Runner extends Person {
    private final List<Person> customers;

    public Runner() {
        super();
        this.customers = new ArrayList<>();
        this.setType(PersonType.RUNNER);
    }

    public Runner(Name name, Phone phone, Email email, Address address, Set<Tag> tags, List<Person> customers) {
        super(name, phone, email, address, tags);
        this.setType(PersonType.RUNNER);
        this.customers = customers;
    }

    public List<Person> getCustomers() {
        return customers;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Runner)) {
            return false;
        }

        Runner otherPerson = (Runner) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress());

    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(getName() + ";")
                .append(" Phone: ")
                .append(getPhone() + ";")
                .append(" Email: ")
                .append(getEmail() + ";")
                .append(" Address: ")
                .append(getAddress() + ";")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append("\n");
        builder.append("Customers: ");
        if (customers.size() > 0) {
            builder.append(customers.get(0).getName());
        }
        if (customers.size() > 1) {
            for (int i = 1; i < customers.size(); i++) {
                builder.append(", ");
                builder.append(customers.get(i).getName());
            }
        }
        return builder.toString();

    }
}
```
###### \java\seedu\address\model\person\TagsContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    //test existence of keywords in person's full name and tags.
    public boolean test(Person person) {

        String stringOfTags = getStringOfTags(person);

        return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(stringOfTags, keyword));
    }

    private String getStringOfTags(Person person) {
        String stringOfTags = "";

        for (Tag x : person.getTags()) {
            stringOfTags = stringOfTags + " " + x.tagName;
        }
        return stringOfTags.trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
            new Runner(new Name("The Terminator"), new Phone("84444448"), new Email("protection@money.com"),
                    new Address("Fountain of Wealth"),
                    getTagSet("Arnold", "HealthIsWealth"), new ArrayList<>()),
            new Runner(new Name("Donny J"), new Phone("0013451945"), new Email("protection@money.com"),
                    new Address("Changi Prison Complex"),
                    getTagSet("Inactive", "Disavowed", "Joker"), new ArrayList<>()),
            new Customer(new Name("Zhong Ming"), new Phone("91121345"), new Email("important@ming.com"),
                    new Address("Merlion"),
                    getTagSet("ImportantMing", "ZhongMing", "MingGreatest", "mingdynasty", "HighSES"),
                    new MoneyBorrowed(98789060),
                    createDate(2014, 6, 7), createDate(2016, 11, 9),
                    new StandardInterest(1.75), new LateInterest(), new Runner()),
            new Runner(new Name("Wu Lui"), new Phone("90011009"), new Email("nigerian_prince@bankofchina.com"),
                    new Address("The LINQ Hotel & Casino"),
                    getTagSet("OnTheStrip", "HighRoller"), new ArrayList<>()),
            new Customer(new Name("Queen Samsung"), new Phone("000"), new Email("king@kim.com"),
                    new Address("Samsung Innovation Museum"),
                    getTagSet("Korean", "Royalty", "Untouchable", "HighSES"), new MoneyBorrowed(999999999),
                    createDate(2000, 1, 1), createDate(2112, 12, 12),
                    new StandardInterest(0.01), new LateInterest(), new Runner()),
            new Customer(new Name("Ma Qing Da Wen"), new Phone("764543543123"), new Email("important@ming.com"),
                    new Address("Town Green"),
                    getTagSet("ForeignContact", "Code49"), new MoneyBorrowed(1124),
                    createDate(2003, 4, 11), createDate(2028, 5, 29),
                    new StandardInterest(5.76), new LateInterest(), new Runner()),
            new Customer(new Name("Lim Tin Ken"), new Phone("81140976"), new Email("limtincan@u.nus.edu"),
                    new Address("Cinnamon College"),
                    getTagSet("USP", "Cinnamonster"), new MoneyBorrowed(0.1),
                    createDate(2018, 4, 1), createDate(2018, 11, 11),
                    new StandardInterest(1000), new LateInterest(), new Runner()),
            new Customer(new Name("Master Wu Gui"), new Phone("94523112"), new Email("turtle@dojo.net"),
                    new Address("The Singapore Island Country Club"),
                    getTagSet("MOJO", "HighSES"), new MoneyBorrowed(645644),
                    createDate(2012, 3, 17), createDate(2015, 7, 30),
                    new StandardInterest(0.9), new LateInterest(), new Runner()),
            new Customer(new Name("Hilarious Kleiny"), new Phone("91208888"), new Email("turtle@dojo.net"),
                    new Address("Institute of Mental Health"),
                    getTagSet("SiaoLiao", "Joker"), new MoneyBorrowed(12064543),
                    createDate(2010, 10, 10), createDate(2022, 9, 22),
                    new StandardInterest(2.309), new LateInterest(), new Runner()),
        };
    }

    /**
     * helper method to generate a custom meaningful date.
     *
     * @return
     */
    private static Date createDate(int year, int month, int dayOfMonth) {
        return createDate(year, month, dayOfMonth, 0, 0, 0);
    }

    /**
     * helper method to generate a custom meaningful date.
     *
     * @return
     */
    private static Date createDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
        return calendar.getTime();
    }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private Person.PersonType personType;

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    //Customer fields
    @XmlElement(required = true)
    private MoneyBorrowed moneyBorrowed;
    @XmlElement(required = true)
    private StandardInterest standardInterest;
    @XmlElement(required = true)
    private LateInterest lateInterest;
    @XmlElement(required = true)
    private Date oweStartDate;
    @XmlElement(required = true)
    private Date oweDueDate;
    @XmlElement(required = true)
    private XmlAdaptedPerson runner;

    //Runner fields
    @XmlElement(required = true)
    private List<XmlAdaptedPerson> customers = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.personType = Person.PersonType.PERSON;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        personType = source.getType();

        if (source instanceof Customer) {
            moneyBorrowed = ((Customer) source).getMoneyBorrowed();
            standardInterest = ((Customer) source).getStandardInterest();
            lateInterest = ((Customer) source).getLateInterest();
            oweStartDate = ((Customer) source).getOweStartDate();
            oweDueDate = ((Customer) source).getOweDueDate();
            runner = new XmlAdaptedPerson(((Customer) source).getRunner());
        }

        if (source instanceof Runner) {
            customers = new ArrayList<>();
            for (Person person : ((Runner) source).getCustomers()) {
                customers.add(new XmlAdaptedPerson(person));
            }
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(this.phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone phone = new Phone(this.phone);

        if (this.email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(this.email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email email = new Email(this.email);

        if (this.address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(this.address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address address = new Address(this.address);

        final Set<Tag> tags = new HashSet<>(personTags);

        if (this.personType == Person.PersonType.CUSTOMER) {
            //moneyBorrowed
            if (this.moneyBorrowed == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MoneyBorrowed.class
                        .getSimpleName()));
            }
            if (!MoneyBorrowed.isValidMoneyBorrowed(this.moneyBorrowed.value)) {
                throw new IllegalValueException(MoneyBorrowed.MESSAGE_MONEY_BORROWED_NO_NEGATIVE);
            }
            final MoneyBorrowed moneyBorrowed = new MoneyBorrowed(this.moneyBorrowed.value);

            //oweStartDate
            if (this.oweStartDate == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName
                        ()));
            }

            final Date oweStartDate = this.oweStartDate;

            //oweDueDate
            if (this.oweDueDate == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName
                        ()));
            }

            final Date oweDueDate = this.oweDueDate;

            //standardInterest
            if (this.standardInterest == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, StandardInterest.class
                        .getSimpleName()));
            }
            if (!standardInterest.isValidInterest(this.standardInterest.value)) {
                throw new IllegalValueException(standardInterest.MESSAGE_STANDARD_INTEREST_NO_NEGATIVE);
            }
            final StandardInterest standardInterest = this.standardInterest;

            //lateInterest
            if (this.lateInterest == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LateInterest.class
                        .getSimpleName()));
            }
            if (!standardInterest.isValidInterest(this.lateInterest.value)) {
                throw new IllegalValueException(standardInterest.MESSAGE_STANDARD_INTEREST_NO_NEGATIVE);
            }
            final LateInterest lateInterest = this.lateInterest;

            //runner
            if (this.runner == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LateInterest.class
                        .getSimpleName()));
            }
            final Person runner = this.runner.toModelType();

            return new Customer(name, phone, email, address, tags, moneyBorrowed, oweStartDate, oweDueDate,
                    standardInterest, lateInterest, runner);

        } else if (this.personType == Person.PersonType.RUNNER) {
            if (this.customers == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, StandardInterest.class
                        .getSimpleName()));
            }

            final List<Person> customerList = new ArrayList<>();
            for (XmlAdaptedPerson person : customers) {
                customerList.add(person.toModelType());
            }

            return new Runner(name, phone, email, address, tags, customerList);

        } else {
            return new Person(name, phone, email, address, tags);

        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleFieldsChangedEvent(FieldsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.person);
    }

}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                setStyle("    -fx-label-padding: 0 0 0 0;"
                        + "    -fx-graphic-text-gap : 0;"
                        + "    -fx-padding: 0 0 0 0;"
                        + "    -fx-background-color: derive(-main-colour, 0%);");
            } else {
                if (person.person instanceof Customer) {
                    setGraphic(person.getRoot());
                    setStyle("    -fx-label-padding: 0 0 0 0;"
                            + "    -fx-graphic-text-gap : 0;"
                            + "    -fx-padding: 0 0 0 0;"
                            + "    -fx-background-color: derive(-main-colour, 0%);");
                } else {
                    setGraphic(person.getRoot());
                    setStyle("    -fx-label-padding: 0 0 0 0;"
                            + "    -fx-graphic-text-gap : 0;"
                            + "    -fx-padding: 0 0 0 0;"
                            + "    -fx-background-color: derive(-main-colour, 50%);");
                }
            }
```
