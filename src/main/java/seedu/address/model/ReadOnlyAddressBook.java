package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;

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
     * Returns an unmodifiable view of the groups list.
     * This list will not contain any duplicate groups.
     */
    ObservableList<Group> getGroupList();

    /**
     * Returns an unmodifiable view of the preferences list.
     * This list will not contain any duplicate preferences.
     */
    ObservableList<Preference> getPreferenceList();

    /**
     * Returns an unmodifiable view of the orders list.
     * This list will not contain any duplicate orders.
     */
    ObservableList<Order> getOrderList();

    /**
     * Returns an unmodifiable view of the calendar events list.
     * This list will not contain any duplicate calendar events.
     */
    ObservableList<CalendarEvent> getEventList();
}
