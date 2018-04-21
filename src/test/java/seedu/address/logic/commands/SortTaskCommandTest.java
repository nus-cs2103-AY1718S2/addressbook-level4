package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTaskTestUtil.assertCommandSuccess;
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
 * Contains integration tests (interaction with the Model) and unit tests for ListTaskCommand.
 */
public class SortTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private SortTaskCommand sortTaskCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortTaskCommand = new SortTaskCommand();
        sortTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_sortsList() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsList() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsEverything() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nullList_sortsList() {
        assertCommandSuccess(sortTaskCommand, model, SortTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
