package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

//@@author jlks96
public class AddAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAppointmentCommand(null);
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        Model modelStub = new ModelManager();
        Appointment validAppointment =  new AppointmentBuilder().build();

        CommandResult commandResult = getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();

        assertEquals(
                String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAppointment), modelStub.getFilteredAppointmentList());
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager();
        Appointment validAppointment =  new AppointmentBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);

        getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
        getAddAppointmentCommandForAppointment(validAppointment, modelStub).execute();
    }

    @Test
    public void execute_clashingAppointment_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager();
        Appointment firstAppointment =  new AppointmentBuilder().build();
        Appointment secondAppointment = new AppointmentBuilder().withPersonName("Bob").build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppointmentCommand.MESSAGE_CLASHING_APPOINTMENT);

        getAddAppointmentCommandForAppointment(firstAppointment, modelStub).execute();
        getAddAppointmentCommandForAppointment(secondAppointment, modelStub).execute();
    }

    @Test
    public void equals() {
        Appointment aliceAppointment = new AppointmentBuilder().withPersonName("Alice").build();
        Appointment bobAppointment = new AppointmentBuilder().withPersonName("Bob").build();
        AddAppointmentCommand addAliceAppointmentCommand = new AddAppointmentCommand(aliceAppointment);
        AddAppointmentCommand addBobAppointmentCommand = new AddAppointmentCommand(bobAppointment);

        // same object -> returns true
        assertTrue(addAliceAppointmentCommand.equals(addAliceAppointmentCommand));

        // same values -> returns true
        AddAppointmentCommand addAliceAppointmentCommandCopy = new AddAppointmentCommand(aliceAppointment);
        assertTrue(addAliceAppointmentCommand.equals(addAliceAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(addAliceAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceAppointmentCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceAppointmentCommand.equals(addBobAppointmentCommand));
    }

    /**
     * Generates a new AddAppointmentCommand with the details of the given appointment.
     */
    private AddAppointmentCommand getAddAppointmentCommandForAppointment(Appointment appointment, Model model) {
        AddAppointmentCommand command = new AddAppointmentCommand(appointment);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
