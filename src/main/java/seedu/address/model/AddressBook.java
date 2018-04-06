package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.job.Job;
import seedu.address.model.job.UniqueJobList;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.skill.Skill;
import seedu.address.model.skill.UniqueSkillList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueAppointmentList appointments;
    private final UniquePersonList persons;
    private final UniqueSkillList skills;
    private final UniqueJobList jobs;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        skills = new UniqueSkillList();
        appointments = new UniqueAppointmentList();
        jobs = new UniqueJobList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons, Jobs and Skills in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setSkills(Set<Skill> skills) {
        this.skills.setSkills(skills);
    }

    public void setAppointments(List<Appointment> appointments) throws DuplicateAppointmentException {
        this.appointments.setAppointments(appointments);
    }

    public void setJobs(List<Job> jobs) throws DuplicateJobException {
        this.jobs.setJobs(jobs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setSkills(new HashSet<>(newData.getSkillList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncPersonWithMasterSkillList)
                .collect(Collectors.toList());
        List<Appointment> syncedAppointmentList = newData.getAppointmentList().stream()
                .collect(Collectors.toList());
        List<Job> syncedJobList = newData.getJobList().stream()
                .map(this::syncJobWithMasterSkillList)
                .collect(Collectors.toList());
        try {
            setAppointments(syncedAppointmentList);
            setPersons(syncedPersonList);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("AddressBooks should not have duplicate persons");
        } catch (DuplicateAppointmentException e) {
            throw new AssertionError("Calendar should not have duplicate appointments");
        }

        try {
            setJobs(syncedJobList);
        } catch (DuplicateJobException e) {
            throw new AssertionError("AddressBooks should not have duplicate jobs");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's skills and updates {@link #skills} with any new skills found,
     * and updates the Skill objects in the person to point to those in {@link #skills}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncPersonWithMasterSkillList(p);
        // TODO: the skills master list will be updated even though the below line fails.
        // This can cause the skills master list to have additional skills that are not tagged to any person
        // in the person list.
        persons.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s skill list will be updated with the skills of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncPersonWithMasterSkillList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncPersonWithMasterSkillList(editedPerson);
        // TODO: the skills master list will be updated even though the below line fails.
        // This can cause the skills master list to have additional skills that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
    }

    /**
     *  Updates the master skill list to include skills in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every skill in this person points to a
     *  Skill object in the master
     *  list.
     */
    private Person syncPersonWithMasterSkillList(Person person) {
        final UniqueSkillList personSkills = new UniqueSkillList(person.getSkills());
        skills.mergeFrom(personSkills);

        // Create map with values = skill object references in the master list
        // used for checking person skill references
        final Map<Skill, Skill> masterSkillObjects = new HashMap<>();
        skills.forEach(skill -> masterSkillObjects.put(skill, skill));

        // Rebuild the list of person skills to point to the relevant skills in the master skill list.
        final Set<Skill> correctSkillReferences = new HashSet<>();
        personSkills.forEach(skill -> correctSkillReferences.add(masterSkillObjects.get(skill)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getCurrentPosition(), person.getCompany(), person.getProfilePicture(), correctSkillReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException {
        if (persons.contains(key)) {
            return persons.remove(key);
        } else {
            throw new PersonNotFoundException();
        }
    }

    //// skill-level operations

    public void addSkill(Skill t) throws UniqueSkillList.DuplicateSkillException {
        skills.add(t);
    }

    /**
     * Remove skill {@code t} from everyone in the Addressbook
     * @throws UniqueSkillList.DuplicateSkillException
     * @throws PersonNotFoundException
     * @throws DuplicatePersonException
     */
    public void removeSkill(Skill t)
            throws UniqueSkillList.DuplicateSkillException, PersonNotFoundException, DuplicatePersonException {
        for (Person person: persons) {

            // cannot remove skill from initSkillSet since initSkillSet is unmodifiableSet
            // create new Skill Set manually
            Set<Skill> initSkillSet = person.getSkills();
            UniqueSkillList afterRemovedSkillSet = new UniqueSkillList();
            for (Skill skill : initSkillSet) {
                if (!skill.equals(t)) {
                    afterRemovedSkillSet.add(skill);
                }
            }
            updatePerson(person, new Person(person.getName(),
                    person.getPhone(), person.getEmail(), person.getAddress(), person.getCurrentPosition(),
                    person.getCompany(), person.getProfilePicture(), afterRemovedSkillSet.toSet()));
        }
        skills.remove(t);
    }

    //// appointment-level operations
    //@@author trafalgarandre
    /**
     * Adds an appointment to the address book.
     *
     *
     * @throws DuplicateAppointmentException if an equivalent appointment already exists.
     */
    public void addAppointment(Appointment a) throws DuplicateAppointmentException {
        appointments.add(a);
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * @throws DuplicateAppointmentException if updating the appointment's details causes the appointment to be
     * equivalent to another existing appointment in the list.
     * @throws AppointmentNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireNonNull(editedAppointment);

        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws AppointmentNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeAppointment(Appointment key) throws AppointmentNotFoundException {
        if (appointments.remove(key)) {
            return true;
        } else {
            throw new AppointmentNotFoundException();
        }
    }


    //// job-level operations

    /**
     * Adds a job to the address book.
     * Also checks the new person's skills and updates {@link #skills} with any new skills found,
     * and updates the Skill objects in the person to point to those in {@link #skills}.
     *
     * @throws DuplicateJobException if an equivalent job already exists.
     */
    public void addJob(Job j) throws DuplicateJobException {
        Job job = syncJobWithMasterSkillList(j);
        // TODO: the skills master list will be updated even though the below line fails.
        // This can cause the skills master list to have additional skills that are not tagged to any person
        // in the person list.
        jobs.add(job);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws JobNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeJob(Job key) throws JobNotFoundException {
        if (jobs.contains(key)) {
            return jobs.remove(key);
        } else {
            throw new JobNotFoundException();
        }
    }

    /**
     *  Updates the master skill list to include skills in {@code job} that are not in the list.
     *  @return a copy of this {@code job} such that every job in this person points to a Job object in the master
     *  list.
     */
    private Job syncJobWithMasterSkillList(Job job) {
        final UniqueSkillList jobSkills = new UniqueSkillList(job.getSkills());
        skills.mergeFrom(jobSkills);

        // Create map with values = skill object references in the master list
        // used for checking job skill references
        final Map<Skill, Skill> masterSkillObjects = new HashMap<>();
        skills.forEach(skill -> masterSkillObjects.put(skill, skill));

        // Rebuild the list of job skills to point to the relevant skills in the master skill list.
        final Set<Skill> correctSkillReferences = new HashSet<>();
        jobSkills.forEach(skill -> correctSkillReferences.add(masterSkillObjects.get(skill)));
        return new Job(job.getPosition(), job.getTeam(), job.getLocation(),
                job.getNumberOfPositions(), correctSkillReferences);
    }

    /// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + jobs.asObservableList().size() + " jobs, "
                + skills.asObservableList().size() +  " skills" + appointments.asList().size() + " appointments";
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Skill> getSkillList() {
        return skills.asObservableList();
    }

    @Override
    public List<Appointment> getAppointmentList() {
        return appointments.asList();
    }

    public ObservableList<Job> getJobList() {
        return jobs.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.skills.equalsOrderInsensitive(((AddressBook) other).skills)
                && this.appointments.equals(((AddressBook) other).appointments)
                && this.jobs.equals(((AddressBook) other).jobs));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, skills, appointments, jobs);
    }
}
