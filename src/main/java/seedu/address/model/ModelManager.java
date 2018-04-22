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
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewApptAvailableEvent;
import seedu.address.commons.events.ui.NewListAllDisplayAvailableEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentAlreadyHasVetTechnicianException;
import seedu.address.model.appointment.exceptions.AppointmentCloseToNextException;
import seedu.address.model.appointment.exceptions.AppointmentCloseToPreviousException;
import seedu.address.model.appointment.exceptions.AppointmentDoesNotHavePetException;
import seedu.address.model.appointment.exceptions.AppointmentHasBeenTakenException;
import seedu.address.model.appointment.exceptions.AppointmentListIsEmptyException;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.association.ClientOwnPet;
import seedu.address.model.association.exceptions.ClientAlreadyOwnsPetException;
import seedu.address.model.association.exceptions.ClientPetAssociationListEmptyException;
import seedu.address.model.association.exceptions.ClientPetAssociationNotFoundException;
import seedu.address.model.association.exceptions.PetAlreadyHasAppointmentException;
import seedu.address.model.association.exceptions.PetAlreadyHasOwnerException;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.ClientHasExistingAppointmentException;
import seedu.address.model.client.exceptions.ClientHasExistingPetException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.PersonsListIsEmptyException;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.exceptions.DuplicatePetException;
import seedu.address.model.pet.exceptions.PetNotFoundException;
import seedu.address.model.vettechnician.VetTechnician;
import seedu.address.model.vettechnician.exceptions.TechnicianHasExistingAppointmentException;
import seedu.address.model.vettechnician.exceptions.VetTechnicianNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Client> filteredClients;
    private final FilteredList<VetTechnician> filteredVetTechnicians;
    private final FilteredList<Pet> filteredPet;

    private final FilteredList<ClientOwnPet> filteredClientPetAssocation;
    private final FilteredList<Appointment> filteredAppointment;

    private Client displayClient = null;
    private ObservableList<Pet> displayPet = null;
    private ObservableList<Appointment> displayAppt = null;

    private int currList = 0;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredClients = new FilteredList<>(this.addressBook.getClientList());
        filteredVetTechnicians = new FilteredList<>(this.addressBook.getVetTechnicianList());
        filteredPet = new FilteredList<>((this.addressBook.getPetList()));
        filteredClientPetAssocation = new FilteredList<>(this.addressBook.getClientPetAssociations());
        filteredAppointment = new FilteredList<>((this.addressBook.getAppointmentList()));
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
        clearListAllPanel();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
        raise(new NewApptAvailableEvent(addressBook.toString()));
    }

    //Person

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
        if (displayClient != null && displayClient.equals(target)) {
            clearListAllPanel();
        }
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author Godxin-functional
    @Override
    public synchronized void scheduleAppointment(Appointment appointment) throws DuplicateAppointmentException,
            AppointmentCloseToPreviousException, AppointmentCloseToNextException {
        addressBook.scheduleAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENT);
        indicateAddressBookChanged();
    }

    //@@author

    //@@author md-azsa
    @Override
    public synchronized void unscheduleAppointment(Appointment appointment) throws
            AppointmentNotFoundException, AppointmentListIsEmptyException {
        addressBook.unscheduleAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENT);
        indicateAddressBookChanged();
        if (displayAppt != null && displayAppt.contains(appointment)) {
            displayAppt.remove(appointment);
            indicateListAllPanelChanged();
        }
    }

    //@@author

    //@@author Godxin-functional
    @Override
    public void updateAppointment(Appointment target, Appointment rescheduleAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireAllNonNull(target, rescheduleAppointment);
        addressBook.updateAppointment(target, rescheduleAppointment);
        indicateAddressBookChanged();
        if (displayAppt != null && displayAppt.contains(target)) {
            displayAppt.set(displayAppt.indexOf(target), rescheduleAppointment);
            indicateListAllPanelChanged();
        }
    }

    //@@author

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException, ClientHasExistingAppointmentException,
            ClientHasExistingPetException, TechnicianHasExistingAppointmentException {
        requireAllNonNull(target, editedPerson);
        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        clearListAllPanel();
    }

    //@@author md-azsa
    @Override
    public void sortClientList() throws PersonsListIsEmptyException {
        addressBook.sortClientList();
        indicateAddressBookChanged();
    }


    // Pet

    @Override
    public synchronized void addPet(Pet pet) throws DuplicatePetException {
        addressBook.addPet(pet);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deletePet(Pet target) throws PetNotFoundException, ClientPetAssociationNotFoundException {
        addressBook.removePet(target);
        indicateAddressBookChanged();
        clearListAllPanel();
    }
    //@@author

    // Association

    //@@author jonathanwj
    @Override
    public void addPetToClient(Pet pet, Client client)
            throws ClientAlreadyOwnsPetException, PetAlreadyHasOwnerException {
        requireAllNonNull(pet, client);
        addressBook.addPetToClient(pet, client);
        indicateAddressBookChanged();
        clearListAllPanel();
    }

    //@@author jonathanwj
    @Override
    public void removePetFromClient(Pet pet, Client client) throws ClientPetAssociationNotFoundException {
        requireAllNonNull(pet, client);
        addressBook.removePetFromClient(pet, client);
        indicateAddressBookChanged();
    }

    //@@author
    //@@author md-azsa
    @Override
    public void sortPetList() throws ClientPetAssociationListEmptyException {
        addressBook.sortPetList();
        indicateAddressBookChanged();
    }

    @Override
    public void addAppointmentToPet(Appointment appointment, Pet pet)
            throws PetAlreadyHasAppointmentException, ClientPetAssociationNotFoundException,
            AppointmentNotFoundException, DuplicateAppointmentException, AppointmentHasBeenTakenException {
        requireAllNonNull(appointment, pet);
        addressBook.addAppointmentToPet(appointment, pet);
        indicateAddressBookChanged();
        clearListAllPanel();
    }

    @Override
    public void removeAppointmentFromPet(Appointment appointment)
            throws AppointmentNotFoundException, DuplicateAppointmentException, AppointmentDoesNotHavePetException {
        requireNonNull(appointment);
        addressBook.removeAppointmentFromPet(appointment);
        indicateAddressBookChanged();
        clearListAllPanel();
    }
    //@@author

    //@@author jonathanwj
    @Override
    public void addVetTechToAppointment(VetTechnician technician, Appointment appointment)
            throws AppointmentAlreadyHasVetTechnicianException,
            DuplicateAppointmentException, AppointmentNotFoundException {
        requireAllNonNull(technician, appointment);
        addressBook.addVetTechToAppointment(technician, appointment);
        indicateAddressBookChanged();
        clearListAllPanel();
    }

    //@@author jonathanwj
    @Override
    public void removeVetTechFromAppointent(Appointment apptToRemoveVetTechFrom)
            throws DuplicateAppointmentException, AppointmentNotFoundException,
            VetTechnicianNotFoundException {
        requireNonNull(apptToRemoveVetTechFrom);
        addressBook.removeVetFromAppointment(apptToRemoveVetTechFrom);
        indicateAddressBookChanged();
        clearListAllPanel();
    }

    //=========== Filtered Person List Accessors =============================================================
    //@@author

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Pet} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Pet> getFilteredPetList() {
        return FXCollections.unmodifiableObservableList(filteredPet);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointment.setPredicate(predicate);
    }

    //Client

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return FXCollections.unmodifiableObservableList(filteredClients);
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    //Vet Technician

    /**
     * Returns an unmodifiable view of the list of {@code VetTechnician} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<VetTechnician> getFilteredVetTechnicianList() {
        return FXCollections.unmodifiableObservableList(filteredVetTechnicians);
    }

    @Override
    public void updateFilteredVetTechnicianList(Predicate<VetTechnician> predicate) {
        requireNonNull(predicate);
        filteredVetTechnicians.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPetList(Predicate<Pet> predicate) {
        requireNonNull(predicate);
        filteredPet.setPredicate(predicate);
    }

    @Override
    public void updateFilteredClientOwnPetAssocation(Predicate<ClientOwnPet> predicate) {
        requireNonNull(predicate);
        filteredClientPetAssocation.setPredicate(predicate);
    }

    //Association

    /**
     * Returns an unmodifiable view of the list of {@code ClientOwnPet} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ClientOwnPet> getFilteredClientPetAssociationList() {
        return FXCollections.unmodifiableObservableList(filteredClientPetAssocation);
    }

    // Appointment

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointment);
    }

    //@@author md-azsa
    @Override
    public void sortAppointmentList() throws AppointmentListIsEmptyException {
        addressBook.sortAppointmentList();
        indicateAddressBookChanged();
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
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredClients.equals(other.filteredClients)
                && filteredVetTechnicians.equals(other.filteredVetTechnicians)
                && filteredClientPetAssocation.equals(other.filteredClientPetAssocation);
    }

    @Override
    public void setCurrentList(int currList) {
        this.currList = currList;
    }

    @Override
    public int getCurrentList() {
        return this.currList;
    }

    //@@author purplepers0n
    @Override
    public void updateDetailsList(Client client, ObservableList<Pet> pets,
                                  ObservableList<Appointment> appointments) {
        displayClient = client;
        displayPet = pets;
        displayAppt = appointments;
    }

    @Override
    public Client getClientDetails() {
        return displayClient;
    }

    @Override
    public ObservableList<Pet> getClientPetList() {
        return displayPet;
    }

    @Override
    public ObservableList<Appointment> getClientApptList() {
        return displayAppt;
    }

    /**
     * Clears the list all panel
     */
    private void clearListAllPanel() {
        displayClient = null;
        displayPet = null;
        displayAppt = null;
        indicateListAllPanelChanged();
    }

    /**
     * Updates the list all panel for UI
     */
    private void indicateListAllPanelChanged() {
        raise(new NewListAllDisplayAvailableEvent(null));
    }
}
