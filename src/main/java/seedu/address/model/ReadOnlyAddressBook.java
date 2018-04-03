package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;
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
     * This list will not contain any duplicate appointments.
     */
    List<Appointment> getAppointmentList();

    /**
     * Returns an unmodifiable view of the jobs list.
     * This list will not contain any duplicate jobs.
     */
    ObservableList<Job> getJobList();
}
