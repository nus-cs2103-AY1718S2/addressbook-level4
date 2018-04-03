package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import javafx.collections.ObservableList;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.email.Template;
import seedu.address.model.email.UniqueTemplateList;
import seedu.address.model.email.exceptions.DuplicateTemplateException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonCompare;
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
    private final UniqueAppointmentList appointments;
    private final UniqueTemplateList templates;

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
        appointments = new UniqueAppointmentList();
        templates = new UniqueTemplateList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons, Tags and Appointments in the {@code toBeCopied}
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

    //@@author jlks96
    public void setAppointments(List<Appointment> appointments) throws DuplicateAppointmentException {
        this.appointments.setAppointments(appointments);
    }
    //@@author

    //@@author ng95junwei

    /**
     * TBD replace with DB seed.
     * @return a List of template to initialize AddressBook with
     */
    private static List<Template> generateTemplates() {
        List<Template> list = new ArrayList<>();
        Template template1 = new Template("coldEmail", "Meet up over Coffee",
                "Hey, I am from Addsurance and would like you ask if you are interested in planning your"
                        + " finances with us. Would you care to meet over coffee in the next week or so?");
        Template template2 = new Template("followUpEmail", "Follow up from last week",
                "Hey, we met last week and I was still hoping if you would like to leave your "
                        + "finances with us at Addsurance. Would you care to meet over coffee in the next week or so"
                        + " to discuss further?");
        list.add(template1);
        list.add(template2);
        return list;
    }

    public void setTemplates() throws DuplicateTemplateException {
        this.templates.setTemplates(generateTemplates());
    }

    public synchronized UniqueTemplateList getAllTemplates() {
        return this.templates;
    }
    //@@author
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
            setAppointments(newData.getAppointmentList());
            setTemplates();
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        } catch (DuplicateAppointmentException e) {
            throw new AssertionError("AddressBooks should not have duplicate appointments");
        } catch (DuplicateTemplateException e) {
            throw new AssertionError("AddressBooks should not have duplicate templates");
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
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getDateAdded(), correctTagReferences);
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

    //@@author jlks96
    /**
     * Removes {@code keys} from this {@code AddressBook}.
     * @throws PersonNotFoundException if any of the {@code keys} are not in this {@code AddressBook}.
     */
    public void removePersons(List<Person> keys) throws PersonNotFoundException {
        persons.removeAll(keys);
    }
    //@@author

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author jlks96
    //// appointment-level operations

    /**
     * Adds an appointment to the address book.
     *
     * @throws DuplicateAppointmentException if an equivalent appointment already exists.
     */
    public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        appointments.add(appointment);
    }

    /**
     * Removes {@code appointment} from this {@code AddressBook}.
     * @throws AppointmentNotFoundException if the {@code appointment} is not in this {@code AddressBook}.
     */
    public boolean removeAppointment(Appointment appointment) throws AppointmentNotFoundException {
        if (appointments.remove(appointment)) {
            return true;
        } else {
            throw new AppointmentNotFoundException();
        }
    }
    //@@author

    //@@author luca590
    /**
     * This function is intended to be called from ModelManager to protect
     * the private {@code UniquePersonList persons} variable
     */
    public void sortAddressBookAlphabeticallyByName() throws DuplicatePersonException {
        //persons is UniquePersonList implements Iterable
        //setPersons(List<Persons> ...)
        List list = Lists.newArrayList(persons);
        Collections.sort(list, new PersonCompare());
        setPersons((List<Person>) list);

    }

    //@@author

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags, "
                + appointments.asObservableList().size() + " appointments";
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

    //@@author jlks96
    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asObservableList();
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags)
                && this.appointments.equals(((AddressBook) other).appointments));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags, appointments);
    }
}
