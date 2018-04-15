# alexawangzi
###### \java\seedu\address\logic\commands\MatchCommand.java
``` java
/**
 * Match a tutor and a student in STUtor
 */
public class MatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "match";
    public static final String COMMAND_WORD_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Match a student and a tutor for lesson.\n"
            + "Parameters: INDEX_A, INDEX_B (must be non-zero positive integers, one student and one tutor.) "
            + "Example: " + COMMAND_WORD + " 4 7 ";

    public static final String MESSAGE_MATCH_SUCCESS = "Created new match %1$s\n";
    public static final String MESSAGE_MATCH_FAILED = "Matching failed.\n %1$s";
    public static final String MESSAGE_MISMATCH_WRONG_ROLE = "Incompatible role.";
    public static final String MESSAGE_MISMATCH_WRONG_SUBJECT = "Incompatible subject. ";
    public static final String MESSAGE_MISMATCH_WRONG_LEVEL = "Incompatible level. ";
    public static final String MESSAGE_MISMATCH_WRONG_PRICE = "Incompatible price.";
    public static final String MESSAGE_MISMATCH_ALREADY_MATCHED = "The two persons are already matched. ";
    public static final String MESSAGE_MISSING_FIELDS = "The person has missing fields. ";

    private final Index indexA;
    private final Index indexB;
    private Person student;
    private Person tutor;


    /**
     * @param indexA,of the person in the filtered person list to match
     */
    public MatchCommand(Index indexA, Index indexB) {
        requireAllNonNull(indexA, indexB);
        this.indexA = indexA;
        this.indexB = indexB;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addPair(student, tutor);
        } catch (DuplicatePairException dpe) {
            throw new CommandException(MESSAGE_MISMATCH_ALREADY_MATCHED);
        }
        return new CommandResult(String.format(MESSAGE_MATCH_SUCCESS, student.getName().fullName
                + " and " + tutor.getName().fullName));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (indexA.getZeroBased() >= lastShownList.size() || indexB.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        student = lastShownList.get(indexA.getZeroBased());
        tutor = lastShownList.get(indexB.getZeroBased());

        if (student.hasMissingFieldForMatch() || tutor.hasMissingFieldForMatch()) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISSING_FIELDS));
        }

        //filter invalid matchings
        if (student.getRole().equals(tutor.getRole())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_ROLE));
        }

        //standardize input order : person A is student, person B is tutor
        if (!student.getRole().value.equals("Student")) {
            Person temp = student;
            student = tutor;
            tutor = temp;
        }

        if (!student.getSubject().equals(tutor.getSubject())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_SUBJECT));
        }
        if (!student.getLevel().equals(tutor.getLevel())) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_LEVEL));
        }

        if (Integer.parseInt(student.getPrice().value) < Integer.parseInt(tutor.getPrice().value)) {
            throw new CommandException(String.format(MESSAGE_MATCH_FAILED, MESSAGE_MISMATCH_WRONG_PRICE));
        }

    }

    @Override
    public boolean equals(Object other) {
        return this == other //short circuit for same object
            || (other instanceof MatchCommand
            && indexA.equals(((MatchCommand) other).indexA) && indexB.equals(((MatchCommand) other).indexB));    }
}
```
###### \java\seedu\address\logic\commands\UnmatchCommand.java
``` java
/**
 * Unmatch a pair listed in STUtor
 */
public class UnmatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unmatch";
    public static final String COMMAND_WORD_ALIAS = "um";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmatch an existing pair.\n "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNMATCH_PAIR_SUCCESS = "Unmatched Pair: %1$s";
    public static final String MESSAGE_NOT_UNMATCHED = "At least one field to unmatch must be provided.";

    private final Index targetIndex;
    private Pair pairToUnmatch;

    /**
     * @param targetIndex of the pair in the filtered pair list to unmatch
     */
    public UnmatchCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(pairToUnmatch);
        try {
            model.deletePair(pairToUnmatch);
        } catch (PairNotFoundException pnfe) {
            throw new AssertionError("The target pair cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_UNMATCH_PAIR_SUCCESS, pairToUnmatch));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Pair> lastShownList = model.getFilteredPairList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);
        }

        pairToUnmatch = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmatchCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnmatchCommand) other).targetIndex) // state check
                && Objects.equals(this.pairToUnmatch, ((UnmatchCommand) other).pairToUnmatch));
    }
}
```
###### \java\seedu\address\logic\parser\MatchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns an MatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        String[] indices = trimmedArgs.split("\\s+");
        if (indices.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }
        Index indexA = null; // index of the first person
        Index indexB = null; // index of the second person
        try {
            indexA = ParserUtil.parseIndex(indices[0]);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        try {
            indexB = ParserUtil.parseIndex(indices[1]);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        return new MatchCommand(indexA, indexB);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String pariHash} into a {@code PairHash}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code  pairHash} is invalid.
     */
    public static PairHash parsePairHash(String pairHash) throws IllegalValueException {
        requireNonNull(pairHash);
        String trimmedPairHash = pairHash.trim();
        if (!PairHash.isValidPairHashValue(trimmedPairHash)) {
            throw new IllegalValueException(PairHash.MESSAGE_PAIRHASH_CONSTRAINTS);
        }
        return new PairHash(trimmedPairHash);
    }


```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code Collection<String> pairHashes} into a {@code Set<PairHash>}.
     */
    public static Set<PairHash> parsePairHashes(Collection<String> pairHashes) throws IllegalValueException {
        requireNonNull(pairHashes);
        final Set<PairHash> pairHashSet = new HashSet<>();
        for (String pairHashValue : pairHashes) {
            pairHashSet.add(parsePairHash(pairHashValue));
        }
        return pairHashSet;
    }


```
###### \java\seedu\address\logic\parser\UnmatchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnmatchCommand object
 */
public class UnmatchCommandParser implements Parser<UnmatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmatchCommand
     * and returns an UnmatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmatchCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnmatchCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //I added an extra checking to prevent updating of person details is the person is matched
    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     * Only applicable for Add and Edit
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePersonForAddAndEdit(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException, PersonMatchedCannotEditException {
        requireNonNull(editedPerson);
        boolean isChanged = !target.getName().equals(editedPerson.getName())
                || !target.getRole().equals(editedPerson.getRole())
                || !target.getSubject().equals(editedPerson.getSubject())
                || !target.getPrice().equals(editedPerson.getPrice())
                || !target.getLevel().equals(editedPerson.getLevel())
                || !target.getStatus().equals(editedPerson.getStatus());
        if (target.isMatched() && isChanged) {
            throw new PersonMatchedCannotEditException();
        }
        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
        removeUnusedTags();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     * Only applicable for Add and Edit
     * @param target
     * @param editedPerson
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    public void updatePersonForMatchUnmatch(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);
        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
        removeUnusedTags();
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a pair to the address book.
     * @param key
     * @throws seedu.address.model.pair.exceptions.DuplicatePairException if an equivalent pair already exists.
     */
    public void addPair(Pair key) throws DuplicatePairException {
        pairs.add(key);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a pair to the address book, and store pairHash to the Student and Tutor involved
     * @param student
     * @param tutor
     * @throws seedu.address.model.pair.exceptions.DuplicatePairException if an equivalent pair already exists.
     */
    public void addPair(Person student, Person tutor) throws DuplicatePairException {
        Pair key = new Pair(student, tutor, student.getSubject(), student.getLevel(), student.getPrice());
        pairs.add(key);
        PairHash pairHash = key.getPairHash();
        addPairHash(student, pairHash);
        addPairHash(tutor, pairHash);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws seedu.address.model.pair.exceptions.PairNotFoundException if the {@code key} is not in this
     * {@code AddressBook}.gr
     */
    public boolean removePair(Pair key) throws PairNotFoundException {
        if (pairs.remove(key)) {
            PairHash pairHash = key.getPairHash();
            for (Person person : persons) {
                if (person.containsPairHash(pairHash)) {
                    removePairHash(person, pairHash);
                }
            }
            return true;
        } else {
            throw new PairNotFoundException();
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * add pairHash to be the person's list of pairHashes
     * @param person
     * @param pairHash
     */
    private void addPairHash(Person person, PairHash pairHash) {
        Person editedPerson;
        Set<PairHash> pairHashSet = new HashSet<PairHash>();
        pairHashSet.addAll(person.getPairHashes());
        if (!pairHashSet.contains(pairHash)) {
            pairHashSet.add(pairHash);
        }

        //update status to Matched if the person's original pairHash List is empty
        Set<Tag> attributeTags = new HashSet<Tag>();
        attributeTags.add(new Tag(person.getRole().value, Tag.AllTagTypes.ROLE));
        attributeTags.add(new Tag(person.getPrice().value, Tag.AllTagTypes.PRICE));
        attributeTags.add(new Tag(person.getSubject().value, Tag.AllTagTypes.SUBJECT));
        attributeTags.add(new Tag(person.getLevel().value, Tag.AllTagTypes.LEVEL));

        attributeTags.add(new Tag("Matched", Tag.AllTagTypes.STATUS));
        editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Matched"),
                    person.getRole(), attributeTags, person.getRemark(), person.getRate(), pairHashSet);
        try {
            updatePersonForAddAndEdit(person, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Should not have duplicates");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("Match exits means person must be in database.");
        } catch (PersonMatchedCannotEditException e) {
            throw new AssertionError("Match should not result in edit exception");
        }

    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * remove pairHash from the person
     * @param person
     * @param pairHash
     */
    private void removePairHash(Person person, PairHash pairHash) {
        Person editedPerson;
        Set<PairHash> pairHashSet = new HashSet<PairHash>();
        pairHashSet.addAll(person.getPairHashes());
        if (pairHashSet.contains(pairHash)) {
            pairHashSet.remove(pairHash);
        }

        Set<Tag> attributeTags = new HashSet<Tag>();
        attributeTags.add(new Tag(person.getRole().value, Tag.AllTagTypes.ROLE));
        attributeTags.add(new Tag(person.getPrice().value, Tag.AllTagTypes.PRICE));
        attributeTags.add(new Tag(person.getSubject().value, Tag.AllTagTypes.SUBJECT));
        attributeTags.add(new Tag(person.getLevel().value, Tag.AllTagTypes.LEVEL));

        //update status to not Matched if the person's pairHash List is empty after removal
        if (pairHashSet.isEmpty()) {
            attributeTags.add(new Tag("Not Matched", Tag.AllTagTypes.STATUS));
            editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Not Matched"),
                    person.getRole(), attributeTags, person.getRemark(), person.getRate(), pairHashSet);
        } else {
            attributeTags.add(new Tag("Matched", Tag.AllTagTypes.STATUS));
            editedPerson = new Person(person.getName(), person.getPhone(),
                    person.getEmail(), person.getAddress(), person.getPrice(),
                    person.getSubject(), person.getLevel(), new Status("Matched"),
                    person.getRole(), attributeTags, person.getRemark(), person.getRate(), pairHashSet);
        }

        try {
            updatePersonForMatchUnmatch(person, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Should not have duplicates");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("Match exits means person must be in database.");
        }
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deletePair(Pair target) throws PairNotFoundException {
        addressBook.removePair(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Add a pair to STUtor
     * @param student
     * @param tutor
     * @throws DuplicatePersonException
     */
    public synchronized void addPair(Person student, Person tutor) throws DuplicatePairException {
        addressBook.addPair(student, tutor);
        updateFilteredPairList(PREDICATE_SHOW_ALL_PAIRS);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

```
###### \java\seedu\address\model\pair\exceptions\DuplicatePairException.java
``` java
/**
 * Signals that the operation will result in duplicate Pair objects.
 */
public class DuplicatePairException extends DuplicateDataException {
    public DuplicatePairException() {
        super("Operation would result in duplicate pairs");
    }
}
```
###### \java\seedu\address\model\pair\exceptions\PairNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified pair.
 */
public class PairNotFoundException extends Exception {}
```
###### \java\seedu\address\model\pair\Pair.java
``` java
/**
 * Represents a pair (one student and one tutor) in STUtor.
 */
public class Pair  {

    private final String studentName;
    private final String tutorName;
    private final String subject;
    private final String level;
    private final String price;
    private final PairHash pairHash;
    private final UniqueTagList tags;


    public Pair(String studentName, String tutorName, String subject, String level, String price,
                PairHash pairHash) {
        requireAllNonNull(studentName, tutorName, subject, level, price, pairHash);
        this.studentName = studentName;
        this.tutorName = tutorName;
        this.subject = subject;
        this.level = level;
        this.price = price;
        this.tags = new UniqueTagList();
        try {
            tags.add(new Tag(price, Tag.AllTagTypes.PRICE));
            tags.add(new Tag(subject, Tag.AllTagTypes.SUBJECT));
            tags.add(new Tag(level, Tag.AllTagTypes.LEVEL));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new AssertionError("AddressBooks should not have duplicate tags from pair.");
        }
        this.pairHash = pairHash;
    }

    public Pair(Person student, Person tutor, Subject subject, Level level, Price price) {
        this(student.getName().fullName, tutor.getName().fullName, subject.value,
                level.value, price.value, new PairHash(student, tutor, subject, level, price));
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getSubject() {
        return subject;
    }

    public String getLevel() {
        return level;
    }

    public String getPrice() {
        return price;
    }

    public String getPairName() {
        return studentName + " & " + tutorName;
    }

    public PairHash getPairHash() {
        return pairHash;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
    */
    public List<Tag> getTags() {
        Set<Tag> setTags = tags.toSet();
        List<Tag> tagsAsList = new ArrayList<>(setTags);
        Collections.sort(tagsAsList);
        return Collections.unmodifiableList(tagsAsList);
    }

    /**
     * return a hashcode of the Pair object
     * @return
     */
    /*  public Pair hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return pairHash;
    }
    */

    /**
     * check if another object is equal to this pair
     * @param other
     * @return
    */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Pair)) {
            return false;
        }


        Pair otherPair = (Pair) other;
        return otherPair.getStudentName().equals(this.getStudentName())
                && otherPair.getTutorName().equals(this.getTutorName())
                && otherPair.getSubject().equals(this.getSubject())
                & otherPair.getLevel().equals(this.getLevel())
                && otherPair.getPrice().equals(this.getPrice())
                && otherPair.getPairHash().equals(this.getPairHash());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Student: ")
                .append(getStudentName())
                .append(" Tutor: ")
                .append(getTutorName())
                .append(" Subject: ")
                .append(getSubject())
                .append(" Level: ")
                .append(getLevel())
                .append(" Price: ")
                .append(getPrice());
        return builder.toString();
    }


}
```
###### \java\seedu\address\model\pair\PairHash.java
``` java
/**
 * Represents a PairHash in the address book.
 * The PairHash is attached to a pair, its student and its tutor to facilitate match/unmatch operations.
 * Guarantees: immutable;
 */
public class PairHash {

    public static final String PAIRHASH_VALIDATION_REGEX = "-?[0-9]{0,10}";
    public static final String MESSAGE_PAIRHASH_CONSTRAINTS = "PairHash should be a signed integer.";

    public static final PairHash DEFAULT_PAIR_HASH = new PairHash(0);
    public final int value;


    public PairHash (Person student, Person tutor, Subject subject, Level level, Price price) {
        this(student.toString(), tutor.toString(), subject, level, price);
    }

    //toString() is used instead of the Person object itself to prevent duplicate pairs
    //(Person stores a list of pairHashes, direct hashing with the person object will give different hashes but
    //essentially adding duplicates to the UniquePairList in model
    public PairHash (String studentDescription, String tutorDescription, Subject subject, Level level, Price price) {
        requireAllNonNull(studentDescription, tutorDescription, subject, level, price);
        this.value = Objects.hash(studentDescription, tutorDescription, subject, level, price);
    }

    public PairHash(String input) {
        this.value = Integer.parseInt(input);
    }

    public PairHash(int input) {
        this.value = input;
    }

    public static Set<PairHash> getDefaultPairHashSet() {
        Set<PairHash> defaultPairHashSet = new HashSet<PairHash>();
        return defaultPairHashSet;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PairHash // instanceof handles nulls
                && this.value == (((PairHash) other).value)); // state check
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Integer.toString(value).hashCode();
    }


    /**
     * Returns true if a given string is a valid pairHash
     */
    public static boolean isValidPairHashValue(String test) {
        return test.matches(PAIRHASH_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\pair\UniquePairHashList.java
``` java
/**
 * A list of pairHashs that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see PairHash#equals(Object)
 */
public class UniquePairHashList implements Iterable<PairHash> {

    private final ObservableList<PairHash> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PairHashList.
     */
    public UniquePairHashList() {}

    /**
     * Creates a UniquePairHashList using given pairHashs.
     * Enforces no nulls.
     */
    public UniquePairHashList(Set<PairHash> pairHashs) {
        requireAllNonNull(pairHashs);
        internalList.addAll(pairHashs);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all pairHashs in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<PairHash> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns true if the list contains an equivalent PairHash as the given argument.
     */
    public boolean contains(PairHash toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a PairHash to the list.
     *
     * @throws DuplicatePairHashException if the PairHash to add is a duplicate of an existing PairHash in the list.
     */
    public void add(PairHash toAdd) throws DuplicatePairHashException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePairHashException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<PairHash> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PairHash> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePairHashList // instanceof handles nulls
                && this.internalList.equals(((UniquePairHashList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePairHashException extends DuplicateDataException {
        protected DuplicatePairHashException() {
            super("Operation would result in duplicate pairHashs");
        }
    }

}
```
###### \java\seedu\address\model\pair\UniquePairList.java
``` java
/**
 * A list of pairs that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Pair#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePairList implements Iterable<Pair> {

    private final ObservableList<Pair> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent pair as the given argument.
     */
    public boolean contains(Pair toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a pair to the list.
     *
     * @throws DuplicatePairException if the pair to add is a duplicate of an existing pair in the list.
     */
    public void add(Pair toAdd) throws DuplicatePairException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePairException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the pair {@code target} in the list with {@code editedPair}.
     *
     * @throws DuplicatePairException if the replacement is equivalent to another existing pair in the list.
     * @throws PairNotFoundException if {@code target} could not be found in the list.
     */
    public void setPair(Pair target, Pair editedPair)
            throws DuplicatePairException, PairNotFoundException {
        requireNonNull(editedPair);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PairNotFoundException();
        }

        if (!target.equals(editedPair) && internalList.contains(editedPair)) {
            throw new DuplicatePairException();
        }

        internalList.set(index, editedPair);
    }

    /**
     * Removes the equivalent pair from the list.
     *
     * @throws PairNotFoundException if no such pair could be found in the list.
     */

    public boolean remove(Pair toRemove) throws PairNotFoundException {
        requireNonNull(toRemove);
        final boolean pairFoundAndDeleted = internalList.remove(toRemove);
        if (!pairFoundAndDeleted) {
            throw new PairNotFoundException();
        }
        return pairFoundAndDeleted;
    }


    public void setPairs(UniquePairList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPairs(List<Pair> pairs) throws DuplicatePairException {
        requireAllNonNull(pairs);
        final UniquePairList replacement = new UniquePairList();
        for (final Pair pair : pairs) {
            replacement.add(pair);
        }
        setPairs(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Pair> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Pair> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePairList // instanceof handles nulls
                && this.internalList.equals(((UniquePairList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\person\exceptions\PersonMatchedCannotDeleteException.java
``` java
/**
 * Signals that the operation is invalid as the person is matched and cannot be deleted
 */
public class PersonMatchedCannotDeleteException extends Exception {
}
```
###### \java\seedu\address\model\person\exceptions\PersonMatchedCannotEditException.java
``` java
/**
 * Signals that the operation is invalid as the person is matched and
 * his status, subject, level and role cannot be changed
 */
public class PersonMatchedCannotEditException extends Exception {
}
```
###### \java\seedu\address\model\person\Level.java
``` java
/**
 * Represents a Person's level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLevel(String)}
 */
public class Level {

    public static final String[] LEVEL_VALUES =
            new String[] { "lower sec", "ls", "upper sec", "us", "lower pri", "lp", "upper pri", "up", ""};
    public static final HashSet<String> SET_ALL_LEVEL = new HashSet<>(Arrays.asList(LEVEL_VALUES));

    public static final String MESSAGE_LEVEL_CONSTRAINTS = "Person Level should be "
            + "of the format <grade><education> "
            + "and adhere to the following constraints:\n"
            + "1. The education should be one of the education system listed in.\n"
            + "2. This is followed by a whitespace and then a number to represent the grade. "
            + "The grade must be consistent with the specific education system indicated earlier.\n";

    public final String value;

    /**
     * Constructs an {@code Level}.
     * @param level A valid level description.
     */
    public Level(String level) {
        requireNonNull(level);
        level = validateLevel(level);
        this.value = formatLevel(level);
    }


    /**
     * format level into proper case
     * @param level
     * @return String representing level in proper case
     */
    private String formatLevel(String level) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(level);
    }


    /**
     * check validity of the level string supplied
     * @param level
     * @return String representing a valid level
     */
    private String validateLevel(String level) {
        level.toLowerCase();
        checkArgument(isValidLevel(level), MESSAGE_LEVEL_CONSTRAINTS);
        level = convertToFullLevel(level);
        return level;
    }


    /**
     * Convert a shortcut to full level name
     * @param original
     * @return String representing the full level
     */
    public String convertToFullLevel(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("ls")) {
            cur = "lower sec";
        } else if (cur.equals("us")) {
            cur = "upper sec";
        } else if (cur.equals("lp")) {
            cur = "lower pri";
        } else if (cur.equals("up")) {
            cur = "upper pri";
        }
        return cur;
    }

    /**
     * Returns if a given string is a valid level description.
     */
    public static boolean isValidLevel(String test) {
        test = test.toLowerCase();
        return SET_ALL_LEVEL.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Level // instanceof handles nulls
                && this.value.equals(((Level) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }


        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(this.getName())
                && otherPerson.getPhone().equals(this.getPhone())
                && otherPerson.getEmail().equals(this.getEmail())
                && otherPerson.getAddress().equals(this.getAddress())
                && otherPerson.getLevel().equals(this.getLevel())
                && otherPerson.getSubject().equals(this.getSubject())
                && otherPerson.getPrice().equals(this.getPrice())
                && otherPerson.getRole().equals(this.getRole());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, price, subject, level, status, role, tags,
                            remark, rate, pairHashes);
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
                .append(getAddress());
        return builder.toString();
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    public boolean isMatched() {
        return (getStatus().value.equals("Matched"));
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    public boolean containsPairHash(PairHash pairHash) {
        return (pairHashes.contains(pairHash));
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Check if a person has missing fields
     * @return
     */
    public boolean hasMissingFieldForMatch() {
        return this.getSubject().value.equals("")
                || this.getLevel().value.equals("")
                || this.getRole().value.equals("")
                || this.getPrice().value.equals("");
    }
}
```
###### \java\seedu\address\model\person\ProperCaseConverter.java
``` java
/**
 * Helper class to change a String to proper class ("This Is An Example Of Proper Case.")
 */
class ProperCaseConverter {

    /**
     * convert the value string to proper case
     */
    public String convertToProperCase(String original) {
        StringBuilder properCase = new StringBuilder();
        boolean nextProperCase = true;

        original = original.toLowerCase();

        for (char c : original.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextProperCase = true;
            } else if (nextProperCase) {
                c = Character.toUpperCase(c);
                nextProperCase = false;
            }
            properCase.append(c);
        }
        return properCase.toString();
    }
}
```
###### \java\seedu\address\model\person\Role.java
``` java
/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String[] ROLES_VALUES = new String[] { "student", "s", "tutor", "t", ""};
    public static final HashSet<String> SET_ALL_ROLES = new HashSet<>(Arrays.asList(ROLES_VALUES));

    public static final String MESSAGE_ROLE_CONSTRAINTS = "Role should be one of: \n"
            + SET_ALL_ROLES.toString()
            + "\n";

    public final String value;

    /**
     * Constructs an {@code Role}.
     *
     * @param role A valid role description.
     */
    public Role(String role) {
        requireNonNull(role);
        role = validateRole(role);
        this.value = formatRole(role);
    }


    /**
     * Format the input into proper case
     * @param role
     * @return
     */
    private String formatRole(String role) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(role);
    }


    /**
     * check validity of the status string supplied
     * @param role
     * @return string representing a valid role
     */
    private String validateRole(String role) {
        role.toLowerCase();
        checkArgument(isValidRole(role), MESSAGE_ROLE_CONSTRAINTS);
        role = convertToFullRole(role);
        return role;
    }

    /**
     * Convert a shortcut to full role name
     */
    public String convertToFullRole(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("s")) {
            cur = "student";
        } else if (cur.equals("t")) {
            cur = "tutor";
        }
        return cur;
    }


    /**
     * Returns if a given string is a valid role description.
     */
    public static boolean isValidRole(String test) {
        test = test.toLowerCase();
        return SET_ALL_ROLES.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Role // instanceof handles nulls
                && this.value.equals(((Role) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
```
###### \java\seedu\address\model\person\Status.java
``` java
/**
 * Represents a Person's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String[] STATUS_VALUES = new String[] { "matched", "m", "not matched", "nm", ""};
    public static final HashSet<String> SET_ALL_STATUS = new HashSet<>(Arrays.asList(STATUS_VALUES));

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status should be one of: \n"
            + SET_ALL_STATUS.toString()
            + "\n";
    public static final Status DEFAULT_STATUS = new Status ("Not Matched");

    public final String value;

    /**
     * Constructs an {@code Status}.
     *
     * @param status A valid statust description.
     */
    public Status(String status) {
        requireNonNull(status);
        status = validateStatus(status);
        this.value = formatStatus(status);
    }


    /**
     * format the input into proper case
     * @param status
     * @return
     */
    private String formatStatus(String status) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(status);
    }


    /**
     * check validity of the status string supplied
     * @param status
     * @return string representing a valid status
     */
    private String validateStatus(String status) {
        status.toLowerCase();
        checkArgument(isValidStatus(status), MESSAGE_STATUS_CONSTRAINTS);
        status = convertToFullStatus(status);
        return status;
    }


    /**
     * Convert a shortcut to full status name
     */
    public String convertToFullStatus(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("nm")) {
            cur = "not matched";
        } else if (cur.equals("m")) {
            cur = "matched";
        }
        return cur;
    }


    /**
     * Returns if a given string is a valid status description.
     */
    public static boolean isValidStatus(String test) {
        test = test.toLowerCase();
        return SET_ALL_STATUS.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Student.java
``` java
/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Student extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param price
     * @param subject
     * @param level
     * @param status
     * @param remark
     * @param rate
     * @param tags
     * @param pairhashes
     */
    public Student(Name name, Phone phone, Email email, Address address,
                   Price price, Subject subject, Level level, Status status,
                   Set<Tag> tags, Remark remark, Rate rate,  Set<PairHash> pairhashes) {
        super(name, phone, email, address, price, subject, level, status, new Role("student"),
              tags, remark, rate, pairhashes);
    }
}
```
###### \java\seedu\address\model\person\Subject.java
``` java
/**
 * Represents a Person's subject in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String[] SUBJECT_VALUES =
            new String[]{"english", "eng", "chinese", "chi", "math", "physics", "phy", "chemistry", "chem", ""};
    public static final HashSet<String> SET_ALL_SUBJECT = new HashSet<>(Arrays.asList(SUBJECT_VALUES));

    public static final String MESSAGE_SUBJECT_CONSTRAINTS = "Subject should be one of: \n"
            + SET_ALL_SUBJECT.toString()
            + "\n";

    public final String value;

    /**
     * Constructs an {@code Subject}.
     *
     * @param subject A valid subject description.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        subject = validateSubject(subject);
        this.value = formatsubject(subject);
    }

    private String formatsubject(String subject) {
        ProperCaseConverter pc = new ProperCaseConverter();
        return pc.convertToProperCase(subject);
    }


    /**
     * check validity of the subject string supplied
     * @param subject
     * @return string representing a valid subject
     */
    private String validateSubject(String subject) {

        subject.toLowerCase();
        checkArgument(isValidSubject(subject), MESSAGE_SUBJECT_CONSTRAINTS);
        subject = convertToFullSubject(subject);
        return subject;
    }

    /**
     * Convert a shortcut to full subject name
     */
    public String convertToFullSubject(String original) {
        String cur = original.toLowerCase();
        if (cur.equals("eng")) {
            cur = "english";
        } else if (cur.equals("chi")) {
            cur = "chinese";
        } else if (cur.equals("phy")) {
            cur = "physics";
        } else if (cur.equals("chem")) {
            cur = "chemistry";
        }
        return cur;
    }


    /**
     * Returns if a given string is a valid subject description.
     */
    public static boolean isValidSubject(String test) {
        test = test.toLowerCase();
        return SET_ALL_SUBJECT.contains(test);
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
###### \java\seedu\address\model\person\Tutor.java
``` java
/**
 * Represents a Tutor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Tutor extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param price
     * @param subject
     * @param level
     * @param status
     * @param remark
     * @param rate
     * @param tags
     * @param pairhashes
     */
    public Tutor(Name name, Phone phone, Email email, Address address, Price price,
                 Subject subject, Level level, Status status, Set<Tag> tags, Remark remark,
                 Rate rate, Set<PairHash> pairhashes) {
        super(name, phone, email, address, price, subject, level, status, new Role("student"),
              tags, remark, rate, pairhashes);
    }
}
```
