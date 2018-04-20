package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AppointmentChangedEvent;
import seedu.address.commons.events.model.ImdbChangedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentEntry;
import seedu.address.model.appointment.DateTime;
import seedu.address.model.appointment.UniqueAppointmentEntryList;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Imdb imdb;
    private final FilteredList<Patient> filteredPatients;
    /**
     * Initializes a ModelManager with the given Imdb and userPrefs.
     */
    public ModelManager(ReadOnlyImdb addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.imdb = new Imdb(addressBook);
        filteredPatients = new FilteredList<>(this.imdb.getPersonList());
    }

    public ModelManager() {
        this(new Imdb(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyImdb newData) {
        imdb.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyImdb getImdb() {
        return imdb;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ImdbChangedEvent(imdb));
    }

    //@@author Kyholmes
    private void indicateAppointmentChanged(Patient patient) {
        raise(new AppointmentChangedEvent(patient, imdb));
    }

    //@@author
    @Override
    public synchronized void deletePerson(Patient target) throws PatientNotFoundException {
        imdb.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Patient patient) throws DuplicatePatientException {
        imdb.addPerson(patient);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Patient target, Patient editedPatient)
            throws DuplicatePatientException, PatientNotFoundException {
        requireAllNonNull(target, editedPatient);

        imdb.updatePerson(target, editedPatient);
        indicateAddressBookChanged();
    }

    @Override
    /**
     * Removes {@code tag} from all Persons
     */
    public void deleteTag(Tag tag) {
        imdb.removeTag(tag);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code Imdb}
     */
    @Override
    public ObservableList<Patient> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPatients);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    //@@author Kyholmes
    @Override
    public Patient getPatientFromList(Predicate<Patient> predicate) {
        filteredPatients.setPredicate(predicate);
        if (filteredPatients.size() > 0) {
            Patient targetPatient = filteredPatients.get(0);
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return targetPatient;
        }
        return null;
    }

    @Override
    public Patient getPatientFromListByIndex(Index targetIndex) {
        return filteredPatients.get(targetIndex.getZeroBased());
    }

    @Override
    public int getPatientSourceIndexInList(int targetIndex) {
        return filteredPatients.getSourceIndex(targetIndex) + 1;
    }

    //=========== Visiting Queue Accessors =============================================================

    @Override
    public boolean checkIfPatientInQueue(Patient patientToDelete) {
        int targetIndex = filteredPatients.getSourceIndex(filteredPatients.indexOf(patientToDelete));
        if (imdb.getUniquePatientQueueNo().contains(targetIndex)) {
            return true;
        }

        return false;
    }

    @Override
    public ObservableList<Integer> getPatientListIndexInQueue() {
        return imdb.getUniquePatientQueueNo();
    }

    @Override
    public synchronized Patient addPatientToQueue(Index targetIndex) throws DuplicatePatientException {
        requireNonNull(targetIndex);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        int patientIndex = filteredPatients.getSourceIndex(targetIndex.getZeroBased());
        imdb.addPatientToQueue(patientIndex);
        indicateAddressBookChanged();

        return filteredPatients.get(targetIndex.getZeroBased());
    }

    @Override
    public synchronized Patient removePatientFromQueue() throws PatientNotFoundException {
        int patientIndexToRemove = imdb.removePatientFromQueue();
        indicateAddressBookChanged();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return filteredPatients.get(patientIndexToRemove);
    }

    @Override
    public Patient removePatientFromQueueByIndex(Index targetIndex) throws PatientNotFoundException {
        imdb.removePatientFromQueueByIndex(targetIndex.getZeroBased());
        indicateAddressBookChanged();
        return filteredPatients.get(targetIndex.getZeroBased());
    }

    @Override
    public ObservableList<Patient> getVisitingQueue() {
        return imdb.getUniquePatientQueue();
    }

    //=========== Appointment List Accessors =============================================================

    @Override
    public synchronized void deletePatientAppointment(Patient patient, DateTime targetAppointmentDateTime) throws
            UniqueAppointmentList.AppoinmentNotFoundException {
        requireAllNonNull(patient, targetAppointmentDateTime);
        Appointment targetAppointment = new Appointment(targetAppointmentDateTime.toString());
        imdb.deletePatientAppointment(patient, targetAppointment);
        indicateAppointmentChanged(patient);
    }

    @Override
    public ObservableList<AppointmentEntry> getAppointmentEntryList() {
        return imdb.getAppointmentEntryList();
    }

    @Override
    public synchronized void addPatientAppointment(Patient patient, DateTime dateTime) throws
            UniqueAppointmentList.DuplicatedAppointmentException,
            UniqueAppointmentEntryList.DuplicatedAppointmentEntryException {
        requireAllNonNull(patient, dateTime);
        imdb.addAppointment(patient, dateTime);
        indicateAppointmentChanged(patient);
    }
    //@@author
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return imdb.equals(other.imdb)
                && filteredPatients.equals(other.filteredPatients);
    }

}
