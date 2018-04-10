package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.BIRTHDAY;
import static seedu.address.testutil.TypicalAppointments.MEETING;
import static seedu.address.testutil.TypicalAppointments.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.appointment.DeleteAppointmentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

//@@author trafalgarandre
/**
 * {@code DeleteAppointmentCommand}.
 */
public class DeleteAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAppointment_success() throws Exception {
        Appointment appointmentToDelete = model.getAppointmentList().get(0);
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointmentToDelete);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_SUCCESS, appointmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notAddedAppointment_throwsCommandException() throws Exception {
        Appointment notAddedAppointment = new AppointmentBuilder()
                .withTitle("Interview").withStartDateTime("2018-04-26 17:00")
                .withEndDateTime("2018-04-26 18:00").build();

        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(notAddedAppointment);

        assertCommandFailure(deleteAppointmentCommand, model, DeleteAppointmentCommand.MESSAGE_NOT_FOUND_APPOINTMENT);
    }

    @Test
    public void equals() throws Exception {
        DeleteAppointmentCommand deleteAppointmentFirstCommand = prepareCommand(BIRTHDAY);
        DeleteAppointmentCommand deleteAppointmentSecondCommand = prepareCommand(MEETING);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy = prepareCommand(BIRTHDAY);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteAppointmentCommand prepareCommand(Appointment appointment) {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(appointment);
        deleteAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAppointmentCommand;
    }
}
