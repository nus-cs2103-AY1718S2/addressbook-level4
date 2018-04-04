package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointmentEntires.getTypicalAppointmentAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RemoveAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAppointmentAddressBook(), new UserPrefs());

    @Test
    public void execute_validSearchText_success() throws Exception {
        String searchText = "meet john";
        RemoveAppointmentsCommand removeAppointmentsCommand = prepareCommand(searchText);

        String expectedMessage = String.format(RemoveAppointmentsCommand.MESSAGE_SUCCESS, searchText);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeAppointment(searchText);

        assertCommandSuccess(removeAppointmentsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSearchText_throwsCommandException() {
        String searchText = "meet YX";
        RemoveAppointmentsCommand removeAppointmentsCommand = prepareCommand(searchText);

        String expectedMessage = String.format(RemoveAppointmentsCommand.MESSAGE_NO_SUCH_APPOINTMENT);

        assertCommandFailure(removeAppointmentsCommand, model, expectedMessage);
    }

    /**
     * Returns a {@code RemoveAppointmentsCommand} with the parameter {@code searchText}.
     */
    private RemoveAppointmentsCommand prepareCommand(String searchText) {
        RemoveAppointmentsCommand removeAppointmentsCommand = new RemoveAppointmentsCommand(searchText);
        removeAppointmentsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeAppointmentsCommand;
    }
}
