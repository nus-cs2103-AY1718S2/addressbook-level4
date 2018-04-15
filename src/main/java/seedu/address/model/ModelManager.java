package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.ReloadCalendarEvent;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.exception.BadDataException;
import seedu.address.model.exception.InvalidPasswordException;
import seedu.address.model.exception.InvalidUsernameException;
import seedu.address.model.exception.MultipleLoginException;
import seedu.address.model.exception.UserLogoutException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.skill.Skill;
import seedu.address.model.skill.UniqueSkillList;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final List<Appointment> appointments;
    private final FilteredList<Job> filteredJobs;

    private AccountsManager accountsManager;
    private Optional<Account> user;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, Account account) {
        super();
        requireAllNonNull(addressBook, userPrefs, account);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        appointments = new ArrayList<Appointment>(this.addressBook.getAppointmentList());
        filteredJobs = new FilteredList<>(this.addressBook.getJobList());
        this.accountsManager = new AccountsManager(account);
        this.user = Optional.empty();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Account());
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

    /** Raises an event to indicate the Calendar has changed */
    private void indicateCalendarChanged() {
        raise(new ReloadCalendarEvent(addressBook.getAppointmentList()));
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
    public void deleteSkill(Skill t) throws PersonNotFoundException, DuplicatePersonException,
            UniqueSkillList.DuplicateSkillException {
        addressBook.removeSkill(t);
        indicateAddressBookChanged();
    }

    //@@author kush1509
    @Override
    public synchronized void addJob(Job job) throws DuplicateJobException {
        addressBook.addJob(job);
        updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateJob(Job target, Job editedJob)
            throws DuplicateJobException, JobNotFoundException {
        requireAllNonNull(target, editedJob);
        addressBook.updateJob(target, editedJob);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteJob(Job target) throws JobNotFoundException {
        addressBook.removeJob(target);
        indicateAddressBookChanged();
    }

    //@@author Jason1im
    /**
     * Logs the user into the system.
     * @throws InvalidUsernameException
     * @throws InvalidPasswordException
     * @throws MultipleLoginException if a user is already logged in.
     */
    @Override
    public void login(String username, String password)
            throws InvalidUsernameException, InvalidPasswordException, MultipleLoginException {
        if (user.isPresent()) {
            throw new MultipleLoginException();
        } else {
            requireNonNull(accountsManager);
            Account user = accountsManager.login(username, password);
            setUser(user);
        }
    }

    /**
     * Logs the user out of the system.
     * @throws UserLogoutException
     */
    @Override
    public void logout() throws UserLogoutException {
        if (user.isPresent()) {
            setUser(null);
        } else {
            throw new UserLogoutException();
        }
    }

    @Override
    public void updateUsername(String newUsername) throws BadDataException {
        accountsManager.updateUsername(newUsername);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword)
        throws InvalidPasswordException, BadDataException {
        accountsManager.updatePassword(oldPassword, newPassword);
    }

    private void setUser(Account account) {
        user = user.ofNullable(account);
    }

    @Override
    public boolean isLoggedIn() { return user.isPresent(); }

    //@@author trafalgarandre
    @Override
    public synchronized void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
        addressBook.addAppointment(appointment);
        indicateAddressBookChanged();
        indicateCalendarChanged();
    }

    @Override
    public synchronized void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
        addressBook.removeAppointment(target);
        indicateAddressBookChanged();
        indicateCalendarChanged();
    }

    @Override
    public void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireAllNonNull(target, editedAppointment);
        addressBook.updateAppointment(target, editedAppointment);
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

    //@@author kush1509
    //=========== Filtered Job List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Jobs} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Job> getFilteredJobList() {
        return FXCollections.unmodifiableObservableList(filteredJobs);
    }

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
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
                && filteredJobs.equals(other.filteredJobs)
                && appointments.equals(other.appointments);
    }

    //=========== Filtered Appointment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public List<Appointment> getAppointmentList() {
        return appointments;
    }
}
