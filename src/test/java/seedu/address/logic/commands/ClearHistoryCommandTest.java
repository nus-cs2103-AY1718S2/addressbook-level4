package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearHistoryCommandTest {
    private ClearHistoryCommand clearHistoryCommand;
    private CommandHistory history;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new CommandHistory();
        clearHistoryCommand = new ClearHistoryCommand();
        clearHistoryCommand.setData(model, history, new UndoRedoStack());
    }

    @Test
    public void execute() {
        assertCommandResult(clearHistoryCommand, ClearHistoryCommand.MESSAGE_SUCCESS);

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = ClearHistoryCommand.MESSAGE_SUCCESS;

        assertCommandResult(clearHistoryCommand, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code clearHistoryCommand}
     * is equal to {@code expectedMessage}
     */
    private void assertCommandResult(ClearHistoryCommand clearHistoryCommand, String expectedMessage) {
        assertEquals(expectedMessage, clearHistoryCommand.execute().feedbackToUser);
    }
}
