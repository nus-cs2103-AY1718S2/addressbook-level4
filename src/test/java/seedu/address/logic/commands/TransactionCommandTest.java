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

public class TransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executes_updatedBalanceAccordingly() throws Exception {
        Double amount = 25.0;
        Double originalBalance = model.getFilteredPersonList().get(0).getMoney().balance;

        TransactionCommand transactionCommand = new TransactionCommand(INDEX_FIRST_PERSON, amount);
        transactionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        transactionCommand.execute();

        assertEquals(originalBalance - 25, model.getFilteredPersonList().get(0).getMoney().balance, 0.001);
    }
}
