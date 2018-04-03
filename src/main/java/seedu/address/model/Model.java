package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.exception.DuplicateUsernameException;
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

    /**
     * Returns AccountsManager.
     */
    AccountsManager getAccountsManager();

    /**
     * Register a new account for user.
     * @throws DuplicateUsernameException if {@param username} is already in used.
     */
    void register(String username, String password) throws DuplicateUsernameException;

    /** Adds the given person */
    void addJob(Job job) throws DuplicateJobException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Job> getFilteredJobList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredJobList(Predicate<Job> predicate);

}
