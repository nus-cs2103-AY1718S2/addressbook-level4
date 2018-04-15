package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.recipe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recipe.logic.commands.DeleteCommand.MESSAGE_DELETE_RECIPE_SUCCESS;
import static seedu.recipe.testutil.TestUtil.getLastIndex;
import static seedu.recipe.testutil.TestUtil.getMidIndex;
import static seedu.recipe.testutil.TestUtil.getRecipe;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.DeleteCommand;
import seedu.recipe.logic.commands.RedoCommand;
import seedu.recipe.logic.commands.UndoCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;

public class DeleteCommandSystemTest extends RecipeBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first recipe in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_RECIPE.getOneBased() + "       ";
        Recipe deletedRecipe = removeRecipe(expectedModel, INDEX_FIRST_RECIPE);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RECIPE_SUCCESS, deletedRecipe);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last recipe in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastRecipeIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastRecipeIndex);

        /* Case: undo deleting the last recipe in the list -> last recipe restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last recipe in the list -> last recipe deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeRecipe(modelBeforeDeletingLast, lastRecipeIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle recipe in the list -> deleted */
        Index middleRecipeIndex = getMidIndex(getModel());
        assertCommandSuccess(middleRecipeIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered recipe list, delete index within bounds of recipe book and recipe list -> deleted */
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_RECIPE;
        assertTrue(index.getZeroBased() < getModel().getFilteredRecipeList().size());
        assertCommandSuccess(index);

        /* Case: filtered recipe list, delete index within bounds of recipe book but out of bounds of recipe list
         * -> rejected
         */
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getRecipeBook().getRecipeList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a recipe card is selected ------------------------ */

        /* Case: delete the selected recipe -> recipe list panel selects the recipe before the deleted recipe */
        showAllRecipes();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectRecipe(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedRecipe = removeRecipe(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_RECIPE_SUCCESS, deletedRecipe);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getRecipeBook().getRecipeList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Recipe} at the specified {@code index} in {@code model}'s recipe book.
     * @return the removed recipe
     */
    private Recipe removeRecipe(Model model, Index index) {
        Recipe targetRecipe = getRecipe(model, index);
        try {
            model.deleteRecipe(targetRecipe);
        } catch (RecipeNotFoundException pnfe) {
            throw new AssertionError("targetRecipe is retrieved from model.");
        }
        return targetRecipe;
    }

    /**
     * Deletes the recipe at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Recipe deletedRecipe = removeRecipe(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_RECIPE_SUCCESS, deletedRecipe);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see RecipeBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
