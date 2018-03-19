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
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.UniquePatientList;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Imdb implements ReadOnlyImdb {

    private final UniquePatientList persons;
    private final UniqueTagList tags;
    private final UniqueAppointmentList appointments;
    private final UniquePatientVisitingQueue visitingQueue;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePatientList();
        tags = new UniqueTagList();
        appointments = new UniqueAppointmentList();
        visitingQueue = new UniquePatientVisitingQueue();
    }

    public Imdb() {}

    /**
     * Creates an Imdb using the Persons and Tags in the {@code toBeCopied}
     */
    public Imdb(ReadOnlyImdb toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Patient> patients) throws DuplicatePatientException {
        this.persons.setPersons(patients);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments.setAppointment(appointments);
    }

    /**
     * Resets the existing data of this {@code Imdb} with {@code newData}.
     */
    public void resetData(ReadOnlyImdb newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Patient> syncedPatientList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        setAppointments(new HashSet<>(newData.getAppointmentList()));

        try {
            setPersons(syncedPatientList);
        } catch (DuplicatePatientException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }
    }

    //// patient-level operations

    /**
     * Adds a patient to the address book.
     * Also checks the new patient's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the patient to point to those in {@link #tags}.
     *
     * @throws DuplicatePatientException if an equivalent patient already exists.
     */
    public void addPerson(Patient p) throws DuplicatePatientException {
        Patient patient = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any patient
        // in the patient list.
        persons.add(patient);
    }

    /**
     * Replaces the given patient {@code target} in the list with {@code editedPerson}.
     * {@code Imdb}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePatientException if updating the patient's details causes the patient to be equivalent to
     *      another existing patient in the list.
     * @throws PatientNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Patient)
     */
    public void updatePerson(Patient target, Patient editedPatient)
            throws DuplicatePatientException, PatientNotFoundException {
        requireNonNull(editedPatient);

        Patient syncedEditedPatient = syncWithMasterTagList(editedPatient);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any patient
        // in the patient list.
        persons.setPerson(target, syncedEditedPatient);
    }

    /**
     *  Updates the master tag list to include tags in {@code patient} that are not in the list.
     *  @return a copy of this {@code patient} such that every tag in this patient points to a Tag object in the master
     *  list.
     */
    private Patient syncWithMasterTagList(Patient patient) {
        final UniqueTagList personTags = new UniqueTagList(patient.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking patient tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of patient tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Patient(
                patient.getName(), patient.getPhone(), patient.getEmail(), patient.getAddress(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code Imdb}.
     * @throws PatientNotFoundException if the {@code key} is not in this {@code Imdb}.
     */
    public boolean removePerson(Patient key) throws PatientNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PatientNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    public void addAppointment(Appointment appt) throws UniqueAppointmentList.DuplicatedAppointmentException {
        appointments.add(appt);
    }

    /**
     * Adds a patient to the visiting queue.
     * Also checks the new patient's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the patient to point to those in {@link #tags}.
     *
     * @throws DuplicatePatientException if an equivalent patient already exists.
     */
    public void addPatientToQueue(Patient p) throws DuplicatePatientException {
        requireNonNull(p);
        Patient patient = syncWithMasterTagList(p);
        visitingQueue.add(patient);
    }

    public UniquePatientVisitingQueue getVisitingQueue() {
        return visitingQueue;
    }

    /**
     * Remove {@code tag} from {@code patient}
     */
    private void removeTagFromPerson (Tag tag, Patient patient) throws PatientNotFoundException {
        Set<Tag> personTags = new HashSet<>(patient.getTags());

        if (personTags.remove(tag)) {
            Patient updatedPatient = new Patient(patient.getName(), patient.getPhone(),
                    patient.getEmail(), patient.getAddress(), personTags);
            try {
                updatePerson(patient, updatedPatient);
            } catch (DuplicatePatientException dpe) {
                throw new PatientNotFoundException();
            }
        }
    }

    /**
     * Loops through all persons in this {@code Imdb} and removes {@code tag}.
     */
    public void removeTag(Tag tag) {
        for (Patient patient : persons) {
            try {
                removeTagFromPerson(tag, patient);
            } catch (PatientNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Patient> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Imdb // instanceof handles nulls
                && this.persons.equals(((Imdb) other).persons)
                && this.tags.equalsOrderInsensitive(((Imdb) other).tags)
                && this.appointments.equalsOrderInsensitive(((Imdb) other).appointments));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags, appointments);
    }
}
