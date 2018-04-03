package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.skill.Skill;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withSkill("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        try {
            addressBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Adds a new {@code Job} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withJob(Job job) {
        try {
            addressBook.addJob(job);
        } catch (DuplicateJobException dpe) {
            throw new IllegalArgumentException("job is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code skillName} into a {@code Skill} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withSkill(String skillName) {
        try {
            addressBook.addSkill(new Skill(skillName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("skillName is expected to be valid.");
        }
        return this;
    }

    //@@author trafalgarandre
    /**
     * Parses {@code Appointment} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withAppointment(Appointment appointment) {
        try {
            addressBook.addAppointment(appointment);
        } catch (DuplicateAppointmentException dae) {
            throw new IllegalArgumentException("Appointment is expected to be unique");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
