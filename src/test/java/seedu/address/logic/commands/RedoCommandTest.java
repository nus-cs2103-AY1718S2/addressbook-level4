package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.removeFirstActivity;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;

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

    private final Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    private final RemoveCommand removeCommandOne = new RemoveCommand("task", INDEX_FIRST_ACTIVITY);
    private final RemoveCommand removeCommandTwo = new RemoveCommand("task", INDEX_SECOND_ACTIVITY);

    @Before
    public void setUp() throws Exception {
        removeCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        removeCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        removeCommandOne.preprocessUndoableCommand();
        removeCommandTwo.preprocessUndoableCommand();
    }

    /**
     * Test
     */
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(removeCommandTwo, removeCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());

        // multiple commands in redoStack
        removeFirstActivity(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        removeFirstActivity(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
