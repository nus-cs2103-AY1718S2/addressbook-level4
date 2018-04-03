package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.AppointmentEntry;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyImdb {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Patient> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the appointment list.
     * This list will not contain any duplicate appointment.
     */
    ObservableList<AppointmentEntry> getAppointmentEntryList();

    /**
     * Returns an unmodifiable view of the patient queue.
     * This list will not contain any duplicate patient in the queue.
     */
    ObservableList<Patient> getUniquePatientQueue();

    /**
     * Returns an unmodifiable view of the patient queue. no
     * This list will not contain any duplicate patient in the queue.
     */
    ObservableList<Integer> getUniquePatientQueueNo();
}
