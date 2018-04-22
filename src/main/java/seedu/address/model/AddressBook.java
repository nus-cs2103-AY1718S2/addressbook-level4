package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
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
import seedu.address.model.person.PersonRole;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.PersonsListIsEmptyException;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.UniquePetList;
import seedu.address.model.pet.exceptions.DuplicatePetException;
import seedu.address.model.pet.exceptions.PetNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.vettechnician.VetTechnician;
import seedu.address.model.vettechnician.exceptions.TechnicianHasExistingAppointmentException;
import seedu.address.model.vettechnician.exceptions.VetTechnicianNotFoundException;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueAppointmentList appointments;
    private final UniquePetList pets;

    private final ObservableList<ClientOwnPet> clientPetAssociations;

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
        pets = new UniquePetList();

        clientPetAssociations = FXCollections.observableArrayList();
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

    //@@author md-azsa
    /**
     * Sorts the persons list lexicographically.
     * @throws PersonsListIsEmptyException
     */
    public void sortClientList() throws PersonsListIsEmptyException {
        if (persons.isEmpty()) {
            throw new PersonsListIsEmptyException();
        } else {
            this.persons.sort();
        }
    }
    //@@author

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setAppointments(List<Appointment> appointments) throws DuplicateAppointmentException,
            AppointmentCloseToPreviousException, AppointmentCloseToNextException {
        this.appointments.setAppointments(appointments);
    }

    //@@author md-azsa
    /**
     * Sets the list of pets to contain data
     */
    public void setPets(List<Pet> pets) throws DuplicatePetException {
        this.pets.setPets(pets);
    }

    /**
     * Sorts the pet list lexicographically.
     */
    public void sortPetList() throws ClientPetAssociationListEmptyException {
        if (clientPetAssociations.isEmpty()) {
            throw new ClientPetAssociationListEmptyException();
        } else {
            this.clientPetAssociations.sort((ClientOwnPet a, ClientOwnPet b) ->
                    a.getPet().getPetName().toString().toLowerCase()
                    .compareTo(b.getPet().getPetName().toString().toLowerCase()));
        }
    }

    /**
     * Sorts the appointment internal list.
     *
     * @throws AppointmentListIsEmptyException
     */
    public void sortAppointmentList() throws AppointmentListIsEmptyException {
        if (appointments.isEmpty()) {
            throw new AppointmentListIsEmptyException();
        } else {
            appointments.sort();
        }
    }
    //@@author

    public void setClientPetAssociations(List<ClientOwnPet> associations) {
        this.clientPetAssociations.setAll(associations);
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
            throw new AssertionError("AddressBooks should not have duplicate persons");
        }

        List<Appointment> syncedAppointmentList = newData.getAppointmentList().stream()
                .map(Appointment::new).collect(Collectors.toList());
        try {
            setAppointments(syncedAppointmentList);
        } catch (DuplicateAppointmentException e) {
            throw new AssertionError("Program should not have duplicate appointments");
        } catch (AppointmentCloseToPreviousException ape) {
            throw new AssertionError("Program should not schedule appointments"
                    + "when the previous one has not ended yet");
        } catch (AppointmentCloseToNextException ape) {
            throw new AssertionError("Program should not schedule appointments"
                    + "when too close to the next one");
        }

        List<Pet> syncedPetList = newData.getPetList().stream()
                .map(this::syncWithMasterPetTagList)
                .collect(Collectors.toList());
        try {
            setPets(syncedPetList);
        } catch (DuplicatePetException e) {
            throw new AssertionError("Program should not have duplicate pets");
        }

        setClientPetAssociations(newData.getClientPetAssociations());
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

    //@@author
    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the client's details causes the client to be equivalent to
     *                                  another existing client in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException, ClientHasExistingPetException,
            ClientHasExistingAppointmentException, TechnicianHasExistingAppointmentException {
        requireNonNull(editedPerson);

        // check able to update
        if (target instanceof Client && editedPerson instanceof VetTechnician) {
            ListIterator<ClientOwnPet> copIterator = clientPetAssociations.listIterator();
            while (copIterator.hasNext()) {
                ClientOwnPet association = copIterator.next();
                if (association.getClient().equals(target)) {
                    throw new ClientHasExistingPetException();
                }
            }
            Iterator<Appointment> i = appointments.iterator();
            while (i.hasNext()) {
                Appointment app = i.next();
                if (app.getClientOwnPet() != null && app.getClientOwnPet().getClient().equals(target)) {
                    throw new ClientHasExistingAppointmentException();
                }
            }
        }
        if (target instanceof VetTechnician && editedPerson instanceof Client) {
            Iterator<Appointment> i = appointments.iterator();
            while (i.hasNext()) {
                Appointment app = i.next();
                if (app.getVetTechnician() != null && app.getVetTechnician().equals(target)) {
                    throw new TechnicianHasExistingAppointmentException();
                }
            }
        }

        // update objects
        if (target instanceof Client && editedPerson instanceof Client) {
            ListIterator<ClientOwnPet> copIterator = clientPetAssociations.listIterator();
            while (copIterator.hasNext()) {
                ClientOwnPet association = copIterator.next();
                if (association.getClient().equals(target)) {
                    copIterator.remove();
                    copIterator.add(new ClientOwnPet((Client) editedPerson, association.getPet()));
                }
            }
            Iterator<Appointment> i = appointments.iterator();
            while (i.hasNext()) {
                Appointment app = i.next();
                if (app.getClientOwnPet() != null && app.getClientOwnPet().getClient().equals(target)) {
                    app.setClientOwnPet(new ClientOwnPet((Client) editedPerson, app.getClientOwnPet().getPet()));
                }
            }
        } else {
            Iterator<Appointment> i = appointments.iterator();
            while (i.hasNext()) {
                Appointment app = i.next();
                if (app.getVetTechnician() != null && app.getVetTechnician().equals(target)) {
                    app.removeVetTech();
                    app.setOptionalVetTech(Optional.of((VetTechnician) editedPerson));
                }
            }
        }

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any client
        // in the client list.
        persons.setPerson(target, syncedEditedPerson);
    }

    //@@author
    /**
     * Updates the master tag list to include tags in {@code person} that are not in the list.
     *
     * @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     * list.
     */
    private Person syncWithMasterTagList(Person person) {
        Person syncedPerson;

        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        if (person.getRole().equals(PersonRole.CLIENT_ROLE)) {
            syncedPerson = new Client(person.getName(), person.getPhone(), person.getEmail(),
                    person.getAddress(), correctTagReferences);
        } else {
            syncedPerson = new VetTechnician(person.getName(), person.getPhone(), person.getEmail(),
                    person.getAddress(), correctTagReferences);
        }
        return syncedPerson;
    }

    //@@author
    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        ArrayList<ClientOwnPet> toRemoveClientPetAssociationList = new ArrayList<>();
        ArrayList<Pet> toRemovePetList = new ArrayList<>();

        // Adds the key and its pets to the toRemoveList
        for (ClientOwnPet cop : clientPetAssociations) {
            if (cop.getClient().equals(key)) {
                toRemoveClientPetAssociationList.add(cop);
                toRemovePetList.add(cop.getPet());
            }
        }

        for (Iterator<Appointment> iterator = appointments.iterator(); iterator.hasNext();) {
            Appointment appt = iterator.next();
            if (appt.getClientOwnPet() == null) {
                continue;
            } else if (appt.getClientOwnPet().getClient().equals(key)) {
                appt.setClientOwnPetToNull();
            }
        }

        // Removes the key and its pets.
        clientPetAssociations.removeAll(toRemoveClientPetAssociationList);
        pets.getInternalList().removeAll(toRemovePetList);
        if (!persons.remove(key)) {
            throw new PersonNotFoundException();
        }

        // Removes vet from any existing appointment
        for (Appointment appointment : appointments) {
            appointment.getOptionalVetTechnician().ifPresent(technician -> {
                try {
                    if (technician.equals(key)) {
                        removeVetFromAppointment(appointment);
                    }
                } catch (AppointmentNotFoundException e) {
                    throw new AssertionError("Appointment should be found");
                } catch (DuplicateAppointmentException e) {
                    throw new AssertionError("Program should not have duplicate appointments");
                } catch (VetTechnicianNotFoundException e) {
                    throw new AssertionError("VetTechnician should be found");
                }
            });
        }
        return true;
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// appointment-level operations

    //@@author md-azsa
    /**
     * Unschedules an appointment
     */
    public void unscheduleAppointment(Appointment key) throws AppointmentListIsEmptyException,
            AppointmentNotFoundException {
        if (appointments.isEmpty()) {
            throw new AppointmentListIsEmptyException();
        } else {
            appointments.remove(key);
        }
    }
    //@@author

    //@@author Godxin-functional
    /**
     * Schedule an appointment to the address book.
     *
     * @throws DuplicateAppointmentException if an equivalent person already exists.
     */
    public void scheduleAppointment(Appointment a) throws DuplicateAppointmentException,
            AppointmentCloseToPreviousException, AppointmentCloseToNextException {
        appointments.add(a);
        appointments.sort();
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code rescheduleAppointment}.
     *
     * @throws DuplicateAppointmentException if updating the appointment's details causes this appointment to clash with
     *      another existing appointment in the list.
     * @throws AppointmentNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateAppointment(Appointment target, Appointment rescheduleAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireNonNull(rescheduleAppointment);

        appointments.setAppointment(target, rescheduleAppointment);
    }
    //@@author
    //// pet-level operations

    //@@author md-azsa
    /**
     * Adds a pet to the program.
     * Also checks the new pet's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the pet to point to those in {@link #tags}.
     *
     * @throws DuplicatePetException if an equivalent pet already exists.
     */
    public void addPet(Pet p) throws DuplicatePetException {
        Pet pet = syncWithMasterPetTagList(p);
        pets.add(pet);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws PetNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePet(Pet key) throws PetNotFoundException, ClientPetAssociationNotFoundException {
        boolean found = false;
        for (Appointment appointment : appointments) {
            if (appointment.getClientOwnPet() != null
                    && appointment.getClientOwnPet().getPet().equals(key)) {
                appointment.setClientOwnPetToNull();
            }
        }
        for (ClientOwnPet association : clientPetAssociations) {
            if (association.getPet().equals(key)) {
                clientPetAssociations.remove(association);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ClientPetAssociationNotFoundException();
        }
        if (pets.remove(key)) {
            return true;
        } else {
            throw new PetNotFoundException();
        }
    }

    /**
     * Updates the master tag list to include tags in {@code person} that are not in the list.
     *
     * @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     * list.
     */
    private Pet syncWithMasterPetTagList(Pet pet) {
        Pet syncedPet;

        final UniqueTagList petTags = new UniqueTagList(pet.getTags());
        tags.mergeFrom(petTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        petTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        syncedPet = new Pet(pet.getPetName(), pet.getPetAge(), pet.getPetGender(), correctTagReferences);
        return syncedPet;
    }
    //@@author

    //// Association methods

    //@@author jonathanwj
    /**
     * Associates pet to client
     *
     * @throws ClientAlreadyOwnsPetException
     * @throws PetAlreadyHasOwnerException
     */
    public void addPetToClient(Pet pet, Client client)
            throws ClientAlreadyOwnsPetException, PetAlreadyHasOwnerException {
        ClientOwnPet toAdd = new ClientOwnPet(client, pet);

        if (!clientPetAssociations.contains(toAdd)) {
            if (hasOwner(pet)) {
                throw new PetAlreadyHasOwnerException();
            }
            clientPetAssociations.add(toAdd);
        } else {
            throw new ClientAlreadyOwnsPetException();
        }

    }

    //@@author
    //@@author md-azsa
    /**
     * Finds the pet and adds the appointment
     */
    public void addAppointmentToPet(Appointment appointment, Pet pet) throws PetAlreadyHasAppointmentException,
            ClientPetAssociationNotFoundException, AppointmentNotFoundException, DuplicateAppointmentException,
            AppointmentHasBeenTakenException {

        boolean isAdded = false;
        boolean isPresent = false;

        if (clientPetAssociations.isEmpty()) {
            throw new ClientPetAssociationNotFoundException();
        }
        if (appointment.getClientOwnPet() != null) {
            throw new AppointmentHasBeenTakenException();
        }

        for (ClientOwnPet a : clientPetAssociations) {
            if (a.getPet().equals(pet)) {
                isPresent = true;
                if (appointment.getClientOwnPet() == null) {
                    Appointment appointmentCopy = new Appointment(appointment);
                    appointmentCopy.setClientOwnPet(a);
                    appointments.setAppointment(appointment, appointmentCopy);
                    isAdded = true;
                }
            }
        }

        if (!isPresent) {
            throw new ClientPetAssociationNotFoundException();
        }
        if (isPresent && !isAdded) {
            throw new PetAlreadyHasAppointmentException();
        }
    }

    /**
     * Removes the appointment from a pet
     */
    public void removeAppointmentFromPet(Appointment appointment) throws
            AppointmentNotFoundException, DuplicateAppointmentException, AppointmentDoesNotHavePetException {
        if (!appointments.contains(appointment)) {
            throw new AppointmentNotFoundException();
        } else {
            Appointment appointmentCopy = new Appointment(appointment);

            if (appointmentCopy.getClientOwnPet() == null) {
                throw new AppointmentDoesNotHavePetException();
            } else {
                appointmentCopy.setClientOwnPetToNull();
                appointments.setAppointment(appointment, appointmentCopy);
            }
        }
    }
    //@@author

    //@@author jonathanwj
    /**
     * Returns true if specified pet has an owner
     */
    private boolean hasOwner(Pet pet) {
        for (ClientOwnPet a : clientPetAssociations) {
            if (a.getPet().equals(pet)) {
                return true;
            }
        }
        return false;
    }

    //@@author jonathanwj
    /**
     * Removes association from pet and client
     *
     * @throws ClientPetAssociationNotFoundException
     */
    public void removePetFromClient(Pet pet, Client client) throws ClientPetAssociationNotFoundException {
        ClientOwnPet toRemove = new ClientOwnPet(client, pet);
        if (clientPetAssociations.contains(toRemove)) {
            clientPetAssociations.remove(toRemove);
        } else {
            throw new ClientPetAssociationNotFoundException();
        }
    }

    //@@author jonathanwj
    /**
     * Adds vet technician to appointment
     */
    public void addVetTechToAppointment(VetTechnician technician, Appointment appointment)
            throws AppointmentNotFoundException, AppointmentAlreadyHasVetTechnicianException,
            DuplicateAppointmentException {
        if (!appointments.contains(appointment)) {
            throw new AppointmentNotFoundException();
        }
        if (appointment.getOptionalVetTechnician().isPresent()) {
            throw new AppointmentAlreadyHasVetTechnicianException();
        }
        Appointment appointmentCopy = new Appointment(appointment);
        appointmentCopy.setVetTech(technician);
        appointments.setAppointment(appointment, appointmentCopy);
    }

    //@@author jonathanwj
    /**
     * Removes a vet technician from the given appointment
     */
    public void removeVetFromAppointment(Appointment apptToRemoveVetFrom)
            throws AppointmentNotFoundException, DuplicateAppointmentException,
            VetTechnicianNotFoundException {
        if (!appointments.contains(apptToRemoveVetFrom)) {
            throw new AppointmentNotFoundException();
        }
        if (!apptToRemoveVetFrom.getOptionalVetTechnician().isPresent()) {
            throw new VetTechnicianNotFoundException();
        }
        Appointment appointmentCopy = new Appointment(apptToRemoveVetFrom);
        appointmentCopy.removeVetTech();
        appointments.setAppointment(apptToRemoveVetFrom, appointmentCopy);
    }

    //// util methods
    //@@author
    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags "
                + pets.asObservableList().size() + " pets, "
                + appointments.asObservableList().size() + " appointments, "
                + clientPetAssociations.size() + " clientpetassoc ";
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
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asObservableList();
    }

    @Override
    public ObservableList<Pet> getPetList() {
        return pets.asObservableList();
    }

    @Override
    public ObservableList<ClientOwnPet> getClientPetAssociations() {
        return clientPetAssociations;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.appointments.equals(((AddressBook) other).appointments)
                && this.pets.equals(((AddressBook) other).pets);
    }

    //@@author jonathanwj
    @Override
    public ObservableList<Client> getClientList() {
        ObservableList<Client> clientList = EasyBind.map(getPersonList(), (person) -> {
            if (person.isClient()) {
                return (Client) person;
            } else {
                return null;
            }
        });
        clientList = FXCollections.unmodifiableObservableList(clientList).filtered(Objects::nonNull);
        return clientList;
    }

    //@@author jonathanwj
    @Override
    public ObservableList<VetTechnician> getVetTechnicianList() {
        ObservableList<VetTechnician> technicianList = EasyBind.map(getPersonList(), (person) -> {
            if (!person.isClient()) {
                return (VetTechnician) person;
            } else {
                return null;
            }
        });
        technicianList = FXCollections.unmodifiableObservableList(technicianList).filtered(Objects::nonNull);
        return technicianList;
    }

    //@@author
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags, appointments, pets);
    }
}

