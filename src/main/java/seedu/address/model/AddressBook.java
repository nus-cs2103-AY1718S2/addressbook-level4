package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.NotificationNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueEmployeeList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.Photo;
import seedu.address.model.photo.UniquePhotoList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEmployeeList persons;
    private final UniqueTagList tags;
    private final UniquePhotoList photos;
    private LinkedList<Notification> notifications;
    private int nextId;
    private String password;
    private Semaphore semaphore;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueEmployeeList();
        tags = new UniqueTagList();
        photos = new UniquePhotoList();
        notifications = new LinkedList<>();
        nextId = 0;
        password = "admin";
        semaphore = new Semaphore(1);
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

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos.setPhotos(photos);
    }

    //@@author IzHoBX
    public void setNotificationsList(LinkedList<Notification> notifications) {
        this.notifications = notifications;
    }
    //@@author

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        setPhotos(new HashSet<>(newData.getPhotoList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        setNotificationsList(newData.getNotificationsList());
        this.nextId = newData.getNextId();
        this.password = newData.getPassword();

        try {
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Employees Tracker should not have duplicate employees");
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
        //@@author IzHoBX
        if (!person.isInitialized()) {
            person.setId(nextId);
            nextId++;
        }
        //@@author
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
        syncedEditedPerson.setPhotoName(editedPerson.getPhotoName());
        persons.setPerson(target, syncedEditedPerson);
    }

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

        //@@author crizyli
        Photo toAdd = new Photo(person.getPhotoName());
        if (!photos.contains(toAdd)) {
            try {
                photos.add(toAdd);
            } catch (UniquePhotoList.DuplicatePhotoException e) {
                e.printStackTrace();
            }
        }

        //@@author emer7
        Person toReturn = new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                correctTagReferences, person.getCalendarId());
        toReturn.setRating(person.getRating());
        toReturn.setReviews(person.getReviews());
        toReturn.setId(person.getId());
        toReturn.setPhotoName(person.getPhotoName());
        return toReturn;
        //@@author

    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        removeNotificationsWithOwnerId(key.getId());
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

    //@@author crizyli
    public void addPhoto(Photo p) throws UniquePhotoList.DuplicatePhotoException {
        photos.add(p);
    }

    //@@author IzHoBX
    // timetable entry level operations
    /**
     * Adds a notification to the address book.
     */
    public void addNotification(Notification notification) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifications.add(notification);
        semaphore.release();
    }

    /**
     * Removes a timetable entry from the address book.
     */
    public void deleteNotification(String notificationId) throws NotificationNotFoundException {
        boolean found = false;
        LinkedList<Notification> toDelete = new LinkedList<>();
        for (Notification n: notifications) {
            if (n.getEventId().equals(notificationId)) {
                toDelete.add(n);
                found = true;
            }
        }
        if (found) {
            for (Notification n: toDelete) {
                notifications.remove(n);
            }
        } else {
            throw new NotificationNotFoundException();
        }
    }

    /**
     * Removes all notifications associated with a person id.
     */
    private void removeNotificationsWithOwnerId (Integer id) {
        LinkedList<Notification> toDelete = new LinkedList<>();
        for (Notification n: notifications) {
            if (n.getOwnerId().equals(id + "")) {
                toDelete.add(n);
            }
        }
        for (Notification n: toDelete) {
            notifications.remove(n);
        }
    }
    //@@author


    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " employees, " + tags.asObservableList().size() +  " tags, "
                + notifications.size() + " notifications" + photos.asObservableList().size() + " photos.";
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    //@@author crizyli
    @Override
    public ObservableList<Photo> getPhotoList() {
        return photos.asObservableList();
    }
    //@@author

    @Override
    public LinkedList<Notification> getNotificationsList() {
        return notifications;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.notifications.equals(((AddressBook) other).notifications);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

    //@@author IzHoBX
    @Override
    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    //@@author crizyli
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //@@author Yoochard
    /** sort the existing persons in specific field
     *
     * @param field must be String and not null
     *
     * */
    public void sort(String field) {
        persons.sort(field);
    }

    //@@author IzHoBX
    /**
     * Returns a person with the given id.
     *
     * @param id must be a valid id.
     */
    public Person findPersonById(int id) {
        for (Person p: persons) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    //@@author IzHoBX

}
