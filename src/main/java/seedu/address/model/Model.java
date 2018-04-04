package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.UniqueCalendarEventList;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.GroupNotFoundException;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    Predicate<CalendarEvent> PREDICATE_SHOW_ALL_CALENDAR_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

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

    void updateFilteredCalendarEventList(Predicate<CalendarEvent> predicate);

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Deletes given Order
     */
    void deleteOrder(Order targetOrder) throws OrderNotFoundException;

    /**
     * Deletes given Group
     * @throws GroupNotFoundException if {@code targetGroup} could not be found in group list.
     */
    void deleteGroup(Group targetGroup) throws GroupNotFoundException;

    /**
     * Deletes given Preference
     * @throws PreferenceNotFoundException if {@code targetPreference} could not be found in preference list.
     */
    void deletePreference(Preference targetPreference) throws PreferenceNotFoundException;

    /**
     * Adds order to list of orders.
     */
    void addOrderToOrderList(Order orderToAdd) throws UniqueOrderList.DuplicateOrderException;

    /**
     * Adds event to list of calendar events.
     */
    void addCalendarEvent(CalendarEvent toAdd) throws UniqueCalendarEventList.DuplicateCalendarEventException;

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<CalendarEvent> getFilteredCalendarEventList();

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     *
     * @throws UniqueOrderList.DuplicateOrderException if updating the order's details causes the order to be
     *  equivalent to another existing order in the list.
     * @throws OrderNotFoundException if {@code target} could not be found in the list.
     */
    void updateOrder(Order target, Order editedOrder)
            throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException;

    /**
     * Updates the order status of {@code target} with {@code orderStatus}.
     */
    void updateOrderStatus(Order target, String orderStatus)
            throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException;
}
