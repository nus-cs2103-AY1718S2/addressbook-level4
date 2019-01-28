package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.appointment.exceptions.AppointmentDependencyNotEmptyException;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.UniquePetPatientList;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;
import seedu.address.model.petpatient.exceptions.PetDependencyNotEmptyException;
import seedu.address.model.petpatient.exceptions.PetPatientNotFoundException;
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
    private final UniquePetPatientList petPatients;

        /*
         * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
         * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
         *
         * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
        *   among constructors.
        */ {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        appointments = new UniqueAppointmentList();
        petPatients = new UniquePetPatientList();
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

    public void setPersons(List<Person> persons) throws DuplicatePersonException, DuplicateNricException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setAppointments(List<Appointment> appointments)
            throws DuplicateAppointmentException, DuplicateDateTimeException,
        ConcurrentAppointmentException, PastAppointmentException {
        this.appointments.setAppointments(appointments);
    }

    public void setPetPatients(List<PetPatient> petPatients) throws DuplicatePetPatientException,
        ConcurrentAppointmentException, PastAppointmentException {
        this.petPatients.setPetPatients(petPatients);
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
            throw new AssertionError("Medeina should not have duplicate contacts.");
        } catch (DuplicateNricException e) {
            throw new AssertionError("Medeina should not have two contacts sharing the same NRIC.");
        }

        setTags(new HashSet<>(newData.getTagList()));
        List<Appointment> syncedAppointmentList = newData.getAppointmentList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        try {
            setAppointments(syncedAppointmentList);
        } catch (DuplicateAppointmentException dae) {
            throw new AssertionError("Medeina should not have duplicate appointments.");
        } catch (DuplicateDateTimeException ddte) {
            throw new AssertionError("Medeina should not have appointments on the same slot");
        } catch (ConcurrentAppointmentException cae) {
            throw new AssertionError("Medeina should not add appointments to on-going appointment slots");
        } catch (PastAppointmentException pae) {
            throw new AssertionError("AddressBook should not add appointments with past DateTime");
        }

        setTags(new HashSet<>(newData.getTagList()));
        List<PetPatient> syncedPetPatientList = newData.getPetPatientList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPetPatients(syncedPetPatientList);
        } catch (DuplicatePetPatientException e) {
            throw new AssertionError("Medeina should not have duplicate pet patients");
        } catch (ConcurrentAppointmentException cae) {
            throw new AssertionError("Medeina should not add appointments to on-going appointment slots");
        } catch (PastAppointmentException pe) {
            throw new AssertionError("Medeina should not add appointments with past DateTime");
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
    public void addPerson(Person p) throws DuplicatePersonException, DuplicateNricException {
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
        removeUselessTags();
    }

    /**
     * Adds a pet patient to the address book.
     * Also checks the new pet patient's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the pet patient to point to those in {@link #tags}.
     *
     * @throws DuplicatePetPatientException if an equivalent person already exists.
     */
    public void addPetPatient(PetPatient p) throws DuplicatePetPatientException {
        PetPatient petPatient = syncWithMasterTagList(p);
        petPatients.add(petPatient);
    }

    //@@author chialejing
    /**
     * Replaces the given pet patient {@code target} in the list with {@code editedPetPatient}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPetPatient}.
     *
     * @throws DuplicatePetPatientException if updating the pet patient's details causes the pet patient to be
     *                                      equivalent to another existing pet patient in the list.
     * @throws PetPatientNotFoundException  if {@code target} could not be found in the list.
     * @see #syncWithMasterTagList(PetPatient)
     */
    public void updatePetPatient(PetPatient target, PetPatient editedPetPatient)
            throws DuplicatePetPatientException, PetPatientNotFoundException {
        requireNonNull(editedPetPatient);

        PetPatient syncEditedPetPatient = syncWithMasterTagList(editedPetPatient);
        petPatients.setPetPatient(target, syncEditedPetPatient);
        removeUselessTags();
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedAppointment}.
     *
     * @throws DuplicateAppointmentException if updating the appointment's details causes the appointment to be
     *                                       equivalent to another existing appointment in the list.
     * @throws AppointmentNotFoundException  if {@code target} could not be found in the list.
     * @see #syncWithMasterTagList(Appointment)
     */
    public void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireNonNull(editedAppointment);

        Appointment syncEditedPetPatient = syncWithMasterTagList(editedAppointment);
        appointments.setAppointment(target, syncEditedPetPatient);
        removeUselessTags();
    }

    //@@author
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
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
            person.getNric(), correctTagReferences);
    }

    //@@author chialejing
    /**
     * Updates the master tag list to include tags in {@code petPatient} that are not in the list.
     *
     * @return a copy of this {@code petPatient} such that every tag in this pet patient points to a Tag object in the
     * master list.
     */
    private PetPatient syncWithMasterTagList (PetPatient petPatient) {
        final UniqueTagList currentPetPatientTags = new UniqueTagList(petPatient.getTags());
        tags.mergeFrom(currentPetPatientTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        currentPetPatientTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new PetPatient(
                petPatient.getName(),
                petPatient.getSpecies(),
                petPatient.getBreed(),
                petPatient.getColour(),
                petPatient.getBloodType(),
                petPatient.getOwner(),
                correctTagReferences);
    }

    //@@author wynonaK
    /**
     * Updates the master tag list to include tags in {@code appointment} that are not in the list.
     *
     * @return a copy of this {@code appointment} such that every tag in this appointment
     * points to a Tag object in the master list.
     */
    private Appointment syncWithMasterTagList(Appointment appointment) {
        final UniqueTagList appointmentTags = new UniqueTagList(appointment.getAppointmentTags());
        tags.mergeFrom(appointmentTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        appointmentTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Appointment(
                appointment.getOwnerNric(),
                appointment.getPetPatientName(),
                appointment.getRemark(),
                appointment.getDateTime(),
                correctTagReferences);
    }

    /**
     * Adds an appointment.
     * Also checks the new appointment's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the appointment to point to those in {@link #tags}.
     *
     * @throws DuplicateAppointmentException if an equivalent person already exists.
     */
    public void addAppointment(Appointment a) throws DuplicateAppointmentException, DuplicateDateTimeException,
        ConcurrentAppointmentException, PastAppointmentException {
        Appointment appointment = syncWithMasterTagList(a);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any appointment
        // in the appointment list.
        appointments.add(appointment);
    }

    ////Delete operations

    //@@author wynonaK
    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     * @throws PetDependencyNotEmptyException if the {@code key} still contains pet patients it is tied to.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException, PetDependencyNotEmptyException {
        petPatientDependenciesExist(key);

        if (persons.remove(key)) {
            removeUselessTags();
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Removes pet patient {@code key} from this {@code AddressBook}.
     *
     * @throws PetPatientNotFoundException if the {@code key} is not in this {@code AddressBook}.
     * @throws AppointmentDependencyNotEmptyException if the {@code key} still contains appointments it is tied to.
     */
    public boolean removePetPatient(PetPatient key)
            throws PetPatientNotFoundException, AppointmentDependencyNotEmptyException {
        appointmentDependenciesExist(key);

        if (petPatients.remove(key)) {
            removeUselessTags();
            return true;
        } else {
            throw new PetPatientNotFoundException();
        }
    }

    /**
     * Forcefully removes all pet patients dependencies on {@code key} from this {@code AddressBook}.
     */

    public List<PetPatient> removeAllPetPatientDependencies(Person key) {
        Iterator<PetPatient> petPatientIterator = petPatients.iterator();
        List<PetPatient> petPatientsDeleted = new ArrayList<>();
        while (petPatientIterator.hasNext()) {
            PetPatient petPatient = petPatientIterator.next();

            if (petPatient.getOwner().equals(key.getNric())) {
                petPatientsDeleted.add(petPatient);
                petPatientIterator.remove();
            }
        }
        return petPatientsDeleted;
    }

    /**
     * @throws AppointmentDependencyNotEmptyException if appointment dependencies of {@code key}
     * still exists in {@code AddressBook}.
     */
    private void appointmentDependenciesExist(PetPatient key) throws AppointmentDependencyNotEmptyException {
        for (Appointment appointment : appointments) {
            if (appointment.getPetPatientName().equals(key.getName())
                    && appointment.getOwnerNric().equals(key.getOwner())) {
                throw new AppointmentDependencyNotEmptyException("Appointment dependency still exist!");
            }
        }
    }

    /**
     * @throws PetDependencyNotEmptyException if pet dependencies of {@code key} still exists in {@code AddressBook}.
     */
    private void petPatientDependenciesExist(Person key) throws PetDependencyNotEmptyException {
        for (PetPatient petPatient : petPatients) {
            if (petPatient.getOwner().equals(key.getNric())) {
                throw new PetDependencyNotEmptyException("Pet Patient dependency still exist!");
            }
        }
    }

    /**
     * Forcefully removes all dependencies relying on pet patient {@code key} from this {@code AddressBook}.
     *
     */
    public List<Appointment> removeAllAppointmentDependencies(PetPatient key) {
        List<Appointment> appointmentsDeleted = new ArrayList<>();
        Iterator<Appointment> appointmentIterator = appointments.iterator();

        while (appointmentIterator.hasNext()) {
            Appointment appointment = appointmentIterator.next();

            if (appointment.getPetPatientName().equals(key.getName())
                    && appointment.getOwnerNric().equals(key.getOwner())) {
                appointmentsDeleted.add(appointment);
                appointmentIterator.remove();
            }
        }

        return appointmentsDeleted;
    }

    /**
     * Removes appointment {@code key} from this {@code AddressBook}.
     *
     * @throws AppointmentNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeAppointment(Appointment key) throws AppointmentNotFoundException {
        if (appointments.remove(key)) {
            removeUselessTags();
            return true;
        } else {
            throw new AppointmentNotFoundException();
        }
    }

    //// tag operations

    //@@author
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author wynonaK-reused
    /**
     * Removes all {@code tag}s not used by anyone in this {@code AddressBook}.
     * Reused from https://github.com/se-edu/addressbook-level4/pull/790/files with minor modifications
     */
    private void removeUselessTags() {
        Set<Tag> personTags =
                persons.asObservableList()
                        .stream()
                        .map(Person::getTags)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());
        Set<Tag> appointmentTags =
                appointments.asObservableList()
                        .stream()
                        .map(Appointment::getAppointmentTags)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());
        Set<Tag> petPatientTags =
                petPatients.asObservableList()
                        .stream()
                        .map(PetPatient::getTags)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());

        personTags.addAll(appointmentTags);
        personTags.addAll(petPatientTags);
        tags.setTags(personTags);
    }

    /**
     * Removes {@code tag} from {@code person} with that tag this {@code AddressBook}.
     *
     * @throws PersonNotFoundException if {@code person} is not found in this {@code AddressBook}.
     *                                 Reused from https://github.com/se-edu/addressbook-level4/
     *                                 pull/790/files with minor modifications
     */
    private void removeTagParticular(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> tagList = new HashSet<>(person.getTags()); //gets all the tags from a person

        if (tagList.remove(tag)) {
            Person updatedPerson =
                    new Person(person.getName(), person.getPhone(), person.getEmail(),
                        person.getAddress(), person.getNric(), tagList);

            try {
                updatePerson(person, updatedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new AssertionError("Modifying tag only should not result in duplicate contact.");
            }
        } else {
            return;
        }
    }

    /**
     * Removes {@code tag} from all person with that tag this {@code AddressBook}.
     * Reused from https://github.com/se-edu/addressbook-level4/pull/790/files with minor modifications
     */
    public void removeTag(Tag tag) {
        try {
            for (Person currPerson : persons) {
                removeTagParticular(tag, currPerson);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Impossible as obtained from Medeina.");
        }
    }

    //// util methods

    //@@author
    @Override
    public String toString() {
        return persons.asObservableList().size() + " contacts, "
                + petPatients.asObservableList().size() + " pet patients, "
                + appointments.asObservableList().size() + " appointments, "
                + tags.asObservableList().size() + " tags";
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
    public ObservableList<PetPatient> getPetPatientList() {
        return petPatients.asObservableList();
    }

    //@@author wynonaK
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
                && this.appointments.equals(((AddressBook) other).appointments)
                && this.petPatients.equals(((AddressBook) other).petPatients)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, appointments, petPatients, tags);
    }
}
