# chuakunhong
###### \java\seedu\address\logic\commands\AddRemarkCommand.java
``` java
/**
 * Edits the details of an existing person in the address book.
 */
public class AddRemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addremark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds remarks to the student that you want. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARKS...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Need help" + "\n";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark added: %1$s\nPerson: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public AddRemarkCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, editPersonDescriptor.getRemark().get(),
                                                personToEdit.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Subject> updatedSubjects = editPersonDescriptor.getSubjects().orElse(personToEdit.getSubjects());
        Remark updatedRemark = parseRemark(((personToEdit.getRemark()).toString() + "\n"
                                            + editPersonDescriptor.getRemark().get().toString()));

        return new Person(updatedName, updatedNric, updatedTags, updatedSubjects, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRemarkCommand)) {
            return false;
        }

        // state check
        AddRemarkCommand e = (AddRemarkCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Nric nric;
        private Set<Tag> tags;
        private Set<Subject>  subjects;
        private Remark remark;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setNric(toCopy.nric);
            setTags(toCopy.tags);
            setSubjects(toCopy.subjects);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.nric, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
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

        /**
         * Sets {@code subjects} to this object's {@code subjects}.
         * A defensive copy of {@code subjects} is used internally.
         */
        public void setSubjects(Set<Subject> subjects) {
            this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
        }

        /**
         * Sets {@code remarks} to this object's {@code remarks}.
         * A defensive copy of {@code remarks} is used internally.
         */
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        /**
         * Sets {@code remarks} to this object's {@code remarks}.
         * A defensive copy of {@code remarks} is used internally.
         */
        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        /**
         * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code subjects} is null.
         */
        public Optional<Set<Subject>> getSubjects() {
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
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
                    && getNric().equals(e.getNric())
                    && getTags().equals(e.getTags())
                    && getSubjects().equals(e.getSubjects());
        }
    }
```
###### \java\seedu\address\logic\commands\DeleteRemarkCommand.java
``` java
/**
 * Edits the details of an existing person in the address book.
 */
public class DeleteRemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteremark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete remarks from the student that you want. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARKS...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Need help" + "\n";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark deleted: %1$s\nPerson: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public DeleteRemarkCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, editPersonDescriptor.getRemark().get(),
                                                personToEdit.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Subject> updatedSubjects = editPersonDescriptor.getSubjects().orElse(personToEdit.getSubjects());
        String[] remarkArray = personToEdit.getRemark().toString().split("\n");
        String updatingRemark = "";
        for (String remark: remarkArray) {
            System.out.println(remark + (editPersonDescriptor.getRemark()).get());
            if (!remark.contains(editPersonDescriptor.getRemark().get().toString())) {
                updatingRemark = updatingRemark + remark + "\n";
            } else {
                editPersonDescriptor.setRemark(parseRemark(remark));
            }
        }
        Remark updatedRemark = parseRemark(updatingRemark);

        return new Person(updatedName, updatedNric, updatedTags, updatedSubjects, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRemarkCommand)) {
            return false;
        }

        // state check
        DeleteRemarkCommand e = (DeleteRemarkCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Nric nric;
        private Set<Tag> tags;
        private Set<Subject>  subjects;
        private Remark remark;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setNric(toCopy.nric);
            setTags(toCopy.tags);
            setSubjects(toCopy.subjects);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.nric, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
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

        /**
         * Sets {@code subjects} to this object's {@code subjects}.
         * A defensive copy of {@code subjects} is used internally.
         */
        public void setSubjects(Set<Subject> subjects) {
            this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
        }

        /**
         * Sets {@code remarks} to this object's {@code remarks}.
         * A defensive copy of {@code remarks} is used internally.
         */
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        /**
         * Sets {@code remarks} to this object's {@code remarks}.
         * A defensive copy of {@code remarks} is used internally.
         */
        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        /**
         * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code subjects} is null.
         */
        public Optional<Set<Subject>> getSubjects() {
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
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
                    && getNric().equals(e.getNric())
                    && getTags().equals(e.getTags())
                    && getSubjects().equals(e.getSubjects());
        }
    }
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Edits the details of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": You can put anything, even nothing. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARKS...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Need help\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "\n";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Remark added: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public RemarkCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Subject> updatedSubjects = editPersonDescriptor.getSubjects().orElse(personToEdit.getSubjects());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());

        return new Person(updatedName, updatedNric, updatedTags, updatedSubjects, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Nric nric;
        private Set<Tag> tags;
        private Set<Subject>  subjects;
        private Remark remark;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setNric(toCopy.nric);
            setTags(toCopy.tags);
            setSubjects(toCopy.subjects);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.nric, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
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

        /**
         * Sets {@code subjects} to this object's {@code subjects}.
         * A defensive copy of {@code subjects} is used internally.
         */
        public void setSubjects(Set<Subject> subjects) {
            this.subjects = (subjects != null) ? new HashSet<>(subjects) : null;
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        /**
         * Returns an unmodifiable subject set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code subjects} is null.
         */
        public Optional<Set<Subject>> getSubjects() {
            return (subjects != null) ? Optional.of(Collections.unmodifiableSet(subjects)) : Optional.empty();
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
                    && getNric().equals(e.getNric())
                    && getTags().equals(e.getTags())
                    && getSubjects().equals(e.getSubjects());
        }
    }
}
```
###### \java\seedu\address\logic\commands\TagReplaceCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 *
 *
 */
public class TagReplaceCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tagreplace";
    public static final String COMMAND_ALIAS = "tr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Replace specified tag from everyone in the "
            + "address book with the intended tag." + "Parameters: Tag \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "1A " + PREFIX_TAG + "2A\n"
            + "Example: " + COMMAND_ALIAS + " " + PREFIX_TAG + "1A " + PREFIX_TAG + "2A";

    public static final String MESSAGE_REPLACE_TAG_SUCCESS = "Replaced Tag: From %1$s to %2$s";

    private List<Tag> tagSet = new LinkedList<>();
    private Tag[] tagArray = new Tag[2];

    public TagReplaceCommand(List<Tag> tagSet) {
        requireNonNull(tagSet);
        this.tagSet = tagSet;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tagSet);
        model.replaceTag(tagSet);
        return new CommandResult(String.format(MESSAGE_REPLACE_TAG_SUCCESS, tagArray[0], tagArray[1]));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Tag> lastShownList = addressBook.getTagList();
        tagSet.toArray(tagArray);
        if (!lastShownList.contains(tagArray[0]) && !tagSet.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_ENTERED);
        }

    }
}
```
###### \java\seedu\address\logic\parser\AddRemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AddRemarkCommandParser implements Parser<AddRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarkCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarkCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get().toString().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarkCommand.MESSAGE_USAGE)));
        } else {
            ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).ifPresent(editPersonDescriptor::setRemark);
        }
        return new AddRemarkCommand(index, editPersonDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
```
###### \java\seedu\address\logic\parser\DeleteRemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class DeleteRemarkCommandParser implements Parser<DeleteRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRemarkCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRemarkCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get().toString().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                    DeleteRemarkCommand.MESSAGE_USAGE)));
        } else {
            ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).ifPresent(editPersonDescriptor::setRemark);
        }
        return new DeleteRemarkCommand(index, editPersonDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).ifPresent(editPersonDescriptor::setRemark);

        return new RemarkCommand(index, editPersonDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\TagReplaceCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class TagReplaceCommandParser implements Parser<TagReplaceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagReplaceCommand
     * and returns an TagReplaceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagReplaceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagReplaceCommand.MESSAGE_USAGE));
        }

        try {
            List<Tag> tagSet = ParserUtil.parseTagsForReplacement(argMultimap.getAllValues(PREFIX_TAG));
            return new TagReplaceCommand(tagSet);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagReplaceCommand.MESSAGE_USAGE));
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
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Calls replaceTagForPerson method when tag is found in tags.
     * @param tagSet
     * @throws TagNotFoundException
     */
    public void replaceTag(List<Tag> tagSet) {
        Tag[] tagArray = new Tag[2];
        tagSet.toArray(tagArray);
        Tag tagToBeReplaced = tagArray[0];
        Tag tagToBePlaced = tagArray[1];
        if (tags.contains(tagToBeReplaced)) {
            for (Person person : persons) {
                replaceTagForPerson(tagToBeReplaced, tagToBePlaced, person);
            }
        } else {
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes a specific tag from an individual person and updates the person's information.
     * @param tagToBeReplaced
     * @param tagToBePlaced
     * @param person
     */
    public void replaceTagForPerson(Tag tagToBeReplaced, Tag tagToBePlaced, Person person) {
        Set<Tag> tagList = new HashSet<>(person.getTags());
        if (tagList.remove(tagToBeReplaced)) {
            tagList.add(tagToBePlaced);
            Person newPerson = new Person(person.getName(), person.getNric(), tagList, person.getSubjects(),
                                        person.getRemark());

            try {
                updatePerson(person, newPerson);
            } catch (DuplicatePersonException error1) {
                throw new AssertionError("Updating person after removing tag should not have duplicate persons.");
            } catch (PersonNotFoundException error2) {
                throw new AssertionError("Person should exist in the address book.");
            }
        }
    }

    //// util methods

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Replaces a specific tag for everyone in the address book.
     * @param tagSet
     */
    void replaceTag(List<Tag> tagSet);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void replaceTag(List<Tag> tagSet) {
        Tag[] tagArray = new Tag[2];
        tagSet.toArray(tagArray);
        addressBook.replaceTag(tagSet);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\person\Nric.java
``` java
/**
 * Represents a Person's nric number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {


    public static final String MESSAGE_NRIC_CONSTRAINTS =
            "Nric should start with a S, should be at 7 digits long"
                    + " and should end with a capital letter.";
    public static final String NRIC_VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";
    public final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric number.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_NRIC_CONSTRAINTS);
        this.value = nric;
    }

    /**
     * Returns true if a given string is a valid person nric number.
     */
    public static boolean isValidNric(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && this.value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a remarks of the person in the address book.
 */
public class Remark {


    public static final String MESSAGE_REMARK_CONSTRAINTS = "Remark can contain anything that you want.";
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
