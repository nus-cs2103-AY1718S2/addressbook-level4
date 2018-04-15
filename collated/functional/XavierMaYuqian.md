# XavierMaYuqian
###### /java/seedu/address/commons/events/model/GetPersonRequestEvent.java
``` java
/**
 *  Event to request current list of employees
 */
public class GetPersonRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/commons/events/model/PasswordChangedEvent.java
``` java
/**
 * Indicates password cahnged
 * */
public class PasswordChangedEvent extends BaseEvent {

    public final String password;

    public PasswordChangedEvent(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "timetable entry added: " + this.password;
    }
}
```
###### /java/seedu/address/commons/events/model/ReturnedPersonEvent.java
``` java
/**
 * Indicates employees list returned.
 */
public class ReturnedPersonEvent extends BaseEvent {

    private final ObservableList<Person> employees;

    public ReturnedPersonEvent(ObservableList<Person> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<Person> getEmployees() {
        return employees;
    }
}
```
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
/**
 * Delete certain tags in the address book.
 */
public class DeleteTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_ALIAS = "dt";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by its name.\n"
            + "Parameters: Tag name (case sensitive)\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_SUCCESS = "Tags deleted successfully";

    private Tag tagToBeDeleted;

    public DeleteTagCommand(Tag t) {
        this.tagToBeDeleted = t;
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        ObservableList<Tag> list = model.getAddressBook().getTagList();

        if (!list.contains(tagToBeDeleted)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG);
        }
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.deleteTag(tagToBeDeleted);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setComment(Comment comment) {
            this.comment = comment; }

```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public Optional<Comment> getComment() {
            return Optional.ofNullable(comment);
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
                    && getTimeZone().equals(e.getTimeZone())
                    && getComment().equals(e.getComment())
                    && getTags().equals(e.getTags());
        }
    }
}
```
###### /java/seedu/address/logic/commands/ExportPersonCommand.java
``` java
/**
 * export employees to a csv file.
 */
public class ExportPersonCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String COMMAND_ALIAS = "ep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export current person to a csv file.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "All person Exported to person.csv!";

    public static final String MESSAGE_FAIL = "Export fail! Make sure you haven't opened person.csv and try again!";

    public static final String EXPORT_FILE_PATH = "data/person.csv";

    private ObservableList<Person> person;

    private boolean isTest;

    public ExportPersonCommand() {
        registerAsAnEventHandler(this);
    }

    public ExportPersonCommand(boolean isTest) {
        this.isTest = isTest;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new GetPersonRequestEvent());

        File csv;
        if (isTest) {
            csv = new File("person.csv");
        } else {
            csv = new File(EXPORT_FILE_PATH);
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(csv));
            bw.write("Name,Phone,Email,Address,Tags\n");
            for (Person p : person) {
                String temp = p.getName().fullName + "," + p.getPhone().value + "," + p.getEmail().value + ","
                        + p.getAddress().value.replaceAll(",", " ");
                if (!p.getTags().isEmpty()) {
                    temp = temp + "," + p.getTags().toString()
                            .replaceAll(", ", " | ");
                }
                temp = temp + "\n";
                bw.write(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_FAIL);
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleReturnedEmployeesEvent(ReturnedPersonEvent event) {
        this.person = event.getEmployees();
    }
}
```
###### /java/seedu/address/logic/commands/ListAppointmentCommand.java
``` java
/**
 * Lists all unarchived persons in the address book to the user.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "listappointment";
    public static final String COMMAND_ALIAS = "lap";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";


    @Override
    public CommandResult execute() {
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/LockCommand.java
``` java
/**
 * Locks the app with a password
 * */
public class LockCommand extends Command {

    public static final String COMMAND_WORD = "lock";

    public static final String COMMAND_ALIAS = "lk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been locked!";

    private final HideAllPerson predicate1 = new HideAllPerson();

    private final HideAllAppointment predicate2 = new HideAllAppointment();

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate1);
        model.updateFilteredAppointmentList(predicate2);
        LogicManager.lock();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/SetPasswordCommand.java
``` java
/**
 * Set the application password
 * */
public class SetPasswordCommand extends Command {

    public static final String COMMAND_WORD = "setPassword";

    public static final String COMMAND_ALIAS = "sp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set new application password, and old "
            + "password required."
            + "Parameters: "
            + "oldPassword" + " newPassword ";

    public static final String MESSAGE_SUCCESS = "New password has been set!";

    public static final String MESSAGE_INCORRECT_OLDPASSWORD = "Incorrect old password!";

    private String oldPassword;

    private String newPassword;

    public SetPasswordCommand(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute() {
        if (this.oldPassword.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.setPassword(this.newPassword);
            model.setPassword(this.newPassword);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_INCORRECT_OLDPASSWORD);
        }
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPasswordCommand // instanceof handles nulls
                && this.oldPassword.equals(((SetPasswordCommand) other).getOldPassword())
                && this.newPassword.equals(((SetPasswordCommand) other).getNewPassword())); // state check
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts all persons in the address book based on alphabetical order of their names.
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";

    public static final String MESSAGE_SUCCESS = "Sorted successfully";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/UnlockCommand.java
``` java
/**
 * Unlocks the addressbook
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String COMMAND_ALIAS = "ulk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlock the address book. "
            + "Parameters: "
            + "Password ";

    public static final String MESSAGE_SUCCESS = "Address book has been unlocked!";

    public static final String MESSAGE_INCORRECT_PASSWORD = "Incorrect unlock password!";

    private String password;

    public UnlockCommand(String keyword) {
        this.password = keyword;
    }

    @Override
    public CommandResult execute() {
        if (!LogicManager.isLocked()) {
            return new CommandResult("SellIT is already unlocked!");
        }

        if (this.password.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.unLock();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_INCORRECT_PASSWORD);
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnlockCommand // instanceof handles nulls
                && this.password.equals(((UnlockCommand) other).getPassword())); // state check
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        try {
            Tag t = ParserUtil.parseTag(args);
            return new DeleteTagCommand(t);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/LockCommandParser.java
``` java
/**
 * LockCommandParser
 */
public class LockCommandParser implements Parser<LockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LockCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        return new LockCommand();
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String comment} into a {@code Comment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code comment} is invalid.
     */
    public static Comment parseComment(String comment) throws IllegalValueException {
        requireNonNull(comment);
        String trimmedComment = comment.trim();
        if (!Comment.isValidComment(trimmedComment)) {
            throw new IllegalValueException(Comment.MESSAGE_COMMENT_CONSTRAINTS);
        }
        return new Comment(trimmedComment);
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> comment} into an {@code Optional<Comment>} if {@code comment} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Comment> parseComment(Optional<String> comment) throws IllegalValueException {
        requireNonNull(comment);
        return comment.isPresent() ? Optional.of(parseComment(comment.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
```
###### /java/seedu/address/logic/parser/SetPasswordCommandParser.java
``` java
/**
 * Parses arguments for the SetPasswordCommand'
 */
public class SetPasswordCommandParser implements Parser<SetPasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetPasswordCommand
     * and returns an SetPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetPasswordCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Scanner sc = new Scanner(trimmedArgs);
        if (!sc.hasNext()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE));
        }
        String oldPsw = sc.next();
        if (!sc.hasNext()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE));
        }
        String newPsw = sc.next();

        return new SetPasswordCommand(oldPsw, newPsw);
    }
}
```
###### /java/seedu/address/logic/parser/UnlockCommandParser.java
``` java
/**
 * Parses arguments for the UnlockCommand'
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnlockCommand
     * and returns an UnlockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnlockCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
        }
        String trimmedArgs = args.trim();

        return new UnlockCommand(trimmedArgs);
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command;
            CommandResult result = new CommandResult("");
            if (isLocked) {
                command = addressBookParser.parseCommand(commandText);
                if (command instanceof UnlockCommand) {
                    UnlockCommand unknockCommand = (UnlockCommand) command;
                    if (unknockCommand.getPassword().compareTo(password) == 0) {
                        isLocked = false;
                        result = new CommandResult(UnlockCommand.MESSAGE_SUCCESS);
                    } else {
                        result = new CommandResult("incorrect unlock password!");
                    }
                } else {
                    result = new CommandResult("Addressbook has been locked, please unlock it first!");
                }
            } else {
                command = addressBookParser.parseCommand(commandText);
                command.setData(model, history, undoRedoStack);
                result = command.execute();
                undoRedoStack.push(command);
            }
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    public static String getPassword() {
        return password;
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    public static void setPassword(String psw) {
        password = psw;
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    public static void unLock() {
        isLocked = false;
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    public static void lock() {
        isLocked = true;
    }

```
###### /java/seedu/address/logic/LogicManager.java
``` java
    public static boolean isLocked() {
        return isLocked;
    }
}
```
###### /java/seedu/address/model/person/Comment.java
``` java
/**
 * Represents a person's comment in the address book.
 */
public class Comment {

    public static final String MESSAGE_COMMENT_CONSTRAINTS =
            "Comment can take any values, and it should not be blank. "
                    + "If you don't have thing to note down, please put 'NIL'";

    /*
     * The first character of the comment must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COMMENT_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Comment}.
     *
     * @param comment A valid comment.
     */
    public Comment(String comment) {
        requireNonNull(comment);
        checkArgument(isValidComment(comment), MESSAGE_COMMENT_CONSTRAINTS);
        this.value = comment;
    }

    /**
     * Returns true if a given string is a valid comment.
     */
    public static boolean isValidComment(String test) {
        return test.matches(COMMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && this.value.equals(((Comment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/HideAllPerson.java
``` java
/**
 * HideAllPerson
 */
public class HideAllPerson implements Predicate<Person> {

    public HideAllPerson() {}

    @Override
    public boolean test(Person person) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sort all persons based on alphabetical order of their full names
     */
    public void sort() {
        internalList.sort((personA, personB) -> (
            personA.getName().fullName.toLowerCase().compareTo(personB.getName().fullName.toLowerCase())));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /java/seedu/address/model/tag/TagNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes all {@code Tag}s that are not used by any {@code Person} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInPersons = persons.asObservableList().stream()
                .map(Person::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInPersons);
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Sorts all the persons alphabetical order of their names
     */
    public void sort() {
        requireNonNull(persons);
        persons.sort();
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes tags from persons
     */
    public void removeTag(Tag t) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(t, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: original person is obtained from the address book.");
        }
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes tags from persons
     */
    private void removeTagFromPerson(Tag t, Person person) throws PersonNotFoundException {
        Set < Tag > newTags = new HashSet<>(person.getTags());

        if (!newTags.remove(t)) {
            return;
        }

        Person newPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                                      person.getAddress(), person.getCustTimeZone(), person.getComment(), newTags);

        try {
            updatePerson(person, newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                     + "See Person#equals(Object).");
        }
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    @Override
    public String getPassword() {
        return password;
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setPassword(String password) {
        this.password = password;
    }

```
###### /java/seedu/address/model/Model.java
``` java
    /** Sorts the persons in AddressBook based on the alphabetical order of their names*/
    void sort();

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

```
###### /java/seedu/address/model/Model.java
``` java
    /** Removes the given {@code tag} from all {@code Person}s. */
    void deleteTag(Tag t);

```
###### /java/seedu/address/model/Model.java
``` java
    /** Adds the given password */
    void setPassword(String e);

```
###### /java/seedu/address/model/Model.java
``` java
    /** Gets the password */
    String getPassword();

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void sort() {
        addressBook.sort();
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void deleteTag(Tag t) {
        addressBook.removeTag(t);
    }

    private void indicatePasswordChangedEvent(String p) {
        raise(new PasswordChangedEvent(p));
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void setPassword(String password) {
        addressBook.setPassword(password);
        indicateAddressBookChanged();
        indicatePasswordChangedEvent(password);
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    public String getPassword() {
        return addressBook.getPassword();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Appointments List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointments);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredAppointments.equals(other.filteredAppointments);
    }

    @Subscribe
    public void handleGetPersonRequestEvent(GetPersonRequestEvent event) {
        EventsCenter.getInstance().post(new ReturnedPersonEvent(addressBook.getPersonList()));
    }

}
```
###### /java/seedu/address/model/appointment/HideAllAppointment.java
``` java
/**
 * HideAllPerson
 */
public class HideAllAppointment implements Predicate<Appointment> {

    public HideAllAppointment() {}

    @Override
    public boolean test(Appointment appointment) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

}
```
###### /java/seedu/address/storage/XmlAdaptedPassword.java
``` java
/**
 * JAXB-friendly adapted version of the Password.
 */
public class XmlAdaptedPassword {

    @XmlValue
    private String password;

    /**
     * Constructs an XmlAdaptedPassword.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPassword() {}

    /**
     * Constructs a {@code XmlAdaptedPassword} with the given {@code password}.
     */
    public XmlAdaptedPassword(String password) {
        this.password = password;
    }

    /**
     * Converts this jaxb-friendly adapted password object into the model's password object.
     */
    public String toModelType() {
        return this.password;
    }
}
```
###### /java/seedu/address/ui/StatusBarFooter.java
``` java
    private void setTotalPersons(int totalPersons) {
        Platform.runLater(() -> this.totalPersonsStatus.setText(String.format(TOTAL_PERSONS_STATUS, totalPersons)));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setTotalPersons(abce.data.getPersonList().size());
    }
}
```
