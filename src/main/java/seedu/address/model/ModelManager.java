package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.report.Proportion;
import seedu.address.model.report.Report;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final Tag defaultPopulation = new Tag("SEIntern");

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Job> filteredJobs;
    private Report report;
    private final FilteredList<Interview> filteredInterviews;
    private final FilteredList<Report> reportList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredJobs = new FilteredList<>(this.addressBook.getJobList());
        filteredInterviews = new FilteredList<>(this.addressBook.getInterviewList());
        reportList = new FilteredList<>(this.addressBook.getReportList());
        this.updateReport(defaultPopulation);
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
    // @@author anh2111
    @Override
    public synchronized void addReport(Report report) {
        addressBook.addReport(report);
        indicateAddressBookChanged();
    }
    // @@author
    //@@author ChengSashankh
    //=========== Filtered Job List Accessors =============================================================

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

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Job} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Job> getFilteredJobList() {
        return FXCollections.unmodifiableObservableList(filteredJobs);
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

    //=========== Report Accessors =============================================================
    // @@author anh2111
    @Override
    public Report getReport() {
        return report;
    }

    @Override
    public void updateReport(Tag population) {
        FilteredList<Person> allPersonList = new FilteredList<>(this.addressBook.getPersonList());
        Predicate<Person> personContainsPopulationTagPredicate =
            new Predicate<Person>() {
                @Override
                public boolean test(Person person) {
                    return person.getTags().stream()
                            .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, population.tagName));
                }
            };
        allPersonList.setPredicate(personContainsPopulationTagPredicate);

        Map<String, Pair<Integer, Integer>> counts = new HashMap<>();
        allPersonList.forEach((p) -> {
            Set<Tag> allTags = p.getTags();
            for (Tag t : allTags) {
                if (!t.tagName.equalsIgnoreCase(population.tagName)) {
                    counts.merge(t.tagName, new Pair<>(1, 1), (a, b) ->
                            new Pair(a.getKey() + b.getKey(), a.getValue() + b.getValue()));
                }
            }
        });

        List<Proportion> allProportions = new ArrayList<>();
        for (Map.Entry<String, Pair<Integer, Integer>> entry : counts.entrySet()) {
            allProportions.add(new Proportion(entry.getKey(), entry.getValue().getKey(), entry.getValue().getValue()));
        }

        report = new Report(population, allProportions, allPersonList.size());
    }

    @Override
    public void refreshReport() {
        this.updateReport(this.report.getPopulation());
    }
    // @@author
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
                && filteredJobs.equals(other.filteredJobs);
    }

    //@@author deeheenguyen
    //=========== Filtered Interview List Accessors =============================================================
    @Override
    public synchronized void addInterview(Interview interview) throws DuplicateInterviewException {
        addressBook.addInterview(interview);
        updateFilteredInterviewList(PREDICATE_SHOW_ALL_INTERVIEWS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteInterview(Interview target) throws InterviewNotFoundException {
        addressBook.removeInterview(target);
        indicateAddressBookChanged();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Interview} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Interview> getFilteredInterviewList() {
        return FXCollections.unmodifiableObservableList(filteredInterviews);
    }

    @Override
    public void updateFilteredInterviewList(Predicate<Interview> predicate) {
        requireNonNull(predicate);
        filteredInterviews.setPredicate(predicate);
    }
    //@@author
    // @@author anh2111
    /**
     * Returns an unmodifiable view of the list of {@code Report} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Report> getReportHistory() {
        reportList.setPredicate(new Predicate<Report>() {
            @Override
            public boolean test(Report oldReport) {
                return oldReport.getPopulation().tagName.equalsIgnoreCase(report.getPopulation().tagName);
            }
        });
        return FXCollections.unmodifiableObservableList(reportList);
    }
    // @@author
}
