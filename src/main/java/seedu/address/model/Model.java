package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.appointment.AppointmentEntry;
import seedu.address.model.appointment.UniqueAppointmentEntryList;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyImdb newData);

    /** Returns the Imdb */
    ReadOnlyImdb getImdb();

    /** Deletes the given patient. */
    void deletePerson(Patient target) throws PatientNotFoundException;

    /** Adds the given patient */
    void addPerson(Patient patient) throws DuplicatePatientException;

    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     *
     * @throws DuplicatePatientException if updating the patient's details causes the patient to be equivalent to
     *      another existing patient in the list.
     * @throws PatientNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Patient target, Patient editedPatient)
            throws DuplicatePatientException, PatientNotFoundException;

    /** Removes {@code tag} from all Persons */
    void deleteTag(Tag tag);

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPersonList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Patient> predicate);

    /** Adds patient into visiting queue
     * @throws NullPointerException if {@code Patient} is null.*/
    Patient addPatientToQueue(NameContainsKeywordsPredicate predicate) throws DuplicatePatientException,
            PatientNotFoundException;

    /** Remove a patient from the visiting queue*/
    Patient removePatientFromQueue() throws PatientNotFoundException;

    /** Get patient visiting queue*/
    ObservableList<Patient> getVisitingQueue();

    /** Get Patient from the patient list */
    Patient getPatientFromList(Predicate<Patient> predicate);

    /** Delete a patient's appointment*/
    boolean deletePatientAppointment(Patient patient, Index index);

    /** Get appointment entries*/
    ObservableList<AppointmentEntry> getAppointmentEntryList();

    /** Delete a patient's appointment*/
    void addPatientAppointment(Patient patient, String dateTimeString) throws
            UniqueAppointmentList.DuplicatedAppointmentException,
            UniqueAppointmentEntryList.DuplicatedAppointmentEntryException;
}
