package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AccountsManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.exception.DuplicateUsernameException;
import seedu.address.model.exception.InvalidPasswordException;
import seedu.address.model.exception.InvalidUsernameException;
import seedu.address.model.exception.MultipleLoginException;
import seedu.address.model.exception.UserLogoutException;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.skill.Skill;
import seedu.address.model.skill.UniqueSkillList;
import seedu.address.testutil.AppointmentBuilder;

//@@author trafalgarandre
public class AddAppointmentCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null);
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        Appointment validAppointment = new AppointmentBuilder().build();

        CommandResult commandResult = getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();

        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.appointmentsAdded);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAppointmentException();
        Appointment validAppointment = new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);

        getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
    }

    @Test
    public void equals() {
        Appointment birthday = new AppointmentBuilder().withTitle("Birthday").build();
        Appointment meeting = new AppointmentBuilder().withTitle("Meeting").build();
        AddAppointmentCommand addBirthdayCommand = new AddAppointmentCommand(birthday);
        AddAppointmentCommand addMeetingCommand = new AddAppointmentCommand(meeting);

        // same object -> returns true
        assertTrue(addBirthdayCommand.equals(addBirthdayCommand));

        // same values -> returns true
        AddAppointmentCommand addBirthdayCommandCopy = new AddAppointmentCommand(birthday);
        assertTrue(addBirthdayCommand.equals(addBirthdayCommandCopy));

        // different types -> returns false
        assertFalse(addBirthdayCommand.equals(1));

        // null -> returns false
        assertFalse(addBirthdayCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addBirthdayCommand.equals(addMeetingCommand));
    }

    /**
     * Generates a new AddAppointmentCommand with the details of the given appointments.
     */
    private AddAppointmentCommand getAddAppointmentCommandForAppointment(Appointment appointment, Model model) {
        AddAppointmentCommand command = new AddAppointmentCommand(appointment);
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
        public void deleteJob(Job target) throws JobNotFoundException {
            Assert.fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteSkill(Skill t)
                throws PersonNotFoundException, DuplicatePersonException, UniqueSkillList.DuplicateSkillException {
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
        public AccountsManager getAccountsManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void login(String username, String password) throws InvalidUsernameException,
                InvalidPasswordException, MultipleLoginException {
            fail("This method should not be called.");
        };

        @Override
        public void logout() throws UserLogoutException {
            fail("This method should not be called.");
        };



        @Override
        public void register(String username, String password) throws DuplicateUsernameException {
            fail("This method should not be called.");
        }

        @Override
        public void addJob(Job job) throws DuplicateJobException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Job> getFilteredJobList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredJobList(Predicate<Job> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment target) throws AppointmentNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
            fail("This method should not be called");
        }

        @Override
        public void updateAppointment(Appointment target, Appointment editedPerson)
                throws DuplicateAppointmentException, AppointmentNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public List<Appointment> getAppointmentList() {
            fail("This method should not be called");
            return null;
        }

    }

    /**
     * A Model stub that always throw a DuplicateAppointmentException when trying to add a appointment.
     */
    private class ModelStubThrowingDuplicateAppointmentException extends ModelStub {
        @Override
        public void addAppointment(Appointment appointment) throws DuplicateAppointmentException {
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
    private class ModelStubAcceptingAppointmentAdded extends ModelStub {
        final ArrayList<Appointment> appointmentsAdded = new ArrayList<>();

        @Override
        public void addAppointment(Appointment person) throws DuplicateAppointmentException {
            requireNonNull(person);
            appointmentsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
