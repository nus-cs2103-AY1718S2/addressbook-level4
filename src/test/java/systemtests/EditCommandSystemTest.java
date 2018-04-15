package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.CALORIES_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.CALORIES_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.COOKING_TIME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.COOKING_TIME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.IMG_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.IMG_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INGREDIENT_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INSTRUCTION_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_PREPARATION_TIME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.SERVINGS_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.SERVINGS_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.URL_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.URL_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_CALORIES_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_COOKING_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_IMG_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_SERVINGS_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.AMY;
import static seedu.recipe.testutil.TypicalRecipes.BOB;
import static seedu.recipe.testutil.TypicalRecipes.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.RedoCommand;
import seedu.recipe.logic.commands.UndoCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.testutil.RecipeUtil;

public class EditCommandSystemTest extends RecipeBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_RECIPE;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + INGREDIENT_DESC_BOB + "  " + INSTRUCTION_DESC_BOB + " " + PREPARATION_TIME_DESC_BOB + " "
                + COOKING_TIME_DESC_BOB + " " + CALORIES_DESC_BOB + " " + SERVINGS_DESC_BOB + " "
                + URL_DESC_BOB + IMG_DESC_BOB + TAG_DESC_HUSBAND + " ";
        Recipe editedRecipe = new RecipeBuilder()
                .withName(VALID_NAME_BOB)
                .withIngredient(VALID_INGREDIENT_BOB)
                .withInstruction(VALID_INSTRUCTION_BOB)
                .withCookingTime(VALID_COOKING_TIME_BOB)
                .withPreparationTime(VALID_PREPARATION_TIME_BOB)
                .withCalories(VALID_CALORIES_BOB)
                .withServings(VALID_SERVINGS_BOB)
                .withUrl(VALID_URL_BOB)
                .withImage(VALID_IMG_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedRecipe);

        /* Case: undo editing the last recipe in the list -> last recipe restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last recipe in the list -> last recipe edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateRecipe(
                getModel().getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased()), editedRecipe);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a recipe with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB
                + COOKING_TIME_DESC_BOB + PREPARATION_TIME_DESC_BOB + CALORIES_DESC_BOB + SERVINGS_DESC_BOB
                + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB + URL_DESC_BOB
                + IMG_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_RECIPE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND;
        Recipe recipeToEdit = getModel().getFilteredRecipeList().get(index.getZeroBased());
        editedRecipe = new RecipeBuilder(recipeToEdit).withTags(VALID_TAG_FRIEND).build();
        assertCommandSuccess(command, index, editedRecipe);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_RECIPE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedRecipe = new RecipeBuilder(recipeToEdit).withTags().build();
        assertCommandSuccess(command, index, editedRecipe);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered recipe list, edit index within bounds of recipe book and recipe list -> edited */
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_RECIPE;
        assertTrue(index.getZeroBased() < getModel().getFilteredRecipeList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        recipeToEdit = getModel().getFilteredRecipeList().get(index.getZeroBased());
        editedRecipe = new RecipeBuilder(recipeToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedRecipe);

        /* Case: filtered recipe list, edit index within bounds of recipe book but out of bounds of recipe list
         * -> rejected
         */
        showRecipesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getRecipeBook().getRecipeList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a recipe card is selected -------------------------- */

        /* Case: selects first card in the recipe list, edit a recipe -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllRecipes();
        index = INDEX_FIRST_RECIPE;
        selectRecipe(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PREPARATION_TIME_DESC_AMY
                + COOKING_TIME_DESC_AMY + SERVINGS_DESC_AMY + CALORIES_DESC_AMY + INGREDIENT_DESC_AMY
                + INSTRUCTION_DESC_AMY  + URL_DESC_AMY + IMG_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new recipe's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredRecipeList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
                + INVALID_NAME_DESC,
            Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid preparation time -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
                + INVALID_PREPARATION_TIME_DESC,
                PreparationTime.MESSAGE_PREPARATION_TIME_CONSTRAINTS);

        /* Case: invalid ingredient -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
                + INVALID_INGREDIENT_DESC,
                Ingredient.MESSAGE_INGREDIENT_CONSTRAINTS);

        /* Case: invalid recipe -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
                + INVALID_INSTRUCTION_DESC,
                Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased()
                + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a recipe with new values same as another recipe's values -> rejected */
        executeCommand(RecipeUtil.getAddCommand(BOB));
        assertTrue(getModel().getRecipeBook().getRecipeList().contains(BOB));
        index = INDEX_FIRST_RECIPE;
        assertFalse(getModel().getFilteredRecipeList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB
                + COOKING_TIME_DESC_BOB + PREPARATION_TIME_DESC_BOB + CALORIES_DESC_BOB + SERVINGS_DESC_BOB
                + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_BOB + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECIPE);

        /* Case: edit a recipe with new values same as another recipe's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB
                + COOKING_TIME_DESC_BOB + PREPARATION_TIME_DESC_BOB + CALORIES_DESC_BOB + SERVINGS_DESC_BOB
                + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Recipe, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Recipe, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Recipe editedRecipe) {
        assertCommandSuccess(command, toEdit, editedRecipe, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the recipe at index {@code toEdit} being
     * updated to values specified {@code editedRecipe}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Recipe editedRecipe,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateRecipe(
                    expectedModel.getFilteredRecipeList().get(toEdit.getZeroBased()), editedRecipe);
            expectedModel.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        } catch (DuplicateRecipeException | RecipeNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedRecipe is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RecipeBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see RecipeBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
