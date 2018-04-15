//@@author Articho28

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
public class BalanceCommandTest {

    private Model model;
    private Model expectedModel;
    private BalanceCommand balanceCommand;
    private double balance;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        balanceCommand = new BalanceCommand();
        balanceCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        balance = balanceCommand.getBalanceFromTravelBanker();
    }

    @Test
    public void executes_getsOverallBalanceSuccess() {
        assertCommandSuccess(balanceCommand, model,  BalanceCommand.MESSAGE_SUCCESS
                + "\n" + "Your balance is "
                + BalanceCommand.getFormatTwoDecimalPlaces().format(balance)
                + ".", expectedModel);
    }




}
