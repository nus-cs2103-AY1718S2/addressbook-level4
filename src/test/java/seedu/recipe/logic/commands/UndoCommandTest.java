package seedu.recipe.logic.commands;

import static seedu.recipe.logic.UndoRedoStackUtil.prepareStack;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.CommandTestUtil.deleteFirstRecipe;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.recipe.logic.CommandHistory;
import seedu.recipe.logic.UndoRedoStack;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

public class UndoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_RECIPE);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_FIRST_RECIPE);

    @Before
    public void setUp() {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        deleteFirstRecipe(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
