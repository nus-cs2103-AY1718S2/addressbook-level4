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

import com.calendarfx.model.CalendarSource;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.model.calendar.exceptions.AppointmentNotFoundException;
import seedu.address.model.calendar.exceptions.DuplicateAppointmentException;
import seedu.address.model.calendar.exceptions.EditAppointmentFailException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.AppointmentBuilder;
//@@author yuxiangSg
public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_appointmentEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        AddAppointmentCommandTest.ModelStubAcceptingAppointmentAdded modelStub =
                new AddAppointmentCommandTest.ModelStubAcceptingAppointmentAdded();

        AppointmentEntry validAppointment = new AppointmentBuilder().build();

        CommandResult commandResult = getAddCommandForAppointment(validAppointment, modelStub).execute();

        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentAdded);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() throws Exception {
        AddAppointmentCommandTest.ModelStub modelStub =
                new AddAppointmentCommandTest.ModelStubThrowingDuplicateAppointmentException();
        AppointmentEntry validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);

        getAddCommandForAppointment(validAppointment, modelStub).execute();
    }

    @Test
    public void equals() {
        AppointmentEntry john = new AppointmentBuilder().withTitle("John").build();
        AppointmentEntry josh = new AppointmentBuilder().withTitle("Josh").build();

        AddAppointmentCommand addJohnCommand = new AddAppointmentCommand(john);
        AddAppointmentCommand addJoshCommand = new AddAppointmentCommand(josh);

        // same object -> returns true
        assertTrue(addJohnCommand.equals(addJohnCommand));

        // same values -> returns true
        AppointmentEntry johnCopy = new AppointmentBuilder().withTitle("John").build();
        AddAppointmentCommand addJohnCommandCopy = new AddAppointmentCommand(johnCopy);
        assertTrue(addJohnCommand.equals(addJohnCommandCopy));

        // different types -> returns false
        assertFalse(addJohnCommand.equals(1));

        // null -> returns false
        assertFalse(addJohnCommand.equals(null));

        // different person -> returns false
        assertFalse(addJohnCommand.equals(addJoshCommand));
    }

    /**
     * Generates a new AddAppointmentCommand with the details of the given appointment.
     */
    private AddAppointmentCommand getAddCommandForAppointment(AppointmentEntry entry, Model model) {
        AddAppointmentCommand command = new AddAppointmentCommand(entry);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            fail("This method should not be called.");
        }

        @Override
        public void removeAppointment(String searchText) throws AppointmentNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void editAppointment(String searchText, AppointmentEntry reference) throws EditAppointmentFailException {
            fail("This method should not be called.");
        }

        @Override
        public AppointmentEntry findAppointment(String searchText) throws AppointmentNotFoundException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public CalendarSource getCalendar() {
            fail("This method should not be called.");
            return null;
        }

        public ArrayList<ArrayList<Double>> getPersonAttrMatrix() {
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicateAppointmentException when trying to add an appointment.
     */
    private class ModelStubThrowingDuplicateAppointmentException extends AddAppointmentCommandTest.ModelStub {

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            throw new DuplicateAppointmentException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingAppointmentAdded extends AddAppointmentCommandTest.ModelStub {
        final ArrayList<AppointmentEntry> appointmentAdded = new ArrayList<>();

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            requireNonNull(appointmentEntry);
            appointmentAdded.add(appointmentEntry);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
