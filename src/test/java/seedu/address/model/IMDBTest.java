package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.UNUSED_TAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
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
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.IMDBBuilder;
import seedu.address.testutil.PatientBuilder;

public class IMDBTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final IMDB IMDB = new IMDB();
    private final IMDB IMDBWithAmyAndBob =
            new IMDBBuilder().withPerson(AMY).withPerson(BOB).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), IMDB.getPersonList());
        assertEquals(Collections.emptyList(), IMDB.getTagList());
    }

    @Test
    public void removeTag_unusedTag_addressBookUnchanged() {
        IMDBWithAmyAndBob.removeTag(new Tag(UNUSED_TAG));

        IMDB expectedIMDB = new IMDBBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(IMDBWithAmyAndBob, expectedIMDB);

    }

    @Test
    public void removeTag_multiplePersonsTag_tagRemoved() {
        IMDBWithAmyAndBob.removeTag(new Tag(VALID_TAG_FRIEND));

        Patient amyWithoutFriendTag = new PatientBuilder(AMY).build();
        Patient bobWithoutFriendTag = new PatientBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        IMDB expectedIMDB = new IMDBBuilder().withPerson(amyWithoutFriendTag)
                .withPerson(bobWithoutFriendTag).build();

        assertEquals(IMDBWithAmyAndBob, expectedIMDB);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        IMDB.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        IMDB newData = getTypicalAddressBook();
        IMDB.resetData(newData);
        assertEquals(newData, IMDB);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Patient> newPatients = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        IMDBStub newData = new IMDBStub(newPatients, newTags);

        thrown.expect(AssertionError.class);
        IMDB.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        IMDB.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        IMDB.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyIMDB whose patients and tags lists can violate interface constraints.
     */
    private static class IMDBStub implements ReadOnlyIMDB {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        IMDBStub(Collection<Patient> patients, Collection<? extends Tag> tags) {
            this.patients.setAll(patients);
            this.tags.setAll(tags);
            this.appointments.setAll(appointments);
        }

        @Override
        public ObservableList<Patient> getPersonList() {
            return patients;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }
    }

}
