package seedu.ptman.logic.commands;

import static seedu.ptman.commons.core.Messages.MESSAGE_ACCESS_DENIED;
import static seedu.ptman.logic.UndoRedoStackUtil.prepareStack;
import static seedu.ptman.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ptman.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ptman.logic.commands.CommandTestUtil.deleteFirstEmployee;
import static seedu.ptman.testutil.TypicalEmployees.getTypicalPartTimeManager;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.ptman.logic.CommandHistory;
import seedu.ptman.logic.UndoRedoStack;
import seedu.ptman.model.Model;
import seedu.ptman.model.ModelManager;
import seedu.ptman.model.Password;
import seedu.ptman.model.UserPrefs;
import seedu.ptman.model.outlet.OutletInformation;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalPartTimeManager(), new UserPrefs(),
            new OutletInformation());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_EMPLOYEE);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_SECOND_EMPLOYEE);

    @Before
    public void setUp() throws Exception {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandOne.preprocessUndoableCommand();
        deleteCommandTwo.preprocessUndoableCommand();
        // in Admin Mode
        model.setTrueAdminMode(new Password());
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalPartTimeManager(), new UserPrefs(), new OutletInformation());

        // multiple commands in redoStack
        deleteFirstEmployee(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstEmployee(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);

        // not in admin mode
        model.setFalseAdminMode();
        assertCommandFailure(redoCommand, model, MESSAGE_ACCESS_DENIED);
    }
}
