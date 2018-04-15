package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;

import seedu.address.model.interview.Interview;
import seedu.address.model.interview.UniqueInterviewList;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;
import seedu.address.model.job.Job;
import seedu.address.model.job.UniqueJobList;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.report.Report;
import seedu.address.model.report.UniqueReportList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueInterviewList interviews;
    private final UniqueJobList jobs;
    private final UniqueReportList reports;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        interviews = new UniqueInterviewList();
        jobs = new UniqueJobList();
        reports = new UniqueReportList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setJobs(List<Job> jobs) throws  DuplicateJobException {
        this.jobs.setJobs(jobs);
    }

    public void setInterviews(List<Interview> interviews) throws DuplicateInterviewException {
        this.interviews.setInterviews(interviews);
    }

    public void setReports(List<Report> reports) {
        this.reports.setReports(reports);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setPersons(syncedPersonList);
            setJobs(new ArrayList<Job>(newData.getJobList()));
            setInterviews(new ArrayList<Interview>(newData.getInterviewList()));
            setReports(new ArrayList<Report>(newData.getReportList()));
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        } catch (DuplicateJobException e) {
            throw new AssertionError("AddressBooks should not have duplicate job postings");
        } catch (DuplicateInterviewException e) {
            throw new AssertionError("AddressBooks should not have duplicate interviews ");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getRemark(), person.getLink(), person.getSkills(), correctTagReferences);
    }

    //@@author ChengSashankh
    /**
     *  Updates the master tag list to include tags in {@code job} that are not in the list.
     *  @return a copy of this {@code job} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Job syncWithMasterTagList(Job job) {
        final UniqueTagList jobTags = new UniqueTagList(job.getTags());
        tags.mergeFrom(jobTags);

        // Create map with values = tag object references in the master list
        // used for checking job tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        jobTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Job(
                job.getJobTitle(), job.getLocation(), job.getSkills(), correctTagReferences);
    }
    //@@author

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /***
     * Removes {@code tag} from the chosen {@code person} in the {@code AddressBook}
     *@throws PersonNotFoundException if the {@code person} is not found in the {@code AddressBook}
     */

    public void deleteTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> modifiedTags = new HashSet<Tag>(person.getTags());

        if (!modifiedTags.remove(tag)) {
            return;
        }

        Person modifiedPerson =
                new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                        person.getRemark(), person.getLink(), person.getSkills(), modifiedTags);

        try {
            updatePerson(person, modifiedPerson);
        } catch (DuplicatePersonException duplictePersonException) {
            throw new AssertionError("Modifying "
                    + "a person's tags only should not result in a duplicate. " + "See Person#equals(Object).");
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /***
     * Deletes chosen tag {@code tag} from all persons in this {@code AddressBook}
     *
     */
    public void deleteTag(Tag tag) throws NoSuchObjectException {
        try {
            for (Person person : persons) {
                deleteTagFromPerson(tag, person);
            }
        } catch (PersonNotFoundException personNotFoundException) {
            throw new AssertionError("Impossible: Person was found in AddressBook.");
        }

        if (!tagExistsInAddressBook(tag)) {
            tags.remove(tag);
        }
    }

    /***
     * Checks if {@code tag} is assigned to atleast one {@code person} in the {@code AddressBook}
     * @param tag
     * @return a boolean value indicating the presence of the tag somewhere on the list
     */
    public boolean tagExistsInAddressBook(Tag tag) {
        for (Person person : persons) {
            if (person.getTags().contains(tag)) {
                return true;
            }
        }
        return false;
    }

    //// interview-level operations
    //@@author deeheenguyen
    public void addInterview(Interview interview) throws DuplicateInterviewException {
        interviews.add(interview);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws InterviewNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeInterview(Interview key) throws InterviewNotFoundException {
        if (interviews.remove(key)) {
            return true;
        } else {
            throw new InterviewNotFoundException();
        }
    }
    //@@author
    //// job methods
    //@@author ChengSashankh

    /**
     * Adds a job to the address book.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addJob(Job j) throws DuplicateJobException {
        Job job = syncWithMasterTagList(j);
        jobs.add(job);
    }

    /**
     * Replaces the given job {@code target} in the list with {@code editedJob}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedJob}.
     *
     * @throws DuplicateJobException if updating the job's details causes the person to be equivalent to
     *      another existing job in the list.
     * @throws JobNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateJob(Job target, Job editedJob)
            throws DuplicateJobException, JobNotFoundException {
        requireNonNull(editedJob);
        Job syncedEditedJob = syncWithMasterTagList(editedJob);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        jobs.setJob(target, syncedEditedJob);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws JobNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeJob(Job key) throws JobNotFoundException {
        if (jobs.remove(key)) {
            return true;
        } else {
            throw new JobNotFoundException();
        }
    }

    //@@author

    //// report methods
    /**
     * Adds a report to the address book.
     *
     */
    public void addReport(Report r) {
        reports.add(r);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags"
                + reports.asObservableList().size() + " reports" + jobs.asObservableList().size() + " jobs.";
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }
    @Override
    public ObservableList<Job> getJobList() {
        return jobs.asObservableList();
    }
    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }
    @Override
    public ObservableList<Interview> getInterviewList() {
        return interviews.asObservableList();
    }

    public ObservableList<Report> getReportList() {
        return reports.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, jobs, reports, tags);
    }
}
