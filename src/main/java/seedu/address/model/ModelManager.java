package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentDependencyNotEmptyException;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;
import seedu.address.model.petpatient.exceptions.PetDependencyNotEmptyException;
import seedu.address.model.petpatient.exceptions.PetPatientNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Appointment> filteredAppointments;
    private final FilteredList<PetPatient> filteredPetPatients;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAppointments = new FilteredList<>(this.addressBook.getAppointmentList());
        filteredPetPatients = new FilteredList<>(this.addressBook.getPetPatientList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(Person target)
            throws PersonNotFoundException, PetDependencyNotEmptyException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException, DuplicateNricException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public Person getPersonWithNric(Nric ownerNric) {
        for (Person p : addressBook.getPersonList()) {
            if (p.getNric().equals(ownerNric)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public synchronized void deletePetPatient(PetPatient target)
            throws PetPatientNotFoundException, AppointmentDependencyNotEmptyException {
        addressBook.removePetPatient(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized List<PetPatient> deletePetPatientDependencies(Person target) {
        List<PetPatient> petPatients = addressBook.removeAllPetPatientDependencies(target);
        indicateAddressBookChanged();
        return petPatients;
    }

    @Override
    public synchronized List<Appointment> deleteAppointmentDependencies(PetPatient target) {
        List<Appointment> dependenciesDeleted = addressBook.removeAllAppointmentDependencies(target);
        indicateAddressBookChanged();
        return dependenciesDeleted;
    }

    @Override
    public synchronized void addPetPatient(PetPatient petPatient) throws DuplicatePetPatientException {
        addressBook.addPetPatient(petPatient);
        updateFilteredPetPatientList(PREDICATE_SHOW_ALL_PET_PATIENTS);
        indicateAddressBookChanged();
    }

    @Override
    public PetPatient getPetPatientWithNricAndName(Nric ownerNric, PetPatientName petPatientName) {
        for (PetPatient p : addressBook.getPetPatientList()) {
            if (p.getOwner().equals(ownerNric) && p.getName().equals(petPatientName)) {
                return p;
            }
        }
        return null;
    }

    //@@author chialejing
    @Override
    public ArrayList<PetPatient> getPetPatientsWithNric(Nric ownerNric) {
        ArrayList<PetPatient> petPatientArrayList = new ArrayList<>();
        for (PetPatient p : addressBook.getPetPatientList()) {
            if (p.getOwner().equals(ownerNric)) {
                petPatientArrayList.add(p);
            }
        }
        return petPatientArrayList;
    }

    @Override
    public ArrayList<Appointment> getAppointmentsWithNric(Nric ownerNric) {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        for (Appointment a : addressBook.getAppointmentList()) {
            if (a.getOwnerNric().equals(ownerNric)) {
                appointmentArrayList.add(a);
            }
        }
        return appointmentArrayList;
    }

    @Override
    public ArrayList<Appointment> getAppointmentsWithNricAndPetName(Nric ownerNric, PetPatientName petPatientName) {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        for (Appointment a : addressBook.getAppointmentList()) {
            if (a.getOwnerNric().equals(ownerNric) && a.getPetPatientName().equals(petPatientName)) {
                appointmentArrayList.add(a);
            }
        }
        return appointmentArrayList;
    }

    @Override
    public Appointment getClashingAppointment(LocalDateTime dateTime) {
        for (Appointment a : addressBook.getAppointmentList()) {
            if (a.getDateTime().equals(dateTime)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void updatePetPatient(PetPatient target, PetPatient editedPetPatient)
            throws DuplicatePetPatientException, PetPatientNotFoundException {
        requireAllNonNull(target, editedPetPatient);

        addressBook.updatePetPatient(target, editedPetPatient);
        indicateAddressBookChanged();
    }

    @Override
    public void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireAllNonNull(target, editedAppointment);

        addressBook.updateAppointment(target, editedAppointment);
        indicateAddressBookChanged();
    }

    //@@author
    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
        addressBook.removeAppointment(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addAppointment(Appointment appointment)
            throws DuplicateAppointmentException, DuplicateDateTimeException {
        addressBook.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTag(Tag tag) {
        addressBook.removeTag(tag);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Appointment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointments);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    //=========== Filtered Pet Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code PetPatient} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<PetPatient> getFilteredPetPatientList() {
        return FXCollections.unmodifiableObservableList(filteredPetPatients);
    }

    @Override
    public void updateFilteredPetPatientList(Predicate<PetPatient> predicate) {
        requireNonNull(predicate);
        filteredPetPatients.setPredicate(predicate);
    }

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
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredAppointments.equals(other.filteredAppointments)
                && filteredPetPatients.equals(other.filteredPetPatients);
    }

}
