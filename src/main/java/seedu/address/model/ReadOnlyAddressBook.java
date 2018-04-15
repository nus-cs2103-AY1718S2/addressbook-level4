package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Client;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the student list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getStudentList();

    /**
     * Returns an unmodifiable view of the tutors list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getTutorList();

    /**
     * Returns an unmodifiable view of the closed student list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getClosedStudentList();

    /**
     * Returns an unmodifiable view of the closed tutors list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getClosedTutorList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
