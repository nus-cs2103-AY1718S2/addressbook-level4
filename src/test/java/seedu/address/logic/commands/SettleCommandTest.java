package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SettleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executes_settlesBalance() throws Exception {
        SettleCommand settleCommand = new SettleCommand(INDEX_FIRST_PERSON);

        settleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        settleCommand.execute();

        assertEquals(0.0, model.getFilteredPersonList().get(0).getMoney().balance, 0.001);
    }
}
