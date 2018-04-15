package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.CellMap;
import seedu.address.model.cell.exceptions.AlreadyInCellException;
import seedu.address.model.cell.exceptions.FullCellException;
import seedu.address.model.cell.exceptions.NonExistentCellException;
import seedu.address.model.cell.exceptions.NotPrisonerException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.user.UniqueUserMap;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.CannotDeleteSelfException;
import seedu.address.model.user.exceptions.MustHaveAtLeastOneSecurityLevelThreeUserException;
import seedu.address.model.user.exceptions.NotEnoughAuthorityToDeleteException;
import seedu.address.model.user.exceptions.UserAlreadyExistsException;
import seedu.address.model.user.exceptions.UserDoesNotExistException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final CellMap cells;
    private final UniqueUserMap users;

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
        cells = new CellMap();
        users = new UniqueUserMap();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags and Cells in the {@code toBeCopied}
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

    public void setCells(ObservableList<Cell> cells) {
        this.cells.setCells(cells);
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
        setCells(newData.getCellList());
        setUsers(newData.getUserList());
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
        //@@author sarahgoh97
        if (target.getIsInCell()) {
            cells.setPrisonerToCell(target, syncedEditedPerson);
        }
    }

    /**
     * Replaces the given person {@code changed} in the list with {@code original} in the cellMap.
     * This is only done from undo.
     */
    public void updatePrisonerFromUndo(Person changed, Person original) {
        requireAllNonNull(original, changed);

        cells.setPrisonerToCell(changed, original);
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
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getRole(),
                correctTagReferences, person.getIsInCell());
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            //@@author sarahgoh97
            if (key.getIsInCell() == true) {
                String cellAddress = key.getCellAddress().toString();
                cells.deletePrisonerFromCell(key, cellAddress);
            }
            //@@author
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author sarahgoh97
    //// cell-level operations

    /**
     *
     * @param c is the cell to add to the map
     */
    public void addCell(Cell c) {
        String cellAddress = c.getCellAddress();
        cells.setCell(c, cellAddress);
    }

    /**
     * Adds a prisoner to a cell
     * @param cellAddress to get the correct cell
     * @param prisoner to be added into the cell
     * @throws FullCellException if the cell already has the maximum number of prisoners
     * @throws NonExistentCellException if the cell address is invalid
     * @throws NotPrisonerException if the Person passed is not a prisoner
     */
    public void addPrisonerToCell(String cellAddress, Person prisoner) throws FullCellException,
            NonExistentCellException, NotPrisonerException, AlreadyInCellException {
        requireAllNonNull(prisoner, cellAddress);
        if (!Cell.isValidCellAddress(cellAddress)) {
            throw new NonExistentCellException();
        } else if (!prisoner.getRole().equals(Role.PRISONER)) {
            throw new NotPrisonerException();
        } else if (prisoner.getIsInCell()) {
            throw new AlreadyInCellException();
        } else if (cells.getCell(cellAddress).getNumberOfPrisoners() >= Cell.MAX_SIZE) {
            throw new FullCellException();
        } else {
            Person updatedPrisoner = new Person(prisoner, true, cellAddress);
            updatePrisoner(prisoner, updatedPrisoner);
            addPrisonerToCellPermitted(updatedPrisoner, cellAddress);
        }
    }

    /**
     * Adding prisoner to CellMap once exceptions cleared
     * @param prisoner is the correct person without requiring editing
     * @param cellAddress is the String corresponding to the cell shown on map
     */
    public void addPrisonerToCellPermitted(Person prisoner, String cellAddress) {
        cells.addPrisonerToCell(prisoner, cellAddress);
    }

    /**
     * Deletes prisoner from a specified cell
     */
    public void deletePrisonerFromCell(Person prisoner, String cellAddress) {
        cells.deletePrisonerFromCell(prisoner, cellAddress);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code updatedPrisoner}.
     */
    public void updatePrisoner(Person target, Person updatedPrisoner) {
        persons.setPrisoner(target, updatedPrisoner);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags\n"
                + cells.getCellList() + users.getUserList();
        // TODO: refine later
    }

    //@@author zacci
    //// user-level operations

    /**
     *
     * @param u is the user to add to the HashMap
     */
    public void addUser(User u) throws UserAlreadyExistsException {
        users.addUser(u);
    }

    /**
     * Attempt to log in with the entered username and password
     */
    public int attemptLogin(String username, String password) {
        return users.verify(username, password);
    }

    @Override
    public ObservableList<User> getUserList() {
        return users.getUserList();
    }

    public void setUsers(ObservableList<User> users) {
        this.users.setUsers(users);
    }

    public void deleteUser(String userToDelete, String deleterUsername) throws CannotDeleteSelfException,
            MustHaveAtLeastOneSecurityLevelThreeUserException, UserDoesNotExistException,
            NotEnoughAuthorityToDeleteException {
        users.deleteUser(userToDelete, deleterUsername);
    }

    //@@author

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    //@@author sarahgoh97
    @Override
    public ObservableList<Cell> getCellList() {
        return cells.getCellList();
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags)
                && this.cells.equals(((AddressBook) other).cells)
                && this.users.equals(((AddressBook) other).users));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
