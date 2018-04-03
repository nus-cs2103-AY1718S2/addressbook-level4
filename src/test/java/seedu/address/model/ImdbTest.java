package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.UNUSED_TAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

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
import seedu.address.model.appointment.AppointmentEntry;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ImdbBuilder;
import seedu.address.testutil.PatientBuilder;

public class ImdbTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Imdb imdb = new Imdb();
    private final Imdb imdbWithAmyAndBob =
            new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), imdb.getPersonList());
        assertEquals(Collections.emptyList(), imdb.getTagList());
    }

    @Test
    public void removeTag_unusedTag_addressBookUnchanged() {
        imdbWithAmyAndBob.removeTag(new Tag(UNUSED_TAG));

        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(imdbWithAmyAndBob, expectedImdb);

    }

    @Test
    public void removeTag_multiplePersonsTag_tagRemoved() {
        imdbWithAmyAndBob.removeTag(new Tag(VALID_TAG_FRIEND));

        Patient amyWithoutFriendTag = new PatientBuilder(AMY).build();
        Patient bobWithoutFriendTag = new PatientBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        Imdb expectedImdb = new ImdbBuilder().withPerson(amyWithoutFriendTag)
                .withPerson(bobWithoutFriendTag).build();

        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        imdb.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Imdb newData = getTypicalAddressBook();
        imdb.resetData(newData);
        assertEquals(newData, imdb);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Patient> newPatients = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Integer> newQueue = new ArrayList<>();
        ImdbStub newData = new ImdbStub(newPatients, newTags, newQueue);

        thrown.expect(AssertionError.class);
        imdb.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        imdb.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        imdb.getTagList().remove(0);
    }

    @Test
    public void getVisitingQueue_modifyQueue_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        imdb.getUniquePatientQueue().remove(0);
    }

    @Test
    public void getAppointmentEntryList_modifyList_throwsUnsupportedOperationException() {
        AppointmentEntry entry = new AppointmentEntry(new Appointment("3/4/2017 1030"),
                "test");
        thrown.expect(UnsupportedOperationException.class);
        imdb.getAppointmentEntryList().add(entry);
    }

    @Test
    public void addPatientToQueue_queueUpdate() throws DuplicatePatientException {
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

        expectedImdb.addPatientToQueue(1);

        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void addPatientToQueue_duplicateIndex() throws DuplicatePatientException {
        imdbWithAmyAndBob.addPatientToQueue(1);

        thrown.expect(DuplicatePatientException.class);

        imdbWithAmyAndBob.addPatientToQueue(1);
    }

    @Test
    public void removePatientFromQueue_queueUpdate() throws DuplicatePatientException, PatientNotFoundException {
        imdbWithAmyAndBob.addPatientToQueue(1);
        Imdb expectedImdb = new ImdbBuilder().withPerson(AMY).withPerson(BOB).build();

        expectedImdb.addPatientToQueue(1);

        imdbWithAmyAndBob.removePatientFromQueue();
        expectedImdb.removePatientFromQueue();

        assertEquals(imdbWithAmyAndBob, expectedImdb);
    }

    @Test
    public void removePatientFromQueue_emptyQueue() throws PatientNotFoundException {
        thrown.expect(PatientNotFoundException.class);
        imdbWithAmyAndBob.removePatientFromQueue();
    }

    /**
     * A stub ReadOnlyImdb whose patients and tags lists can violate interface constraints.
     */
    private static class ImdbStub implements ReadOnlyImdb {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<AppointmentEntry> appointments = FXCollections.observableArrayList();
        private final ObservableList<Integer> visitingQueue = FXCollections.observableArrayList();

        ImdbStub(Collection<Patient> patients, Collection<? extends Tag> tags, Collection<Integer> queue) {
            this.patients.setAll(patients);
            this.tags.setAll(tags);
            this.visitingQueue.setAll(queue);
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
        public ObservableList<AppointmentEntry> getAppointmentEntryList() {
            return appointments;
        }

        @Override
        public ObservableList<Patient> getUniquePatientQueue() {
            return null;
        }

        @Override
        public ObservableList<Integer> getUniquePatientQueueNo() {
            return visitingQueue;
        }
    }

}
