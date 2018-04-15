package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAppointments.MEETING;
import static seedu.address.testutil.TypicalJobs.MARKETING_INTERN;
import static seedu.address.testutil.TypicalJobs.SOFTWARE_ENGINEER;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.skill.Skill;
import seedu.address.model.skill.UniqueSkillList;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getSkillList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        List<Skill> newSkills = new ArrayList<>(ALICE.getSkills());
        List<Job> newJobs = Arrays.asList(SOFTWARE_ENGINEER, MARKETING_INTERN);
        List<Appointment> newAppointments = new ArrayList<>();
        newAppointments.add(MEETING);
        AddressBookStub newData = new AddressBookStub(newPersons, newJobs, newSkills, newAppointments);
        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void removeTag_withValidAddressBook()
            throws DuplicatePersonException, PersonNotFoundException, UniqueSkillList.DuplicateSkillException {
        addressBook.addPerson(BENSON);
        List<Skill> skillList = new ArrayList<Skill>();
        skillList.add(new Skill("owesMoney"));
        addressBook.removeSkill(new Skill("friends"));
        assertEquals(addressBook.getSkillList(), skillList);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getJobList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getJobList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getSkillList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and skills lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Skill> skills = FXCollections.observableArrayList();
        private final ObservableList<Job> jobs = FXCollections.observableArrayList();
        private final List<Appointment> appointments = new ArrayList<Appointment>();

        AddressBookStub(Collection<Person> persons, Collection<Job> jobs, Collection<? extends Skill> skills,
                        Collection<? extends Appointment> appointments) {
            this.persons.setAll(persons);
            this.skills.setAll(skills);
            this.jobs.setAll(jobs);
            this.appointments.addAll(appointments);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Skill> getSkillList() {
            return skills;
        }

        @Override
        public List<Appointment> getAppointmentList() {
            return appointments;
        }

        public ObservableList<Job> getJobList() {
            return jobs;
        }
    }

}
