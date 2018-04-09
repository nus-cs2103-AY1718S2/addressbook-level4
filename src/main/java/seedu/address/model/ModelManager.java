package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CalendarManagerChangedEvent;
import seedu.address.model.event.CalendarEntry;
import seedu.address.model.event.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.event.exceptions.DuplicateCalendarEntryException;
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
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<CalendarEntry> filteredEvents;
    private final CalendarManager calendarManager;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyCalendarManager calendarManager, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, calendarManager, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.calendarManager = new CalendarManager(calendarManager);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredOrders = new FilteredList<>(this.addressBook.getOrderList());
        filteredEvents = new FilteredList<>(this.calendarManager.getCalendarEntryList());
    }

    public ModelManager() {
        this(new AddressBook(), new CalendarManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData, ReadOnlyCalendarManager newCalendarData) {
        addressBook.resetData(newData);
        calendarManager.resetData(newCalendarData);
        indicateAddressBookChanged();
        indicateCalendarManagerChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyCalendarManager getCalendarManager() {
        return calendarManager;
    }

    /** Raises an event to indicate address book has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    /** Raises an event to indicate calendar manager has changed */
    private void indicateCalendarManagerChanged() {
        raise(new CalendarManagerChangedEvent(calendarManager));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteGroup(Group targetGroup) throws GroupNotFoundException {
        addressBook.removeGroup(targetGroup);
        indicateAddressBookChanged();
    }

    @Override
    public void deletePreference(Preference targetPreference) throws PreferenceNotFoundException {
        addressBook.removePreference(targetPreference);
        indicateAddressBookChanged();
    }

    //@@author amad-person
    @Override
    public void addOrderToOrderList(Order orderToAdd) throws UniqueOrderList.DuplicateOrderException {
        addressBook.addOrderToOrderList(orderToAdd);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteOrder(Order targetOrder) throws OrderNotFoundException {
        addressBook.deleteOrder(targetOrder);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        indicateAddressBookChanged();
    }
    //@@author

    //@@author SuxianAlicia
    @Override
    public void addCalendarEntry(CalendarEntry toAdd) throws DuplicateCalendarEntryException {
        calendarManager.addCalendarEntry(toAdd);
        updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
        indicateCalendarManagerChanged();
    }


    @Override
    public void deleteCalendarEntry(CalendarEntry entryToDelete) throws CalendarEntryNotFoundException {
        calendarManager.deleteCalendarEntry(entryToDelete);
        updateFilteredCalendarEventList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
        indicateCalendarManagerChanged();
    }

    //@@author

    //@@author amad-person
    @Override
    public void updateOrder(Order target, Order editedOrder)
        throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
        requireAllNonNull(target, editedOrder);

        addressBook.updateOrder(target, editedOrder);
        indicateAddressBookChanged();
    }
    //@@author

    @Override
    public void updateOrderStatus(Order target, String orderStatus)
            throws UniqueOrderList.DuplicateOrderException, OrderNotFoundException {
        addressBook.updateOrderStatus(target, orderStatus);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Order List Accessors =============================================================
    //@@author amad-person
    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return FXCollections.unmodifiableObservableList(filteredOrders);
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {

        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }
    //@@author

    // ========== Filtered Calendar Entry List Accessors ==================================
    //@@author SuxianAlicia
    @Override
    public ObservableList<CalendarEntry> getFilteredCalendarEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredCalendarEventList(Predicate<CalendarEntry> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public Calendar getCalendar() {
        return calendarManager.getCalendar();
    }

    //@@author
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && calendarManager.equals(other.calendarManager)
                && filteredPersons.equals(other.filteredPersons);
    }

}
