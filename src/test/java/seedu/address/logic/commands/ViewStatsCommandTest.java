package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewStatsCommand.MESSAGE_VIEW_STATS_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author aussiroth
public class ViewStatsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeViewStatsCommand_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ViewStatsCommand command = new ViewStatsCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(command, model, MESSAGE_VIEW_STATS_SUCCESS, expectedModel);
    }
}
