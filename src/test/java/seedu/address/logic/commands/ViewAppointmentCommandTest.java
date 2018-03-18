package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewAppointmentCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewAppointmentCommand viewAppointmentCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        viewAppointmentCommand = new ViewAppointmentCommand();
        viewAppointmentCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showSameList() {
        assertCommandSuccess(viewAppointmentCommand, model, ViewAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
