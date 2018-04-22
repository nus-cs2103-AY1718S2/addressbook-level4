package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.logic.GetEmployeesRequestEvent;
import seedu.address.commons.events.logic.RequestToDeleteNotificationEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookPasswordChangedEvent;
import seedu.address.commons.events.model.NotificationAddedEvent;
import seedu.address.commons.events.model.RequestForNotificationCenterEvent;
import seedu.address.commons.events.model.ReturnedEmployeesEvent;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.NotificationNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.Photo;
import seedu.address.ui.NotificationCard;
import seedu.address.ui.NotificationCenter;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private NotificationCenter notificationCenter;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with Employees Tracker: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    /**
     * Deletes all records of a Person, including Notification related to it
     */
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        int targetId = target.getId();
        addressBook.removePerson(target);
        ensureNotificationCenterNonNull();
        if (notificationCenter != null) {
            notificationCenter.removeNotificationForPerson(targetId);
        }
        indicateAddressBookChanged();
    }

    //@@author IzHoBX

    /**
     * Ensures Notification Center is non-null. Attempts to assign notification center if so.
     */
    private void ensureNotificationCenterNonNull() {
        if (notificationCenter == null) {
            raise(new RequestForNotificationCenterEvent());
        }
    }
    @Override
    public synchronized void deleteNotification(String id, boolean deleteFromAddressBookOnly) throws
            NotificationNotFoundException {
        try {
            addressBook.deleteNotification(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!deleteFromAddressBookOnly) {
            try {
                ensureNotificationCenterNonNull();
                notificationCenter.deleteNotification(id);
            } catch (NullPointerException e) {
                logger.info("NullPointerException encountered when deleting notification for deleted employee");
            }
        }
        indicateAddressBookChanged();
    }

    @Override
    public NotificationCard deleteNotificationByIndex(Index targetIndex) throws NotificationNotFoundException {
        ensureNotificationCenterNonNull();
        addressBook.deleteNotification(notificationCenter.getIdByIndex(targetIndex));
        indicateAddressBookChanged();
        NotificationCard toDelete = notificationCenter.deleteNotificationByIndex(targetIndex);
        return toDelete;
    }

    private void indicateNotificationAdded(Notification e) {
        raise(new NotificationAddedEvent(e));
    }
    //@@author

    private void indicatePasswordChangedEvent(String p) {
        raise(new AddressBookPasswordChangedEvent(p, addressBook));
    }

    //@@author IzHoBX
    @Override
    public void addNotification(Notification e) {
        addressBook.addNotification(e);
        indicateAddressBookChanged();
        indicateNotificationAdded(e);
    }

    //@@author crizyli
    @Override
    public void setPassword(String password) {
        addressBook.setPassword(password);
        indicatePasswordChangedEvent(password);
    }

    public String getPassword() {
        return addressBook.getPassword();
    }
    //@@author

    public ObservableList<Photo> getPhotoList() {
        return addressBook.getPhotoList();
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
    public Person getPerson(int index) throws IndexOutOfBoundsException {
        return addressBook.getPersonList().get(index);
    }

    //@@author IzHoBX
    public String getNameById(String id) {
        return addressBook.findPersonById(Integer.parseInt(id)).getName().toString();
    }
    //@@author

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

    //@@author Yoochard
    @Override
    public void sort(String field) {
        addressBook.sort(field);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
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
                && filteredPersons.equals(other.filteredPersons);
    }

    //@@author IzHoBX
    @Subscribe
    private void handleRequestToDeleteNotificationEvent(RequestToDeleteNotificationEvent event) {
        try {
            deleteNotification(event.id, event.deleteFromAddressbookOnly);
        } catch (NotificationNotFoundException e) {
            e.printStackTrace();
            logger.info("Notification is not stored locally");
        }
    }

    @Override
    public void findAllSavedNotifications() {

        //schedule all notification
        for (Notification n: getAddressBook().getNotificationsList()) {
            indicateNotificationAdded(n);
        }
    }

    public void setNotificationCenter(NotificationCenter notificationCenter) {
        assert(notificationCenter != null);
        this.notificationCenter = notificationCenter;
    }

    public NotificationCenter getNotificationCenter() {
        return  notificationCenter;
    }

    //@@author
    //@@author crizyli
    @Subscribe
    public void handleGetEmployeesRequestEvent(GetEmployeesRequestEvent event) {
        EventsCenter.getInstance().post(new ReturnedEmployeesEvent(addressBook.getPersonList()));
    }
}
