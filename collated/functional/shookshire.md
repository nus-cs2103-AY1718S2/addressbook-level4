# shookshire
###### \java\seedu\address\logic\commands\AddClientCommand.java
``` java
/**
 * Adds a tutor to TuitionCor.
 */
public class AddClientCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addclient";
    public static final String COMMAND_ALIAS = "ac";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to TuitionCor. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]... "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_GRADE + "GRADE "
            + PREFIX_SUBJECT + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_CATEGORY + "t "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_LOCATION + "east "
            + PREFIX_GRADE + "p6 "
            + PREFIX_SUBJECT + "physics";

    public static final String MESSAGE_SUCCESS_STUDENT = "New student added: %1$s";
    public static final String MESSAGE_SUCCESS_TUTOR = "New tutor added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student/tutor already exists in the address book";

    private final Client toAdd;

    /**
     * Creates an AddClientCommand to add the specified {@code Client}
     */
    public AddClientCommand(Client client) {
        requireNonNull(client);
        toAdd = client;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        try {
            if (toAdd.getCategory().isStudent()) {
                model.addStudent(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS_STUDENT, toAdd));
            } else {
                model.addTutor(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS_TUTOR, toAdd));
            }

        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddClientCommand // instanceof handles nulls
                && toAdd.equals(((AddClientCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\RemoveCommand.java
``` java
/**
 * Remove the specified details of an existing client in the address book.
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "re";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the subject from the client identified "
            + "by the index number used in the last client listing. "
            + "If the specified subject exists it would be removed from the client. "
            + "Input subject should be a single word without space.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_SUBJECT + "SUBJECT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CATEGORY + "s "
            + PREFIX_SUBJECT + "math";

    public static final String MESSAGE_REMOVE_CLIENT_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_VALUE_DONT_EXIST = "The inputted subject does not exist";
    public static final String MESSAGE_LAST_VALUE = "The last subject cannot be removed.\n"
            + "Recommended to delete or close client instead";

    public static final String REMOVE_VALIDATION_REGEX = "[a-zA-Z]+";

    private final Index index;
    private final Category category;
    private final Subject toRemove;

    private Client personToEdit;
    private Client editedPerson;


    /**
     * @param index of the person in the filtered person list to edit
     * @param toRemove the subject to be removed
     */
    public RemoveCommand(Index index, Subject toRemove, Category category) {
        requireNonNull(index);
        requireNonNull(toRemove);
        requireNonNull(category);

        this.category = category;
        this.index = index;
        this.toRemove = toRemove;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateClient(personToEdit, editedPerson, category);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
        return new CommandResult(String.format(MESSAGE_REMOVE_CLIENT_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        List<Client> lastShownList;

        if (category.isStudent()) {
            lastShownList = model.getFilteredStudentList();
        } else {
            lastShownList = model.getFilteredTutorList();
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());

        if (!isValidRemovableSubject(toRemove)) {
            throw new CommandException(MESSAGE_USAGE);
        }

        if (!containsSubject(personToEdit, toRemove)) {
            throw new CommandException(MESSAGE_VALUE_DONT_EXIST);
        }

        if (!isMoreThanOne(personToEdit.getSubject())) {
            throw new CommandException(MESSAGE_LAST_VALUE);
        }

        editedPerson = createEditedPerson(personToEdit, toRemove);
    }

    /**
     * @param personToEdit the client that we wish to remove a subject from
     * @param toRemove the subject to be removed
     * Returns true if the subject exists
     */
    private static boolean containsSubject(Client personToEdit, Subject toRemove) {
        String originalSubject = personToEdit.getSubject().toString();
        ArrayList<String> originalSubjectArrayList = new ArrayList<>(Arrays.asList(originalSubject.split(" ")));

        return originalSubjectArrayList.contains(toRemove.value);
    }

    private static boolean isValidRemovableSubject(Subject test) {
        return test.value.matches(REMOVE_VALIDATION_REGEX);
    }

    private static boolean isMoreThanOne(Subject test) {
        String[] testArray = test.value.split(" ");
        return testArray.length > 1;
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Client createEditedPerson(Client personToEdit, Subject toRemove) {
        assert personToEdit != null;

        String editSubjects = personToEdit.getSubject().value;
        ArrayList<String> editSubjectArray = new ArrayList<>(Arrays.asList(editSubjects.split(" ")));
        editSubjectArray.remove(toRemove.value);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < editSubjectArray.size(); i++) {
            sb.append(editSubjectArray.get(i));
            sb.append(" ");
        }

        return new Client(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getLocation(),
                personToEdit.getGrade(), new Subject(sb.toString()), personToEdit.getCategory());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveCommand)) {
            return false;
        }

        // state check
        RemoveCommand r = (RemoveCommand) other;
        return index.equals(r.index)
                && Objects.equals(editedPerson, r.editedPerson)
                && Objects.equals(personToEdit, r.personToEdit);
    }

}

```
###### \java\seedu\address\logic\parser\AddClientCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddClientCommand object
 */
public class AddClientCommandParser implements Parser<AddClientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClientCommand
     * and returns an AddClientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_LOCATION, PREFIX_GRADE, PREFIX_SUBJECT, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_LOCATION, PREFIX_GRADE, PREFIX_SUBJECT, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)).get();
            Grade grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE)).get();
            Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).get();
            Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY)).get();

            Client client = new Client(name, phone, email, address, tagList, location, grade, subject, category);

            return new AddClientCommand(client);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\RemoveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser implements Parser<RemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommand
     * and returns an RemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_SUBJECT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

        Index index;
        Category category;
        Subject subject;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY)).get();
            subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

        return new RemoveCommand(index, subject, category);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
    private final UniqueClientList students;
    private final UniqueClientList tutors;
    private final UniqueClientList closedStudents;
    private final UniqueClientList closedTutors;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueClientList();
        tutors = new UniqueClientList();
        closedTutors = new UniqueClientList();
        closedStudents = new UniqueClientList();
        tags = new UniqueTagList();
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setStudents(List<Client> students) throws DuplicatePersonException {
        this.students.setClients(students);
    }

    public void setTutors(List<Client> tutors) throws DuplicatePersonException {
        this.tutors.setClients(tutors);
    }

    public void setClosedStudents(List<Client> closedStudents) throws DuplicatePersonException {
        this.closedStudents.setClients(closedStudents);
    }

    public void setClosedTutors(List<Client> closedTutors) throws DuplicatePersonException {
        this.closedTutors.setClients(closedTutors);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a tutor to TuitionCor.
     * Also checks the new tutor's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tutor to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addTutor(Client t) throws DuplicatePersonException {
        Client tutor = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        tutors.add(tutor, closedTutors);
    }

    /**
     * Adds a student to TuitionCor.
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the student to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addStudent(Client t) throws DuplicatePersonException {
        Client student = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        students.add(student, closedStudents);
    }

    /**
     * Adds a student to closed student's list.
     * Also checks the closed student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the closed student to point to those in {@link #tags}.
     *
     * @throws AssertionError if an equivalent person already exists.
     */
    public void addClosedStudent(Client t) throws AssertionError {
        Client closedStudent = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        closedStudents.add(closedStudent);
    }

    /**
     * Adds a tutor to closed tutor's list.
     * Also checks the closed tutor's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the closed tutor to point to those in {@link #tags}.
     *
     * @throws AssertionError if an equivalent person already exists.
     */
    public void addClosedTutor(Client t) throws AssertionError {
        Client closedTutor = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        closedTutors.add(closedTutor);
    }

    /**
     * For test cases use and when adding sample data
     * Adds a closed client to TuitionCor.
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tutor to point to those in {@link #tags}.
     *
     */
    public void addClosedClient(Client t) {
        if (t.getCategory().isStudent()) {
            Client closedStudent = syncWithMasterTagList(t);
            closedStudents.add(closedStudent);
        } else {
            Client closedTutor = syncWithMasterTagList(t);
            closedTutors.add(closedTutor);
        }
    }

    /**
     * For test cases use and when adding sample data
     * Adds a client to TuitionCor
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tutor to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addClient(Client t) throws DuplicatePersonException {
        if (t.getCategory().isStudent()) {
            Client student = syncWithMasterTagList(t);
            students.add(student);
        } else {
            Client tutor = syncWithMasterTagList(t);
            tutors.add(tutor);
        }
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedClient}.
     * Either closedStudents or closedTutors will be pass in for duplication check when editing the client in active
     * list.
     * @throws DuplicatePersonException if updating the client's details causes the client to be equivalent to
     *      another existing client in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Client)
     */
    public void updatePerson(Client target, Client editedClient, Category category)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedClient);

        Client syncedEditedPerson = syncWithMasterTagList(editedClient);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        if (category.isStudent()) {
            students.setClient(target, syncedEditedPerson, closedStudents);
        } else {
            tutors.setClient(target, syncedEditedPerson, closedTutors);
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from the active client list in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeClient(Client key, Category category) throws PersonNotFoundException {
        Boolean isSuccess;

        if (category.isStudent()) {
            isSuccess = students.remove(key);
        } else {
            isSuccess = tutors.remove(key);
        }

        if (isSuccess) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Removes {@code key} from the closed client list in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeClosedClient(Client key, Category category) throws PersonNotFoundException {
        Boolean isSuccess;

        if (category.isStudent()) {
            isSuccess = closedStudents.remove(key);
        } else {
            isSuccess = closedTutors.remove(key);
        }

        if (isSuccess) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Client> getStudentList() {
        return students.asObservableList();
    }

    @Override
    public ObservableList<Client> getTutorList() {
        return tutors.asObservableList();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteClosedClient(Client target, Category category) throws PersonNotFoundException {
        addressBook.removeClosedClient(target, category);
        indicateAddressBookChanged();
    }

    @Override
    public void updateClient(Client target, Client editedPerson, Category category)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson, category);
        addressBook.updatePerson(target, editedPerson, category);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTutor(Client tutor) throws DuplicatePersonException {
        addressBook.addTutor(tutor);
        updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addStudent(Client student) throws DuplicatePersonException {
        addressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addClosedTutor(Client closedTutor) throws DuplicatePersonException {
        addressBook.addClosedTutor(closedTutor);
        updateFilteredClosedTutorList(PREDICATE_SHOW_ALL_CLOSED_TUTORS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addClosedStudent(Client closedStudent) throws DuplicatePersonException {
        addressBook.addClosedStudent(closedStudent);
        updateFilteredClosedStudentList(PREDICATE_SHOW_ALL_CLOSED_STUDENTS);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Client> getFilteredStudentList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredStudents);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
        indicateAddressBookChanged();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Client> getFilteredTutorList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredTutors);
    }

    @Override
    public void updateFilteredTutorList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredTutors.setPredicate(predicate);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\Category.java
``` java
/**
 * Represents if a Client is a student or tutor in TuitionCor.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class Category {

    public static final String MESSAGE_CATEGORY_CONSTRAINTS =
            "Client Category can only be s or t, representing student or tutor respectively";

    /*
     * Must be either s or t
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[st]";

    public final String value;

    /**
     * Constructs an {@code Category}.
     *
     * @param category A valid category.
     */
    public Category(String category) {
        requireNonNull(category);
        checkArgument(isValidCategory(category), MESSAGE_CATEGORY_CONSTRAINTS);
        this.value = category;
    }

    /**
     * Returns true if a given string is a valid client category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    public boolean isStudent() {
        return value.equals("s");
    }

    public boolean isTutor() {
        return value.equals("t");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.value.equals(((Category) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Client.java
``` java
/**
 * Represents a Client in tuitionCor.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client extends Person {

    private final Location location;
    private final Grade grade;
    private final Subject subject;
    private final Category category;
    private int rank = 0;
    private boolean matchedGrade = false;
    private boolean matchedSubject = false;
    private boolean matchedLocation = false;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Location location,
                  Grade grade, Subject subject, Category category) {
        super(name, phone, email, address, tags);
        requireAllNonNull(location, grade, subject);
        this.location = location;
        this.grade = grade;
        this.subject = subject;
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public Grade getGrade() {
        return grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public Category getCategory() {
        return category;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int value) {
        this.rank = value;
    }

    public boolean getMatchedGrade() {
        return matchedGrade;
    }

    public void setMatchedGrade(boolean isMatch) {
        this.matchedGrade = isMatch;
    }

    public boolean getMatchedSubject() {
        return matchedSubject;
    }

    public void setMatchedSubject(boolean isMatch) {
        this.matchedSubject = isMatch;
    }

    public boolean getMatchedLocation() {
        return matchedLocation;
    }

    public void setMatchedLocation(boolean isMatch) {
        this.matchedLocation = isMatch;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.getName(), this.getPhone(), this.getEmail(), this.getAddress(),
                this.getTags(), location, grade, subject, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Location: ")
                .append(getLocation())
                .append(" Grade: ")
                .append(getGrade())
                .append(" Subject: ")
                .append(getSubject());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\person\Grade.java
``` java
/**
 * Represents a Client's related Grade (the year of study eg. Primary 4, Secondary 3) in the TuitionCor.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {
    public static final String MESSAGE_WORD_KINDERGARTEN = "Kindergarten";
    public static final String MESSAGE_WORD_KINDERGARTEN_ALIAS = "K";
    public static final String MESSAGE_WORD_PRIMARY = "Primary";
    public static final String MESSAGE_WORD_PRIMARY_ALIAS = "P";
    public static final String MESSAGE_WORD_SECONDARY = "Secondary";
    public static final String MESSAGE_WORD_SECONDARY_ALIAS = "S";
    public static final String MESSAGE_WORD_TERTIARY = "Tertiary";
    public static final String MESSAGE_WORD_TERTIARY_ALIAS = "J";
    public static final String MESSAGE_WORD_UNIVERSITY = "University";
    public static final String MESSAGE_WORD_UNIVERSITY_ALIAS = "U";

    private static final String KINDERGARTEN_REGEX = "kindergarten";
    private static final String KINDERGARTEN_ALIAS_REGEX = "k";
    private static final String PRIMARY_REGEX = "primary";
    private static final String PRIMARY_ALIAS_REGEX = "p";
    private static final String SECONDARY_REGEX = "secondary";
    private static final String SECONDARY_ALIAS_REGEX = "s";
    private static final String TERTIARY_REGEX = "tertiary";
    private static final String TERTIARY_ALIAS_REGEX = "j";
    private static final String UNIVERSITY_REGEX = "university";
    private static final String UNIVERSITY_ALIAS_REGEX = "u";

    private static final String GRADE_VALIDATION_REGEX_KINDERGARTEN_FULL = "(?i)"
            + MESSAGE_WORD_KINDERGARTEN + "[1-3]";
    private static final String GRADE_VALIDATION_REGEX_KINDERGARTEN_ALIAS = "(?i)"
            + MESSAGE_WORD_KINDERGARTEN_ALIAS + "[1-3]";
    private static final String GRADE_VALIDATION_REGEX_PRIMARY_FULL = "(?i)"
            + MESSAGE_WORD_PRIMARY + "[1-6]";
    private static final String GRADE_VALIDATION_REGEX_PRIMARY_ALIAS = "(?i)"
            + MESSAGE_WORD_PRIMARY_ALIAS + "[1-6]";
    private static final String GRADE_VALIDATION_REGEX_SECONDARY_FULL = "(?i)"
            + MESSAGE_WORD_SECONDARY  + "[1-5]";
    private static final String GRADE_VALIDATION_REGEX_SECONDARY_ALIAS = "(?i)"
            + MESSAGE_WORD_SECONDARY_ALIAS + "[1-5]";
    private static final String GRADE_VALIDATION_REGEX_TERTIARY_FULL = "(?i)"
            + MESSAGE_WORD_TERTIARY  + "[1-2]";
    private static final String GRADE_VALIDATION_REGEX_TERTIARY_ALIAS = "(?i)"
            + MESSAGE_WORD_TERTIARY_ALIAS + "[1-2]";
    private static final String GRADE_VALIDATION_REGEX_UNIVERSITY_FULL = "(?i)"
            + MESSAGE_WORD_UNIVERSITY  + "[1-4]";
    private static final String GRADE_VALIDATION_REGEX_UNIVERSITY_ALIAS = "(?i)"
            + MESSAGE_WORD_UNIVERSITY_ALIAS + "[1-4]";

    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grades accepted are " + MESSAGE_WORD_KINDERGARTEN + " (1-3)"
                    + ", " + MESSAGE_WORD_PRIMARY + " (1-6)"
                    + ", " + MESSAGE_WORD_SECONDARY + " (1-5)"
                    + ", " + MESSAGE_WORD_TERTIARY + " (1-2)"
                    + ", " + MESSAGE_WORD_UNIVERSITY + " (1-4).\n"
                    + "Alias acceptable are " + MESSAGE_WORD_KINDERGARTEN_ALIAS + " for " + MESSAGE_WORD_KINDERGARTEN
                    + ", " + MESSAGE_WORD_PRIMARY_ALIAS + " for " + MESSAGE_WORD_PRIMARY
                    + ", " + MESSAGE_WORD_SECONDARY_ALIAS + " for " + MESSAGE_WORD_SECONDARY
                    + ", " + MESSAGE_WORD_TERTIARY_ALIAS + " for " + MESSAGE_WORD_TERTIARY
                    + ", " + MESSAGE_WORD_UNIVERSITY_ALIAS + " for " + MESSAGE_WORD_UNIVERSITY + ".\n"
                    + "Examples of valid input for grade: " + MESSAGE_WORD_KINDERGARTEN  + "1"
                    + " or " + MESSAGE_WORD_KINDERGARTEN_ALIAS + "1" + ", "
                    + MESSAGE_WORD_TERTIARY  + "2"
                    + " or " + MESSAGE_WORD_TERTIARY_ALIAS + "2.\n"
                    + "multiple grades should be typed with a single space between them "
                    + "in decreasing order of preferences with no repetitions";

    private static final int levelIndex = 0;
    private static final int yearIndex = 1;

    public final String value;

    public final int valueWeightage; //Stores the int value weightage of only the first grade in the list

    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid grade.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        this.value = grade.trim().replaceAll(" +", " ");
        this.valueWeightage = getGradeIndex(this.value);
    }

    /**
     * @return an int value base on the grade or the first grade in a string of multiple grades
     */
    public static int getGradeIndex(String value) {
        final String levelField = getGradeFields(value)[levelIndex].toLowerCase();

        int tempIndex = 0;

        switch (levelField) {
        case KINDERGARTEN_REGEX:
            tempIndex += 1;
            break;

        case KINDERGARTEN_ALIAS_REGEX:
            tempIndex += 1;
            break;

        case PRIMARY_REGEX:
            tempIndex += 4;
            break;

        case PRIMARY_ALIAS_REGEX:
            tempIndex += 4;
            break;

        case SECONDARY_REGEX:
            tempIndex += 10;
            break;

        case SECONDARY_ALIAS_REGEX:
            tempIndex += 10;
            break;

        case TERTIARY_REGEX:
            tempIndex += 15;
            break;

        case TERTIARY_ALIAS_REGEX:
            tempIndex += 15;
            break;

        case UNIVERSITY_REGEX:
            tempIndex += 17;
            break;

        case UNIVERSITY_ALIAS_REGEX:
            tempIndex += 17;
            break;

        default:
            throw new AssertionError("It should not be possible to reach here");
        }

        tempIndex += (Integer.parseInt(getGradeFields(value)[yearIndex]) - 1);

        return tempIndex;
    }

    /**
     * Returns true if a given string is a valid client Grade.
     */
    public static boolean isValidGradeRegex(String test) {
        return test.matches(GRADE_VALIDATION_REGEX_KINDERGARTEN_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_KINDERGARTEN_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_PRIMARY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_PRIMARY_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_SECONDARY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_SECONDARY_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_TERTIARY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_TERTIARY_FULL)
                || test.matches(GRADE_VALIDATION_REGEX_UNIVERSITY_ALIAS)
                || test.matches(GRADE_VALIDATION_REGEX_UNIVERSITY_FULL);

    }

    /**
     * Returns true if all of the given string is a valid client Grade.
     */
    public static boolean isValidGrade(String test) {
        if (test.matches("\\s+")) {
            return false;
        }
        String[] splitGrade = test.split("\\s+");
        Set<String> isUnique = new HashSet<>();
        Set<Integer> isUniqueWeightage = new HashSet<>();

        boolean isValid = true;
        for (String ss : splitGrade) {
            if (isValid) {
                isValid = isValidGradeRegex(ss);
                isUnique.add(ss);
            }

            if (isValid) {
                isUniqueWeightage.add(getGradeIndex(ss));
            }
        }
        if (isUnique.size() != splitGrade.length || isUniqueWeightage.size() != splitGrade.length) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * @return gradeFields of only the first Grade in the string in terms of an array containing
     * Level(Primary,Secondary..) and Year(1,2...
     */
    private static String[] getGradeFields(String value) {
        String[] allGrades = value.split("\\s+");
        String[] gradeFields =  allGrades[0].split("(?=[\\d])");

        checkArgument(gradeFields.length == 2, "Error in grade fields format.");

        String temp = gradeFields[levelIndex];
        gradeFields[levelIndex] = temp.trim();
        gradeFields[yearIndex].trim();

        return gradeFields;
    }

    /**
     * @return an array containing all the grade weightage of the individual grades
     */
    public static int[] getAllGradeWeightage(String value) {
        String[] allGrades = value.split("\\s+");
        int[] allGradeWeightage = new int[allGrades.length];

        for (int i = 0; i < allGrades.length; i++) {
            allGradeWeightage[i] = getGradeIndex(allGrades[i]);
        }
        return allGradeWeightage;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grade // instanceof handles nulls
                && this.value.equals(((Grade) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Location.java
``` java
/**
 * Represents a Person's available Location in the TuitionCor.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Location should only be north, south, east, west and central in decreasing order of preference without "
                    + "any repetitions";

    private static final String LOCATION_VALIDATION_REGEX_NORTH = "(?i)North";
    private static final String LOCATION_VALIDATION_REGEX_SOUTH = "(?i)South";
    private static final String LOCATION_VALIDATION_REGEX_EAST = "(?i)East";
    private static final String LOCATION_VALIDATION_REGEX_WEST = "(?i)West";
    private static final String LOCATION_VALIDATION_REGEX_CENTRAL = "(?i)Central";

    public final String value;

    /**
     * Constructs a {@code Location}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location.trim().replaceAll(" +", " ");
    }

    /**
     * Returns true if a given string is a valid client Location.
     */
    public static boolean isValidLocationRegex(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX_NORTH) || test.matches(LOCATION_VALIDATION_REGEX_EAST)
                || test.matches(LOCATION_VALIDATION_REGEX_SOUTH)
                || test.matches(LOCATION_VALIDATION_REGEX_WEST)
                || test.matches(LOCATION_VALIDATION_REGEX_CENTRAL);
    }

    /**
     * Returns true if all of the given string is a valid client Location.
     */
    public static boolean isValidLocation(String test) {
        if (test.matches("\\s+")) {
            return false;
        }
        String[] splitLocation = test.split("\\s+");
        Set<String> isUnique = new HashSet<>();
        boolean isValid = true;
        for (String ss : splitLocation) {
            if (isValid) {
                isValid = isValidLocationRegex(ss);
                isUnique.add(ss);
            }
        }
        if (isUnique.size() != splitLocation.length) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Subject.java
``` java
/**
 * Represents a Person's related Subject in the TuitionCor.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_SUBJECT_CONSTRAINTS =
            "Subjects can take any value and should not be blank";

    /*
     * The first character of the Subject must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Subject}.
     *
     * @param subject A valid subject.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        checkArgument(isValidSubject(subject), MESSAGE_SUBJECT_CONSTRAINTS);
        this.value = subject.trim().replaceAll(" +", " ");
    }

    /**
     * Returns true if a given string is a valid person Subject.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && this.value.equals(((Subject) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\UniqueClientList.java
``` java
/**
 * A list of client that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Client#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueClientList implements Iterable<Client> {

    private final ObservableList<Client> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent client as the given argument.
     */
    public boolean contains(Client toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a client to the active list.
     *
     * @throws DuplicatePersonException if the client to add is a duplicate of an existing client in the list.
     */
    public void add(Client toAdd, UniqueClientList closedList) throws DuplicatePersonException {
        requireNonNull(toAdd);
        if (contains(toAdd) || closedList.contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a client to TuitionCor.
     *
     * @throws AssertionError as it's impossible to have a duplicate given that we have checked for duplication
     * before adding it into active list.
     */
    public void add(Client toAdd) throws AssertionError {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new AssertionError("It's impossible to have a duplicate person here");
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing client in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * Returns true if success.
     */
    public Boolean setClient(Client target, Client editedClient, UniqueClientList closedList)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedClient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedClient) && (internalList.contains(editedClient)
                || closedList.contains(editedClient))) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedClient);
        return true;
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Client toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean clientFoundAndDeleted = internalList.remove(toRemove);
        if (!clientFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return clientFoundAndDeleted;
    }

    public void setClients(UniqueClientList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setClients(List<Client> clients) throws DuplicatePersonException {
        requireAllNonNull(clients);
        final UniqueClientList replacement = new UniqueClientList();
        for (final Client client : clients) {
            replacement.add(client);
        }
        setClients(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Client> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Client> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueClientList // instanceof handles nulls
                && this.internalList.equals(((UniqueClientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedClient.java
``` java
/**
 * JAXB-friendly version of the Client for tutors.
 */
public class XmlAdaptedClient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String grade;
    @XmlElement(required = true)
    private String subject;
    @XmlElement(required = true)
    private String category;


    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedClient.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedClient() {}

    /**
     * Constructs an {@code XmlAdaptedClient} with the given person details.
     */
    public XmlAdaptedClient(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged,
                            String location, String grade, String subject, String category) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.location = location;
        this.grade = grade;
        this.subject = subject;
        this.category = category;
    }

    /**
     * Converts a given Client into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedClient
     */
    public XmlAdaptedClient(Client source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        location = source.getLocation().value;
        grade = source.getGrade().value;
        subject = source.getSubject().value;
        category = source.getCategory().value;
    }

    /**
     * Converts this jaxb-friendly adapted client object into the model's Client object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Client toModelType() throws IllegalValueException {
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

        if (this.location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(this.location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location location = new Location(this.location);

        if (this.grade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGrade(this.grade)) {
            throw new IllegalValueException(Grade.MESSAGE_GRADE_CONSTRAINTS);
        }
        final Grade grade = new Grade(this.grade);

        if (this.subject == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Subject.class.getSimpleName()));
        }
        if (!Subject.isValidSubject(this.subject)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        final Subject subject = new Subject(this.subject);

        if (this.category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(this.category)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        final Category category = new Category(this.category);


        return new Client(name, phone, email, address, tags, location, grade, subject, category);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedClient)) {
            return false;
        }

        XmlAdaptedClient otherClient = (XmlAdaptedClient) other;
        return Objects.equals(name, otherClient.name)
                && Objects.equals(phone, otherClient.phone)
                && Objects.equals(email, otherClient.email)
                && Objects.equals(address, otherClient.address)
                && tagged.equals(otherClient.tagged)
                && Objects.equals(location, otherClient.location)
                && Objects.equals(subject, otherClient.subject)
                && Objects.equals(grade, otherClient.grade)
                && Objects.equals(category, otherClient.category);

    }
}
```
###### \java\seedu\address\ui\ClientCard.java
``` java
/**
 * An UI component that displays information of a {@code Client}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    private static final String[] TAGS_COLOUR_STYLES =
        { "red" , "blue" , "green" , "yellow" , "purple" , "lightpink" , "gold" , "wheat" };

    private static final String MATCH_COLOUR_STYLE = "orange";

    private static final String UNMATCH_COLOUR_STYLE = "noFill";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label places;
    @FXML
    private Label grades;
    @FXML
    private Label subjects;
    @FXML
    private FlowPane tags;


    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;
        id.setText(displayedIndex + ". ");
        name.setText(client.getName().fullName);
        phone.setText(client.getPhone().value);
        address.setText(client.getAddress().value);
        email.setText(client.getEmail().value);
        intplaces(client);
        intGrades(client);
        intSubjects(client);
        intTags(client);
    }

    /**
     *@author olimhc-reused
     *Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits with minor modification
     * Initialises tags
     * @param client
     */
    private void intTags(Client client) {
        client.getTags().forEach(tag -> {
            Label newLabel = new Label(tag.tagName);
            newLabel.getStyleClass().add(getColour(tag.tagName));
            tags.getChildren().add(newLabel);
        });
    }

```
