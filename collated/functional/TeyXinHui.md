# TeyXinHui
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person selectedPerson = lastShownList.get(targetIndex.getZeroBased());
        StringBuilder result = new StringBuilder();

        if (selectedPerson.getSubjects().size() < 6) {
            throw new CommandException(Messages.MESSAGE_INSUFFICIENT_SUBJECTS);
        }
        try {
            score = selectedPerson.calculateL1R5();
        } catch (InvalidSubjectCombinationException isce) {
            return new CommandResult("Please check that you have at least 1 subject in each L1R5 "
                    + "category.\n"  + "Please use edit to change subjects allocated to student.");
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(result.append(String.format(MESSAGE_SELECT_PERSON_SUCCESS, selectedPerson.getName()))
                .append(String.format(MESSAGE_L1R5_SUCCESS, score)).toString());

    }

```
###### \java\seedu\address\logic\commands\TagDeleteCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class TagDeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tagdelete";
    public static final String COMMAND_ALIAS = "td";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified tag from everyone in the "
            + "in the address book.\n"
            + "Parameters: Tag \n"
            + "Example: " + COMMAND_WORD + " 1A\n"
            + "Example: " + COMMAND_ALIAS + " 1A";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";

    private Tag tagToDelete;

    public TagDeleteCommand(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tagToDelete);
        try {
            model.deleteTag(tagToDelete);
        } catch (TagNotFoundException error) {
            throw new AssertionError("The target tag cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Tag> lastShownList = addressBook.getTagList();

        if (!lastShownList.contains(tagToDelete)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_ENTERED);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagDeleteCommand // instanceof handles nulls
                && this.tagToDelete.equals(((TagDeleteCommand) other).tagToDelete)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\TagDeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class TagDeleteCommandParser implements Parser<TagDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagDeleteCommand
     * and returns an TagDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagDeleteCommand parse(String args) throws ParseException {
        try {
            Tag tag = ParserUtil.parseTag(args.trim());
            return new TagDeleteCommand(tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagDeleteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addSubject(Subject s) throws UniqueSubjectList.DuplicateSubjectException {
        subjects.add(s);
    }

    /**
     * Calls removeTagFromPerson method when tag is found in tags.
     * @param t
     * @throws TagNotFoundException
     */
    public void removeTag(Tag t) throws TagNotFoundException {
        if (tags.contains(t)) {
            for (Person person : persons) {
                removeTagFromPerson(t, person);
            }
        } else {
            throw new TagNotFoundException("Specific tag is not used in the address book.");
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes a specific tag from an individual person and updates the person's information.
     * Person needs to have the specific tag in his/her tag list.
     * @param tag
     * @param person
     */
    public void removeTagFromPerson(Tag tag, Person person) {
        Set<Tag> tagList = new HashSet<>(person.getTags());
        if (tagList.remove(tag)) {
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

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Removes a specific tag from everyone in the address book.
     * @param tag
     * @throws TagNotFoundException
     */
    void deleteTag(Tag tag) throws TagNotFoundException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteTag(Tag tag) {
        try {
            addressBook.removeTag(tag);
        } catch (TagNotFoundException error) {
            throw new AssertionError();
        }
    }

```
###### \java\seedu\address\model\person\exceptions\InvalidSubjectCombinationException.java
``` java
/**
 * Signals that the student did not have a valid subject combination entered.
 */
public class InvalidSubjectCombinationException extends Exception {
    public InvalidSubjectCombinationException(String message) {
        super(message);
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Calculates the lowest possible score from the grades of the subjects of the selected person.
     * @return L1R5 score
     */
    public int calculateL1R5() throws InvalidSubjectCombinationException {
        int score = 0;
        Set<Subject> subjects = new HashSet<>(this.getSubjects());
        Set<Subject> subjectsToCheck = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            for (Subject subject: subjects) {
                switch (i) {
                // Check if the subject is a L1 subject
                case 0:
                    if (Arrays.asList(Subject.L1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R1 subject
                case 1:
                    if (Arrays.asList(Subject.R1_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R2 subject
                case 2:
                    if (Arrays.asList(Subject.R2_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R3 subject
                case 3:
                    if (Arrays.asList(Subject.R3_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                // Check if the subject is a R4 or R5 subject
                case 4:
                case 5:
                    if (Arrays.asList(Subject.R4_R5_SUBJECT).contains(subject.subjectName)) {
                        subjectsToCheck.add(subject);
                    }
                    break;
                default:
                    break;
                }
            }
            // Check that if the student has at least one subject in each L1R5 category, else return error message
            if (checkLowest(subjectsToCheck, subjects) == 10) {
                throw new InvalidSubjectCombinationException("Subjects taken do not fulfil the L1R5 requirements.");
            } else {
                score += checkLowest(subjectsToCheck, subjects);
            }
            subjectsToCheck.clear();
        }
        return score;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Takes in a set of subjects under the category of L1 or R5 and find the smallest grade score.
     * Removes the best subject from the full list of subjects of the student to prevent the same subject being
     * considered in the L1R5 score more than once.
     * @param subjectsToCheck
     * @return lowest grade score
     */
    public static int checkLowest(Set<Subject> subjectsToCheck, Set<Subject> subjects) {
        int lowest = 10;
        Subject bestSubject = new Subject();
        for (Subject subject: subjectsToCheck) {
            if (Character.getNumericValue(subject.subjectGrade.charAt(1)) < lowest) {
                lowest = Character.getNumericValue(subject.subjectGrade.charAt(1));
                bestSubject = subject;
            }
        }
        subjects.remove(bestSubject);
        return lowest;
    }

```
###### \java\seedu\address\model\subject\Subject.java
``` java
/**
 * Represents a subject in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Subject {

    public static final String[] SUBJECT_NAME = new String[] {"English", "Chinese", "HChi", "CSP",
                                                              "ChiB", "French", "German", "Spanish", "Hindi",
                                                              "Urdu", "Gujarati", "Panjabi", "Bengali", "Burmese",
                                                              "Thai", "Jap", "BIndo", "Tamil",
                                                              "HTamil", "TamilB", "Malay", "HMalay", "MalayB",
                                                              "MSP", "EMath", "AMath", "Phy", "Chem", "Bio", "Sci",
                                                              "Hist", "Geog", "ComHum", "ELit", "CLit", "MLit", "TLit",
                                                              "Music", "HMusic", "Art", "HArt", "DnT", "Comp",
                                                              "FnN", "PoA", "Econs", "Drama", "PE", "Biz", "Biotech",
                                                              "Design"};
    public static final String MESSAGE_SUBJECT_NAME_CONSTRAINTS = "Subject names should be alphabetic and should be "
            + "one of the following: \n" + Arrays.toString(Arrays.copyOfRange(SUBJECT_NAME, 0, 9)) + "\n"
            + Arrays.toString(Arrays.copyOfRange(SUBJECT_NAME, 10, 19)) + "\n"
            + Arrays.toString(Arrays.copyOfRange(SUBJECT_NAME, 20, 29)) + "\n"
            + Arrays.toString(Arrays.copyOfRange(SUBJECT_NAME, 30, 39)) + "\n"
            + Arrays.toString(Arrays.copyOfRange(SUBJECT_NAME, 40, SUBJECT_NAME.length)) + ".";
    public static final String[] SUBJECT_GRADE = new String[] {"A1", "A2", "B3", "B4", "C5", "C6", "D7", "E8", "F9"};

    public static final String MESSAGE_SUBJECT_GRADE_CONSTRAINTS = "Subject grade should be alphanumeric and should be"
            + " one of the following: \n" + Arrays.deepToString(SUBJECT_GRADE) + ".";

    // Use for the calculation of the L1R5 subjects
    public static final String[] L1_SUBJECT = {"English", "HChi", "HTamil", "HMalay"};
    public static final String[] R1_SUBJECT = {"Hist", "Geog", "Com.Hum",  "ELit", "CLit", "MLit", "TLit", "HArt",
                                               "HMusic", "BIndo", "CSP", "MSP"};
    public static final String[] R2_SUBJECT = {"EMath", "AMath", "Phy", "Chem", "Bio", "Sci"};
    public static final String[] R3_SUBJECT = {"Hist", "Geog", "ComHum",  "ELit", "CLit", "MLit", "TLit", "HArt",
                                               "H.Music", "BIndo", "CSP", "MSP", "EMath", "AMath", "Phy", "Chem",
                                               "Bio", "Sci"};
    public static final String[] R4_R5_SUBJECT = {"English", "Chinese", "HChi", "CSP", "French", "German", "Spanish",
                                                  "Hindi", "Urdu", "Gujarati", "Panjabi", "Bengali", "Burmese",
                                                  "Thai", "Jap", "BIndo", "Tamil", "HTamil", "Malay", "HMalay",
                                                  "MSP", "EMath", "AMath", "Phy", "Chem", "Bio", "Sci", "Hist", "Geog",
                                                  "ComHum", "ELit", "CLit", "MLit", "TLit", "Music", "HMusic", "Art",
                                                  "HArt", "DnT", "Comp", "FnN", "PoA", "Econs", "Drama", "PE",
                                                  "Biz", "Biotech", "Design"};


    public final String subjectName;
    public final String subjectGrade;

    /**
     * Default constructor of Subject Object
     */
    public Subject() {
        this.subjectName = "";
        this.subjectGrade = "";
    }

    /**
     * Constructs a {@code Subject}.
     *
     * @param subjectName  A valid subject name.
     * @param subjectGrade A valid subject grade.
     */
    public Subject(String subjectName, String subjectGrade) {
        requireNonNull(subjectName);
        checkArgument(isValidSubjectName(subjectName), MESSAGE_SUBJECT_NAME_CONSTRAINTS);
        this.subjectName = subjectName;

        requireNonNull(subjectGrade);
        checkArgument(isValidSubjectGrade(subjectGrade), MESSAGE_SUBJECT_GRADE_CONSTRAINTS);
        this.subjectGrade = subjectGrade;
    }

    /**
     * Constructs a {@code Subject} by splitting the subject string into {@code subjectName}.
     *
     * @param subject A valid subject string.
     */
    public Subject(String subject) {
        requireNonNull(subject);
        String[] splitSubjectStr = subject.trim().split("\\s+");
        String subjectName = splitSubjectStr[0];
        String subjectGrade = splitSubjectStr[1];
        this.subjectName = subjectName;
        this.subjectGrade = subjectGrade;
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubjectName(String test) {
        for (String validSubjectName : SUBJECT_NAME) {
            if (test.equals(validSubjectName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given string is a valid subject grade.
     */

    public static boolean isValidSubjectGrade(String test) {
        for (String validSubjectGrade : SUBJECT_GRADE) {
            if (test.equals(validSubjectGrade)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        //return other == this // short circuit if same object
        //        || (other instanceof Subject // instanceof handles nulls
        //        && this.subjectName.equals(((Subject) other).subjectName)); // state check
        if (other == null) {
            return false;
        }
        if (this == other) { //same object
            return true;
        }
        if (!(other instanceof Subject)) {
            return false;
        }
        Subject object = (Subject) other;
        return this.subjectName == object.subjectName && this.subjectGrade == object.subjectGrade;
    }

    @Override
    public int hashCode() {
        //return subjectName.hashCode() && subjectGrade.hashCode();
        int hash = 17;
        hash = 37 * hash + subjectName.hashCode();
        hash = 37 * hash + subjectGrade.hashCode();
        return hash;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + subjectName + ' ' + subjectGrade + ']';
    }
```
###### \java\seedu\address\model\subject\UniqueSubjectList.java
``` java
/**
 * A list of subjects that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Subject#equals(Object)
 */
public class UniqueSubjectList implements Iterable<Subject> {

    private final ObservableList<Subject> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty Subject List.
     */
    public UniqueSubjectList() {}

    /**
     * Creates a UniqueSubjectList using given subjects.
     * Enforces no nulls.
     */
    public UniqueSubjectList(Set<Subject> subjects) {
        requireAllNonNull(subjects);
        internalList.addAll(subjects);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Subjects in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Subject> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Subjects in this list with those in the argument subject list.
     */
    public void setSubjects(Set<Subject> subjects) {
        requireAllNonNull(subjects);
        internalList.setAll(subjects);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every subject in the argument list exists in this object.
     */
    public void mergeFrom(UniqueSubjectList from) {
        final Set<Subject> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(subject -> !alreadyInside.contains(subject))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Subject as the given argument.
     */
    public boolean contains(Subject toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Subject to the list.
     *
     * @throws DuplicateSubjectException if the Tag to add is a duplicate of an existing Subject in the list.
     */
    public void add(Subject toAdd) throws DuplicateSubjectException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSubjectException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Subject> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Subject> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueSubjectList // instanceof handles nulls
                && this.internalList.equals(((UniqueSubjectList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueSubjectList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateSubjectException extends DuplicateDataException {
        protected DuplicateSubjectException() {
            super("Operation would result in duplicate subject");
        }
    }

}
```
###### \java\seedu\address\model\tag\exceptions\TagNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException(String message) {
        super(message);
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a subject set containing the list of strings given.
     */
    public static Set<Subject> getSubjectSet(String... strings) {
        HashSet<Subject> subjects = new HashSet<>();
        for (String s : strings) {
            subjects.add(new Subject(s));
        }

        return subjects;
    }
```
###### \java\seedu\address\storage\XmlAdaptedSubject.java
``` java
/**
 *
 */
public class XmlAdaptedSubject {

    @XmlElement
    private String subjectName;
    @XmlElement
    private String subjectGrade;

    /**
     * Constructs an XmlAdaptedSubject.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSubject() {}

    /**
     * Constructs a {@code XmlAdaptedSubject} with the given {@code subject}.
     */
    public XmlAdaptedSubject(String subject) {
        String[] splitSubjectStr = subject.trim().split("\\s+");
        String subjectName = splitSubjectStr[0];
        String subjectGrade = splitSubjectStr[1];
        this.subjectName = subjectName;
        this.subjectGrade = subjectGrade;
    }

    /**
     * Converts a given Subject into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSubject(Subject source) {
        subjectName = source.subjectName;
        subjectGrade = source.subjectGrade;
    }

    /**
     * Converts this jaxb-friendly adapted subject object into the model's subject object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Subject toModelType() throws IllegalValueException {
        if (!Subject.isValidSubjectName(subjectName)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_NAME_CONSTRAINTS);
        }
        if (!Subject.isValidSubjectGrade(subjectGrade)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_GRADE_CONSTRAINTS);
        }
        return new Subject(subjectName, subjectGrade);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) { //same object
            return true;
        }
        if (!(other instanceof XmlAdaptedSubject)) {
            return false;
        }
        XmlAdaptedSubject object = (XmlAdaptedSubject) other;
        return this.subjectName == object.subjectName && this.subjectGrade == object.subjectGrade;
    }
}
```
