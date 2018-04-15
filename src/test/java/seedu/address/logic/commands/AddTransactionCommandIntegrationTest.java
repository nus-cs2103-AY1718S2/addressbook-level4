package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.AddressBookBuilder.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.TransactionBuilder;
//@@author ongkc
/**
 * Contains integration tests (interaction with the Model) for {@code AddTransactionCommand}.
 */
public class AddTransactionCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTransaction_success() throws Exception {
        Transaction validTransaction = new TransactionBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTransaction(validTransaction);

        assertCommandSuccess(prepareCommand(validTransaction, model), model,
                String.format(AddTransactionCommand.MESSAGE_SUCCESS, validTransaction), expectedModel);
    }

    /**
     * Generates a new {@code AddTransactionCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddTransactionCommand prepareCommand(Transaction transaction, Model model) {
        AddTransactionCommand command = new AddTransactionCommand(transaction);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
