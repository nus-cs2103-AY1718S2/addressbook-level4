package seedu.address.model;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import seedu.address.model.notification.Notification;
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

    //@@author IzHoBX
    /**
     * Returns an unmodifiable view of the notification list.
     */
    LinkedList<Notification> getNotificationsList();

    int getNextId();
    //@@author

    String getPassword();


}
