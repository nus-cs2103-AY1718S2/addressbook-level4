package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AppointmentDeletedEvent;
import seedu.address.commons.events.model.NewAppointmentAddedEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.ClashingAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.email.Template;
import seedu.address.model.email.exceptions.DuplicateTemplateException;
import seedu.address.model.email.exceptions.TemplateNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Appointment> filteredAppointments;
    private final FilteredList<Template> filteredTemplates;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAppointments = new FilteredList<>(this.addressBook.getAppointmentList());
        filteredTemplates = new FilteredList<>(this.addressBook.getTemplateList());
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

    //@@author jlks96
    /** Raises an event to indicate a new appointment has been added */
    private void indicateAppointmentAdded(Appointment appointment) {
        raise(new NewAppointmentAddedEvent(appointment));
    }

    /** Raises an event to indicate an appointment has been deleted */
    private void indicateAppointmentDeleted(ObservableList<Appointment> appointments) {
        raise(new AppointmentDeletedEvent(appointments));
    }
    //@@author
    private void indicateTemplateAdded(Template template) {
        //raise(new NewTemplateAddedEvent(template)); TO IMPLEMENT
    }
    //@@author ng95junwei


    //@@author

    //=========== Person Mutators =============================================================

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    //@@author jlks96
    @Override
    public synchronized void deletePersons(List<Person> targets) throws PersonNotFoundException {
        addressBook.removePersons(targets);
        indicateAddressBookChanged();
    }
    //@@author

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

    //=========== Appointment Mutators =============================================================
    //@@author jlks96
    @Override
    public synchronized void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
        addressBook.removeAppointment(target);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateAppointmentDeleted(getFilteredAppointmentList());
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addAppointment(Appointment appointment)
            throws DuplicateAppointmentException, ClashingAppointmentException {
        addressBook.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateAppointmentAdded(appointment);
        indicateAddressBookChanged();
    }
    //@@author
    //========== Template Mutators ==================================================================
    //@@author ng95junwei
    @Override
    public synchronized void addTemplate(Template template) throws DuplicateTemplateException {
        addressBook.addTemplate(template);
        updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        indicateTemplateAdded(template);
        indicateAddressBookChanged();
    }
    //@@author
    //========== Template Accessors =================================================================
    //@@author ng95junwei
    @Override
    public ObservableList<Template> getAllTemplates() {
        return addressBook.getAllTemplates().asObservableList();
    }

    @Override
    public Template selectTemplate(String search) throws TemplateNotFoundException {
        return addressBook.getAllTemplates().search(search);
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

    @Override
    public void updateFilteredTemplateList(Predicate<Template> predicate) {
        requireNonNull(predicate);
        filteredTemplates.setPredicate(predicate);
    }

    //=========== Filtered Appointment List Accessors =============================================================
    //@@author jlks96
    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointments);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
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
                && filteredPersons.equals(other.filteredPersons)
                && filteredAppointments.equals(other.filteredAppointments);
    }

}
