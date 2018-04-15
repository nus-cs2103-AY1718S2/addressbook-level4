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
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.UniqueClientList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
//@@author shookshire
/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
    private final UniqueClientList students;
    private final UniqueClientList tutors;
    private final UniqueClientList closedStudents;
    private final UniqueClientList closedTutors;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueClientList();
        tutors = new UniqueClientList();
        closedTutors = new UniqueClientList();
        closedStudents = new UniqueClientList();
        tags = new UniqueTagList();
    }

    //@@author
    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    //@@author shookshire
    public void setStudents(List<Client> students) throws DuplicatePersonException {
        this.students.setClients(students);
    }

    public void setTutors(List<Client> tutors) throws DuplicatePersonException {
        this.tutors.setClients(tutors);
    }

    public void setClosedStudents(List<Client> closedStudents) throws DuplicatePersonException {
        this.closedStudents.setClients(closedStudents);
    }

    public void setClosedTutors(List<Client> closedTutors) throws DuplicatePersonException {
        this.closedTutors.setClients(closedTutors);
    }

    //@@author
    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Client> syncedStudentList = newData.getStudentList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Client> syncedTutorList = newData.getTutorList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Client> syncedClosedStudentList = newData.getClosedStudentList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Client> syncedClosedTutorList = newData.getClosedTutorList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setStudents(syncedStudentList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate students");
        }
        try {
            setTutors(syncedTutorList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate tutors");
        }
        try {
            setClosedStudents(syncedClosedStudentList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate students");
        }
        try {
            setClosedTutors(syncedClosedTutorList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate tutors");
        }
    }

    //// person-level operations
    //@@author shookshire
    /**
     * Adds a tutor to TuitionCor.
     * Also checks the new tutor's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tutor to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addTutor(Client t) throws DuplicatePersonException {
        Client tutor = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        tutors.add(tutor, closedTutors);
    }

    /**
     * Adds a student to TuitionCor.
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the student to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addStudent(Client t) throws DuplicatePersonException {
        Client student = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        students.add(student, closedStudents);
    }

    /**
     * Adds a student to closed student's list.
     * Also checks the closed student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the closed student to point to those in {@link #tags}.
     *
     * @throws AssertionError if an equivalent person already exists.
     */
    public void addClosedStudent(Client t) throws AssertionError {
        Client closedStudent = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        closedStudents.add(closedStudent);
    }

    /**
     * Adds a tutor to closed tutor's list.
     * Also checks the closed tutor's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the closed tutor to point to those in {@link #tags}.
     *
     * @throws AssertionError if an equivalent person already exists.
     */
    public void addClosedTutor(Client t) throws AssertionError {
        Client closedTutor = syncWithMasterTagList(t);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        closedTutors.add(closedTutor);
    }

    /**
     * For test cases use and when adding sample data
     * Adds a closed client to TuitionCor.
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tutor to point to those in {@link #tags}.
     *
     */
    public void addClosedClient(Client t) {
        if (t.getCategory().isStudent()) {
            Client closedStudent = syncWithMasterTagList(t);
            closedStudents.add(closedStudent);
        } else {
            Client closedTutor = syncWithMasterTagList(t);
            closedTutors.add(closedTutor);
        }
    }

    /**
     * For test cases use and when adding sample data
     * Adds a client to TuitionCor
     * Also checks the new student's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the tutor to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addClient(Client t) throws DuplicatePersonException {
        if (t.getCategory().isStudent()) {
            Client student = syncWithMasterTagList(t);
            students.add(student);
        } else {
            Client tutor = syncWithMasterTagList(t);
            tutors.add(tutor);
        }
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedClient}.
     * Either closedStudents or closedTutors will be pass in for duplication check when editing the client in active
     * list.
     * @throws DuplicatePersonException if updating the client's details causes the client to be equivalent to
     *      another existing client in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Client)
     */
    public void updatePerson(Client target, Client editedClient, Category category)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedClient);

        Client syncedEditedPerson = syncWithMasterTagList(editedClient);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        if (category.isStudent()) {
            students.setClient(target, syncedEditedPerson, closedStudents);
        } else {
            tutors.setClient(target, syncedEditedPerson, closedTutors);
        }
    }

    //@@author
    /**
     *  Updates the master tag list to include tags in {@code client} that are not in the list.
     *  @return a copy of this {@code client} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Client syncWithMasterTagList(Client client) {
        final UniqueTagList clientTags = new UniqueTagList(client.getTags());
        tags.mergeFrom(clientTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        clientTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Client(
                client.getName(), client.getPhone(), client.getEmail(), client.getAddress(), correctTagReferences,
                client.getLocation(), client.getGrade(), client.getSubject(), client.getCategory());
    }

    //@@author shookshire
    /**
     * Removes {@code key} from the active client list in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeClient(Client key, Category category) throws PersonNotFoundException {
        Boolean isSuccess;

        if (category.isStudent()) {
            isSuccess = students.remove(key);
        } else {
            isSuccess = tutors.remove(key);
        }

        if (isSuccess) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Removes {@code key} from the closed client list in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeClosedClient(Client key, Category category) throws PersonNotFoundException {
        Boolean isSuccess;

        if (category.isStudent()) {
            isSuccess = closedStudents.remove(key);
        } else {
            isSuccess = closedTutors.remove(key);
        }

        if (isSuccess) {
            return true;
        } else {
            throw new PersonNotFoundException();
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
        return students.asObservableList().size() + " students, "
                + tutors.asObservableList().size() + " tutors, "
                + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    //@@author shookshire
    @Override
    public ObservableList<Client> getStudentList() {
        return students.asObservableList();
    }

    @Override
    public ObservableList<Client> getTutorList() {
        return tutors.asObservableList();
    }

    //@@author
    @Override
    public ObservableList<Client> getClosedStudentList() {
        return closedStudents.asObservableList();
    }

    @Override
    public ObservableList<Client> getClosedTutorList() {
        return closedTutors.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.students.equals(((AddressBook) other).students)
                && this.tutors.equals(((AddressBook) other).tutors)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(students, tutors, tags);
    }
}
