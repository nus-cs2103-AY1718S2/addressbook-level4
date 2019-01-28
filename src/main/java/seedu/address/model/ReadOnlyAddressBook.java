package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**

     * Returns an unmodifiable view of the appointments list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Appointment> getAppointmentList();

    /**

     * Returns an unmodifiable view of the pet patient list.
     * This list will not contain any duplicate pet patients.
     */
    ObservableList<PetPatient> getPetPatientList();
}
