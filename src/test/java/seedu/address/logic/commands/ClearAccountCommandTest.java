package seedu.address.logic.commands;

import static org.junit.Assert.*;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

public class ClearAccountCommandTest {

    @Test
    public void execute_empty_accountList() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAccountList_success() {
        Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS, model);
    }

    private ClearAccountCommand prepareCommand (Model model){
        ClearAccountCommand command = new ClearAccountCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}