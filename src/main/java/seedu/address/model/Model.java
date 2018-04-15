package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
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
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Job> PREDICATE_SHOW_ALL_JOBS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Deletes the given job. */
    void deleteJob(Job target) throws JobNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /**
     * Replaces the given job {@code target} with {@code editedJob}.
     *
     * @throws DuplicateJobException if updating the person's details causes the job to be equivalent to
     *      another existing job in the list.
     * @throws JobNotFoundException if {@code target} could not be found in the list.
     */
    void updateJob(Job target, Job editedJob)
            throws DuplicateJobException, JobNotFoundException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Delete specified skill from everyone in the address book */
    void deleteSkill(Skill t) throws PersonNotFoundException, DuplicatePersonException,
            UniqueSkillList.DuplicateSkillException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //@@author Jason1im
    /**
     * Logs the user into contactHeRo.
     * @throws InvalidUsernameException if username is invalid.
     * @throws InvalidPasswordException if the password is invalid.
     * @throws MultipleLoginException if a user is already logged in.
     */
    void login(String username, String password) throws InvalidUsernameException,
            InvalidPasswordException, MultipleLoginException;

    /**
     * Logs the user out of contactHeRo
     * @throws UserLogoutException if no user is login to the system.
     */
    void logout() throws UserLogoutException;

    /**
     * Changes the password of the user account
     * @param oldPassword
     * @param newPassword
     * @throws InvalidPasswordException if password is invalid.
     * @throws BadDataException if password does not meet contraints.
     */
    void updatePassword(String oldPassword, String newPassword)
            throws InvalidPasswordException, BadDataException;

    /**
     * Changes the username of the user account.
     * @throws BadDataException is username does not meet contraints.
     */
    void updateUsername(String oldUsername) throws BadDataException;

    /**
     * Checks if the user has logged in.
     * @return true if user is logged in.
     */
    boolean isLoggedIn();

    //@@author
    /** Adds the given person */
    void addJob(Job job) throws DuplicateJobException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Job> getFilteredJobList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredJobList(Predicate<Job> predicate);

    /** Deletes the given appointment. */
    void deleteAppointment(Appointment target) throws AppointmentNotFoundException;

    /** Adds the given appointment */
    void addAppointment(Appointment appointment) throws DuplicateAppointmentException;

    /**
     * Replaces the given appointment {@code target} with {@code editedAppointment}.
     *
     * @throws DuplicateAppointmentException if updating the appointment's details causes the appointment
     * to be equivalent to another existing appointment in the list.
     * @throws AppointmentNotFoundException if {@code target} could not be found in the list.
     */
    void updateAppointment(Appointment target, Appointment editedPerson)
            throws DuplicateAppointmentException, AppointmentNotFoundException;

    /** Returns an unmodifiable view of the filtered appointment list */
    List<Appointment> getAppointmentList();

}
