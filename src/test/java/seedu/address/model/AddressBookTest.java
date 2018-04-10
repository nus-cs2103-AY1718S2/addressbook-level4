package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersonsAndAppointments.ALICE;
import static seedu.address.testutil.TypicalPersonsAndAppointments.ALICE_APPT;
import static seedu.address.testutil.TypicalPersonsAndAppointments.getTypicalAddressBook;

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
import seedu.address.model.email.Template;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
        assertEquals(Collections.emptyList(), addressBook.getAppointmentList());
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
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Appointment> newAppointments = Arrays.asList(ALICE_APPT);
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newAppointments);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    //@@author jlks96
    @Test
    public void resetData_withDuplicateAppointments_throwsAssertionError() {
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Appointment> newAppointments = Arrays.asList(ALICE_APPT, ALICE_APPT); // Repeat ALICE_APPT twice
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newAppointments);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }
    //@@author

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    //@@author jlks96
    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getAppointmentList().remove(0);
    }
    //@@author

    /**
     * A stub ReadOnlyAddressBook whose persons, tags and appointment lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Template> templates = FXCollections.observableArrayList();

        AddressBookStub(
                Collection<Person> persons, Collection<? extends Tag> tags, Collection<Appointment> appointments) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.appointments.setAll(appointments);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        @Override
        public ObservableList<Template> getTemplateList() {
            return templates;
        }
    }

}
