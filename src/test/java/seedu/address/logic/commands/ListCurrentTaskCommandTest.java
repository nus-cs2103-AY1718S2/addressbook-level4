package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author WoodySIN
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCurrentTaskCommand.
 */
public class ListCurrentTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCurrentTaskCommand listCurrentTaskCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCurrentTaskCommand = new ListCurrentTaskCommand();
        listCurrentTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCurrentTaskCommand, model, ListCurrentTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        new ListTaskCommand();
        assertCommandSuccess(listCurrentTaskCommand, model, ListCurrentTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
