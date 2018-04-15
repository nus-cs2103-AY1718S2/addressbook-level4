package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.report.Report;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Job> PREDICATE_SHOW_ALL_JOBS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Interview> PREDICATE_SHOW_ALL_INTERVIEWS = unused -> true;

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

    /** Returns an unmodifiable view of the filtered job list */
    ObservableList<Job> getFilteredJobList();

    /** Returns an unmodifiable view of the filtered interview list */
    ObservableList<Report> getReportHistory();

    /** Returns an unmodifiable view of the filtered interview list */
    ObservableList<Interview> getFilteredInterviewList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /* Adds the given job. */
    void addJob(Job j) throws DuplicateJobException;

    /** Deletes the given job. */
    void deleteJob(Job target) throws JobNotFoundException;

    /**
     * Replaces the given job {@code target} with {@code editedJob}.
     *
     * @throws DuplicateJobException if updating the job's details causes the job to be equivalent to
     *      another existing person in the list.
     * @throws JobNotFoundException if {@code target} could not be found in the list.
     */
    void updateJob(Job target, Job editedJob)
            throws DuplicateJobException, JobNotFoundException;

    /**
     * Updates the filter of the filtered job list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredJobList(Predicate<Job> predicate);


    /* Adds the give interview. */
    void addInterview(Interview interview) throws DuplicateInterviewException;

    /** Deletes the given interview. */
    void deleteInterview(Interview target) throws InterviewNotFoundException;

    /**
     * Updates the filter of the filtered interview list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredInterviewList(Predicate<Interview> predicate);

    /** Adds the given report */
    void addReport(Report report);

    /**
     * Updates the {@code population} of the report.
     */
    void updateReport(Tag population);

    /**
     * refresh the report of the AddressBook.
     */
    void refreshReport();

    /**
     * Get the report of the AddressBook.
     */
    Report getReport();
}
