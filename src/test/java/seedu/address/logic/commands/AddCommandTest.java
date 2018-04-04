package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Imdb;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyImdb;
import seedu.address.model.appointment.AppointmentEntry;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PatientBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Patient validPatient = new PatientBuilder().build();

        CommandResult commandResult = getAddCommandForPerson(validPatient, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPatient), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPatient), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Patient validPatient = new PatientBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPatient, modelStub).execute();
    }

    @Test
    public void equals() throws Exception {
        Patient alice = new PatientBuilder().withName("Alice").build();
        Patient bob = new PatientBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different patient -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given patient.
     */
    private AddCommand getAddCommandForPerson(Patient patient, Model model) {
        AddCommand command = new AddCommand(patient);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Patient patient) throws DuplicatePatientException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyImdb newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyImdb getImdb() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Patient target) throws PatientNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Patient target, Patient editedPatient)
                throws DuplicatePatientException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Patient> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public Patient addPatientToQueue(NameContainsKeywordsPredicate predicate) throws DuplicatePatientException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Patient removePatientFromQueue() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Patient> getVisitingQueue() {
            return null;    //update later
        }

        @Override
        public Patient getPatientFromList(Predicate<Patient> predicate) {
            return null;
        }

        @Override
        public boolean deletePatientAppointment(Patient patient, Index index) {
            return false;
        }

        @Override
        public ObservableList<AppointmentEntry> getAppointmentEntryList() {
            return null;
        }

        @Override
        public void addPatientAppointment(Patient patient, String dateTimeString) {

        }
    }

    /**
     * A Model stub that always throw a DuplicatePatientException when trying to add a patient.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(Patient patient) throws DuplicatePatientException {
            throw new DuplicatePatientException();
        }

        @Override
        public ReadOnlyImdb getImdb() {
            return new Imdb();
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Patient> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(Patient patient) throws DuplicatePatientException {
            requireNonNull(patient);
            personsAdded.add(patient);
        }

        @Override
        public ReadOnlyImdb getImdb() {
            return new Imdb();
        }
    }

}
