package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.testutil.TypicalAppointments.ALICE_APP;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
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
import seedu.address.model.person.Person;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

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
        List<Appointment> newAppointments = Arrays.asList(ALICE_APP);
        List<Tag> newAppointmentTags = new ArrayList<>(ALICE_APP.getAppointmentTags());
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newAppointments, newAppointmentTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    // Reused from https://github.com/se-edu/addressbook-level4/pull/790/files with minor modifications
    @Test
    public void removeUnusedTag_addressBookUnchanged() throws Exception {
        AddressBook addressBookAmyBob = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        addressBookAmyBob.removeTag(new Tag(VALID_TAG_UNUSED));

        AddressBook addressBookExpected = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        //should be equal as unused tag removed
        assertEquals(addressBookExpected, addressBookAmyBob);
    }

    // Reused from https://github.com/se-edu/addressbook-level4/pull/790/files with minor modifications
    @Test
    public void removeTag_multiplePersons_addressBookChanged() throws Exception {
        AddressBook addressBookAmyBob = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
        addressBookAmyBob.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook addressBookAfterChange =
                new AddressBookBuilder().withPerson(amyWithoutFriendTag).withPerson(bobWithoutFriendTag).build();

        assertEquals(addressBookAfterChange, addressBookAmyBob);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Tag> appointmentTags = FXCollections.observableArrayList();
        private final ObservableList<PetPatient> petPatients = FXCollections.observableArrayList();
        private final ObservableList<Tag> petPatientTags = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<? extends Tag> tags,
                        Collection<Appointment> appointments, Collection<? extends Tag> appointmentTags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.appointments.setAll(appointments);
            this.appointmentTags.setAll(appointmentTags);
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
        public ObservableList<PetPatient> getPetPatientList() {
            return petPatients;
        }
    }

}
