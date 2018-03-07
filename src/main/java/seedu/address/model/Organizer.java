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
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Organizer implements ReadOnlyOrganizer {

    private final UniqueTaskList persons;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        persons = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public Organizer() {
    }

    /**
     * Creates an Organizer using the Persons and Tags in the {@code toBeCopied}
     */
    public Organizer(ReadOnlyOrganizer toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Task> tasks) throws DuplicateTaskException {
        this.persons.setPersons(tasks);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code Organizer} with {@code newData}.
     */
    public void resetData(ReadOnlyOrganizer newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Task> syncedTaskList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedTaskList);
        } catch (DuplicateTaskException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }

    //// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addPerson(Task p) throws DuplicateTaskException {
        Task task = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        persons.add(task);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code Organizer}'s tag list will be updated with the tags of {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *                                another existing task in the list.
     * @throws TaskNotFoundException  if {@code target} could not be found in the list.
     * @see #syncWithMasterTagList(Task)
     */
    public void updatePerson(Task target, Task editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        Task syncedEditedTask = syncWithMasterTagList(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        persons.setPerson(target, syncedEditedTask);
    }

    /**
     * Updates the master tag list to include tags in {@code task} that are not in the list.
     *
     * @return a copy of this {@code task} such that every tag in this task points to a Tag object in the master
     * list.
     */
    private Task syncWithMasterTagList(Task task) {
        final UniqueTagList personTags = new UniqueTagList(task.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Task(
                task.getName(), task.getPhone(), task.getEmail(), task.getAddress(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code Organizer}.
     *
     * @throws TaskNotFoundException if the {@code key} is not in this {@code Organizer}.
     */
    public boolean removePerson(Task key) throws TaskNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Organizer // instanceof handles nulls
                && this.persons.equals(((Organizer) other).persons)
                && this.tags.equalsOrderInsensitive(((Organizer) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
