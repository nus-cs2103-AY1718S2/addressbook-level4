package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class WipeCommandTest {
    private Model model;
    private Model expectedModel;
    private WipeBalancesCommand wipeBalancesCommand;
    private double balance;


    @Before
    public void setUp() throws CommandException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        wipeBalancesCommand = new WipeBalancesCommand();

        wipeBalancesCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        wipeBalancesCommand.execute();

    }

    @Test
    public void executes_getsWipeSuccess() {
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            assertEquals(0.0, model.getFilteredPersonList().get(i).getMoney().balance, 0.001);
        }

    }
}
