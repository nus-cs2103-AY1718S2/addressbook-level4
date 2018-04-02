package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPT;
import static seedu.address.testutil.TypicalPersonsAndAppointments.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

//@@author jlks96
public class DeleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_existentAppointment_success() throws Exception {
        Appointment appointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPT_SUCCESS, appointment);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointment);

        assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentAppointment_throwsCommandException() {
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);

        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_APPT_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_existentAppointment_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Appointment appointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first appointment deleted
        deleteAppointmentCommand.execute();
        undoRedoStack.push(deleteAppointmentCommand);

        // undo -> reverts address book back to previous state and filtered appointment list to show all appointments
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first appointment deleted again
        expectedModel.deleteAppointment(appointment);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_nonExistentAppointment_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Appointment appointment = new AppointmentBuilder().build();
        DeleteAppointmentCommand deleteAppointmentCommand = prepareCommand(appointment);

        // execution failed -> deleteAppointmentCommand not pushed into undoRedoStack
        assertCommandFailure(deleteAppointmentCommand, model, Messages.MESSAGE_APPT_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPT.getZeroBased());
        Appointment secondAppointment = model.getFilteredAppointmentList().get(INDEX_SECOND_APPT.getZeroBased());
        DeleteAppointmentCommand deleteAppointmentFirstCommand = prepareCommand(firstAppointment);
        DeleteAppointmentCommand deleteAppointmentSecondCommand = prepareCommand(secondAppointment);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy = prepareCommand(firstAppointment);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

    /**
     * Returns a {@code DeleteAppointmentCommand} with the parameter {@code appointmentToDelete}.
     */
    private DeleteAppointmentCommand prepareCommand(Appointment appointmentToDelete) {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(appointmentToDelete);
        deleteAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAppointmentCommand;
    }
}
