package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), getTypicalEventBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_emptyListIsSorted_success() {
        model.sortPersons();
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyListIsSorted_success() {
        model.sortPersons();
        assertCommandSuccess(sortCommand, model, sortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
