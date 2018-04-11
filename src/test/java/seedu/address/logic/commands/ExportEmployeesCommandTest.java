package seedu.address.logic.commands;
//@@author crizyli
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportEmployeesCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ExportEmployeesCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ExportEmployeesCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ExportEmployeesCommand} which upon execution, clears the contents in {@code model}.
     */
    private ExportEmployeesCommand prepareCommand(Model model) {
        ExportEmployeesCommand command = new ExportEmployeesCommand(true);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
