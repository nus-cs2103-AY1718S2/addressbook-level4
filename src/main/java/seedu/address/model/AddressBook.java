package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.PairHash;
import seedu.address.model.pair.UniquePairList;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.pair.exceptions.PairNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonMatchedCannotDeleteException;
import seedu.address.model.person.exceptions.PersonMatchedCannotEditException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePairList pairs;
    private final UniqueTagList tags;


    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        pairs = new UniquePairList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setPairs(List<Pair> pairs) throws DuplicatePairException {
        this.pairs.setPairs(pairs);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }

        List<Pair> syncedPairList = newData.getPairList().stream()
                // .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPairs(syncedPairList);
        } catch (DuplicatePairException e) {
            throw new AssertionError("AddressBooks should not have duplicate pairs");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    //@@author alexawangzi
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

    //@@author
    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     * This is meant to be used only for rating and remark changes for a person.
     * @param target
     * @param editedPerson
     * @throws DuplicatePersonException
     * @throws PersonNotFoundExceptiong
     */
    public void updatePersonForRateAndRemark(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);
        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
        removeUnusedTags();
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        Set<Tag> personTagsAsSet = new HashSet<>(person.getTags());
        final UniqueTagList personTags = new UniqueTagList(personTagsAsSet);
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getPrice(), person.getSubject(), person.getLevel(), person.getStatus(), person.getRole(),
                correctTagReferences, person.getRemark(), person.getRate(), person.getPairHashes());
    }



    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException, PersonMatchedCannotDeleteException {
        if (!key.getPairHashes().isEmpty()) {
            throw new PersonMatchedCannotDeleteException();
        }
        try {
            persons.remove(key);
            return true;
        } catch (PersonNotFoundException pnfe) {
            throw new PersonNotFoundException();
        }
    }


    //// pair-level operations

    //@@author alexawangzi
    /**
     * Adds a pair to the address book.
     * @param key
     * @throws seedu.address.model.pair.exceptions.DuplicatePairException if an equivalent pair already exists.
     */
    public void addPair(Pair key) throws DuplicatePairException {
        pairs.add(key);
    }

    //@@author alexawangzi
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

    //@@author alexawangzi
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

    //@@author alexawangzi
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

    //@@author alexawangzi
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

    //@@author
    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<seedu.address.model.pair.Pair> getPairList() {
        return pairs.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

    /**
     *
     * Removes unsed {@code tag} from this {@code AddressBook}.
     * Reused from https://github.com/se-edu/
     * addressbook-level4/pull/790/commits/48ba8e95de5d7eae883504d40e6795c857dae3c2
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInPersons = persons.asObservableList().stream()
                           .map(Person::getTags)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInPersons);
    }
}
