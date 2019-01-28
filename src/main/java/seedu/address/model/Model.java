package seedu.address.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentDependencyNotEmptyException;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;
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
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;
    Predicate<PetPatient> PREDICATE_SHOW_ALL_PET_PATIENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException, PetDependencyNotEmptyException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException, DuplicateNricException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    void updatePetPatient(PetPatient target, PetPatient editedPetPatient)
            throws DuplicatePetPatientException, PetPatientNotFoundException;

    void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException;

    /** Removes the specific {@code tag} from all {@code persons} with that tag **/
    void deleteTag(Tag tag);

    /** Adds the given appointment */
    void addAppointment(Appointment appointment) throws DuplicateAppointmentException, DuplicateDateTimeException,
        ConcurrentAppointmentException, PastAppointmentException;

    /** Deletes the given appointment. */
    void deleteAppointment(Appointment target) throws AppointmentNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<PetPatient> getFilteredPetPatientList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    void updateFilteredPetPatientList(Predicate<PetPatient> predicate);

    void addPetPatient(PetPatient petPatient) throws DuplicatePetPatientException;

    /** Returns a person object that has the given {@code Nric}. */
    Person getPersonWithNric(Nric ownerNric);

    /** Returns a petpatient object that has the given {@code Nric} and {@code PetPatientName}. */
    PetPatient getPetPatientWithNricAndName(Nric ownerNric, PetPatientName petPatientName);

    /** Returns a list of tags used in the application. */
    List<Tag> getTagList();

    ArrayList<PetPatient> getPetPatientsWithNric(Nric ownerNric);

    ArrayList<Appointment> getAppointmentsWithNric(Nric ownerNric);

    ArrayList<Appointment> getAppointmentsWithNricAndPetName(Nric ownerNric, PetPatientName petPatientName);

    Appointment getClashingAppointment(LocalDateTime dateTime);

    /** Deletes the given pet. */
    void deletePetPatient(PetPatient target)
            throws PetPatientNotFoundException, AppointmentDependencyNotEmptyException;

    /** Deletes all pet dependencies. */
    List<PetPatient> deletePetPatientDependencies(Person key);

    /** Deletes all appointment dependencies. */
    List<Appointment> deleteAppointmentDependencies(PetPatient target);

    boolean hasConcurrentAppointment(LocalDateTime oldDateTime, LocalDateTime newDateTime);
}
