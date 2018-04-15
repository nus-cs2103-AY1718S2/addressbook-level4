package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstCoin;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;
import static seedu.address.testutil.TypicalTargets.TARGET_FIRST_COIN;
import static seedu.address.testutil.TypicalTargets.TARGET_SECOND_COIN;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalCoinBook(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(TARGET_FIRST_COIN);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(TARGET_SECOND_COIN);

    @Before
    public void setUp() throws Exception {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandOne.preprocessUndoableCommand();
        deleteCommandTwo.preprocessUndoableCommand();
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalCoinBook(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstCoin(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstCoin(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
