package seedu.address.model;

import java.util.ArrayList;

import javafx.collections.ObservableList;

import seedu.address.model.insuranceCalendar.AppointmentEntry;
import seedu.address.model.insuranceCalendar.InsuranceCalendar;
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
     * Returns an unmodifiable view of the appointment entry list.
     * This list will not contain any duplicate lists.
     */
    ArrayList<AppointmentEntry> getMyCalendarEntries();

    /**
     * Returns an unmodifiable view of Insurance calendar.
     *
     */
    InsuranceCalendar getMyCalendar();




}
