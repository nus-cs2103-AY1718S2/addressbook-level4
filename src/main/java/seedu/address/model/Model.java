package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.NotificationNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.Photo;
import seedu.address.ui.NotificationCard;
import seedu.address.ui.NotificationCenter;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /** Returns a person given an index*/
    Person getPerson(int index) throws IndexOutOfBoundsException;

    //@@author IzHoBX
    /** Deletes a timetable entry given its id. */
    void deleteNotification(String id, boolean deleteFromAddressBookOnly) throws NotificationNotFoundException;

    /** Adds the given person */
    void addNotification(Notification e);
    //@@author IzHoBX

    //@@author crizyli
    /** Adds the given password */
    void setPassword(String e);

    /** Gets the password */
    String getPassword();
    //@@author

    ObservableList<Photo> getPhotoList();

    //@@author Yoochard
    /** Sort existing employees by any field in alphabetical order */
    void sort(String field);
    //@@author

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void findAllSavedNotifications();

    void setNotificationCenter(NotificationCenter notificationCenter);

    NotificationCenter getNotificationCenter();

    NotificationCard deleteNotificationByIndex(Index targetIndex) throws NotificationNotFoundException;
}
