//@@author sarahgoh97
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ShowCellsCommandTest {
    private Model model;
    private Model expectedModel;
    private ShowCellsCommand mapCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        mapCommand = new ShowCellsCommand();
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_typicalMap() {
        assertCommandSuccess(mapCommand, model, String.format(mapCommand.MESSAGE_SUCCESS,
                new ShowCellsCommand().getMapString(model.getAddressBook().getCellList().toString())), expectedModel);

    }
}
