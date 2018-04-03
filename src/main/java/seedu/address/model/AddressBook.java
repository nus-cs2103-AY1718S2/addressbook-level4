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
import seedu.address.model.event.DuplicateEventException;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.UniqueToDoList;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueToDoList todos;
    private final UniqueGroupList groups;
    private final UniqueEventList events;

    /**
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        todos = new UniqueToDoList();
        groups = new UniqueGroupList();
        events = new UniqueEventList();
    }

    public AddressBook() {
    }

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

    public void setToDos(List<ToDo> todos) throws DuplicateToDoException {
        this.todos.setToDos(todos);
    }

    public void setGroups(List<Group> groups) throws DuplicateGroupException {
        this.groups.setGroups(groups);
    }

    public void setEvents(List<Event> events) throws DuplicateEventException {
        this.events.setEvents(events);
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
        List<ToDo> syncedToDoList = newData.getToDoList();
        List<Group> syncedGroupList = newData.getGroupList();
        List<Event> syncedEventList = newData.getEventList();

        try {
            setPersons(syncedPersonList);
            setToDos(syncedToDoList);
            setGroups(syncedGroupList);
            setEvents(syncedEventList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        } catch (DuplicateToDoException e) {
            throw new AssertionError("AddressBooks should not have duplicate todos");
        } catch (DuplicateGroupException e) {
            throw new AssertionError("AddressBooks Should not have duplicate groups");
        } catch (DuplicateEventException e) {
            throw new AssertionError("AddressBooks Should not have duplicate events");
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

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
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
    }

    /**
     * Replaces the given ToDo {@code target} in the list with {@code editedToDo}.
     *
     * @throws DuplicateToDoException if updating the ToDo's details causes the ToDo to be equivalent to
     *                                  another existing ToDo in the list.
     * @throws ToDoNotFoundException  if {@code target} could not be found in the list.
     */
    public void updateToDo(ToDo target, ToDo editedToDo)
            throws DuplicateToDoException, ToDoNotFoundException {
        requireNonNull(editedToDo);

        todos.setToDo(target, editedToDo);
    }

    /**
     * Updates the master tag list to include tags in {@code person} that are not in the list.
     *
     * @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     * list.
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
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTimeTableLink(),
                person.getDetail(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws ToDoNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeToDo(ToDo key) throws ToDoNotFoundException {
        if (todos.remove(key)) {
            return true;
        } else {
            throw new ToDoNotFoundException();
        }
    }

    //// to-do-level operations

    /**
     * Adds a to-do to the address book.
     *
     * @throws DuplicateToDoException if an equivalent to-do already exists.
     */
    public void addToDo(ToDo todo) throws DuplicateToDoException {
        todos.add(todo);
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    ////Group operation
    public void addGroup(Group group) throws DuplicateGroupException {
        groups.add(group);
    }

    ////Event operations
    /**
     * Adds an event to the address book.
     * @throws DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(Event e) throws DuplicateEventException {
        events.add(e);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags";
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

    @Override
    public ObservableList<ToDo> getToDoList() {
        return todos.asObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asObservableList();
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
     * Removes {@code tag} from all {@code persons} in the {@code AddressBook} and from the {@code AddressBook}.
     */
    public void removeTag(Tag tag) {
        try {
            for (Person person : persons) {
                removeTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible: original person is not found from the address book.");
        }

        removeTagFromAddressBook(tag);

    }

    /**
     * Removes {@code tag} from the {@code AddressBook}.
     */
    private void removeTagFromAddressBook(Tag tag) {
        Set<Tag> editedTagList = tags.toSet();
        if (editedTagList.contains(tag)) {
            editedTagList.remove(tag);
            tags.setTags(editedTagList);
        }
    }

    /**
     * Replaces the old {@code target} tag with the new {@code editedTag}
     */
    public void editTag(Tag target, Tag editedTag) throws TagNotFoundException {
        Set<Tag> editedTagList = tags.toSet();
        if (editedTagList.contains(target)) {
            editedTagList.remove(target);
            editedTagList.add(editedTag);
            tags.setTags(editedTagList);
        } else {
            throw new TagNotFoundException();
        }
        for (Person p : persons) {
            replaceTagInPerson(target, editedTag, p);
        }
    }

    /**
     * Replaces the old {@code target} tag of a {@code person} with the new {@code editedTag}
     */
    private void replaceTagInPerson(Tag target, Tag editedTag, Person person) {
        Set<Tag> tagList = new HashSet<>(person.getTags());

        //Terminate if tag is not is tagList
        if (!tagList.remove(target)) {
            return;
        }
        tagList.add(editedTag);
        Person updatedPerson = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getTimeTableLink(), person.getDetail(), tagList);

        try {
            updatePerson(person, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Modifying a person's tags only should not result in "
                    + "a PersonNotFoundException. See Person#equals(Object).");
        }
    }

    /**
     * Removes {@code tag} from all {@code persons} in the {@code AddressBook}.
     */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> tagList = new HashSet<>(person.getTags());

        //Terminate if tag is not is tagList
        if (!tagList.remove(tag)) {
            return;
        }
        Person updatedPerson = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getTimeTableLink(), person.getDetail(), tagList);

        try {
            updatePerson(person, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }
}
