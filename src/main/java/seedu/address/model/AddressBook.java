package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.UniqueAliasList;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueAliasList aliases;
    private final Password password;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        aliases = new UniqueAliasList();
    }

    //@@author yeggasd
    public AddressBook() {
        password = new Password();
    }

    public AddressBook(String password) {
        this.password = new Password(password);
    }
    //@@author

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

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    //@@author jingyinno
    public void setAliases(HashMap<String, String> aliases) {
        this.aliases.setAliases(aliases);
    }
    //@@author

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setAliases(new HashMap<>(newData.getAliasMapping()));
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        updatePassword(newData.getPassword());
        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }

    //@@author jingyinno
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData} and {@code newList}.
     */
    public void resetData(ReadOnlyAddressBook newData, HashMap<String, String> newList) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        setAliases(new HashMap<>(newList));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        updatePassword(newData.getPassword());
        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }
    //@@author

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

    //@@author Caijun7
    /**
     * Imports a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     */
    public void importPerson(Person p) {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.importPerson(person);
    }
    //@@author

    //// command-level operations

    //@@author jingyinno
    /**
     * Adds an alias to the address book.
     *
     * @throws DuplicateAliasException if an equivalent alias already exists.
     */
    public void addAlias(Alias alias) throws DuplicateAliasException {
        aliases.add(alias);
    }

    /**
     * Removes an alias from the address book.
     *
     * @throws AliasNotFoundException if alias to-be-removed does not exist.
     */
    public void removeAlias(String toRemove) throws AliasNotFoundException {
        aliases.remove(toRemove);
    }

    /**
     * Retrieve the associated commandWord from the address book.
     * @param aliasKey the alias keyword associated to command word
     * @return the associated command word if exists else the aliasKey
     *
     */
    public String getCommandFromAlias(String aliasKey) {
        return aliases.contains(aliasKey) ? aliases.getCommandFromAlias(aliasKey) : aliasKey;
    }

    //@@author

    //@@author Caijun7
    public void importAlias(Alias alias) {
        aliases.importAlias(alias);
    }
    //@@author

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
        removeUnusedTags();
    }

    //@@author Caijun7-reused
    /**
     * Removes all {@code tag}s that are not used by any {@code person} in this {@code AddressBook}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInPersons = persons.asObservableList().stream()
                .map(Person::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInPersons);
    }
    //@@author

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getBirthday(),
                person.getTimetable(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author Caijun7
    public void importTag(Tag t) {
        tags.importTag(t);
    }
    //@@author

    //@@author Caijun7-reused
    /**
    * Removes {@code tag} from {@code person} in this {@code AddressBook}.
    * @throws PersonNotFoundException if the {@code person} is not in this {@code AddressBook}.
    */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> newTags = new HashSet<>(person.getTags());
        if (!newTags.remove(tag)) {
            return;
        }
        Person newPerson =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getBirthday(), person.getTimetable(), newTags);
        try {
            updatePerson(person, newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }

    /**
    * Removes {@code tag} from all persons in this {@code AddressBook}.
    */
    public void removeTag(Tag tag) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible exception: person is obtained from the address book.");
        }
    }
    //@@author

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags, "
                + password + " password, " + aliases.asObservableList().size() + " aliases";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    //@@author jingyinno
    @Override
    public ObservableList<Alias> getAliasList() {
        return aliases.getAliasObservableList();
    }

    @Override
    public ArrayList<ArrayList<String>> getUiFormattedAliasList() {
        return aliases.extractAliasMapping();
    }

    @Override
    public HashMap<String, String> getAliasMapping() {
        return aliases.getAliasCommandMappings();
    }


    @Override
    public void resetAliasList() {
        aliases.resetHashmap();
    }
    //@@author

    //@@author yeggasd
    @Override
    public Password getPassword() {
        return password;
    }

    /**
     * Updates the password of this {@code AddressBook}.
     * @param newPassword  will be the new password.
     */
    public void updatePassword (byte[] newPassword) {
        password.updatePassword(newPassword);
    }

    /**
     * Updates the password of this {@code AddressBook}.
     * @param newPassword  will be the new password.
     */
    public void updatePassword (Password newPassword) {
        password.updatePassword(newPassword);
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.aliases.equals(((AddressBook) other).aliases)
                && this.password.equals(((AddressBook) other).password);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
