package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.interview.Interview;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;
import seedu.address.model.report.Report;
import seedu.address.model.tag.Tag;

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
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the jobs list.
     * This list will not contain any duplicate jobs.
     */
    ObservableList<Job> getJobList();

    /**
     * Returns an unmodifiable view of the interviews list.
     * This list will not contain any duplicate interviews.
     */
    ObservableList<Interview> getInterviewList();

    /**
     * Returns an unmodifiable view of the reports list.
     * This list will not contain any duplicate reports;
     */
    ObservableList<Report> getReportList();
}
