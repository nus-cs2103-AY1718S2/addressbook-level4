package seedu.recipe.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.recipe.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Test;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.CommandHistory;
import seedu.recipe.logic.UndoRedoStack;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.EditRecipeDescriptorBuilder;
import seedu.recipe.testutil.RecipeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Recipe editedRecipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(editedRecipe).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_RECIPE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.updateRecipe(model.getFilteredRecipeList().get(0), editedRecipe);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastRecipe = Index.fromOneBased(model.getFilteredRecipeList().size());
        Recipe lastRecipe = model.getFilteredRecipeList().get(indexLastRecipe.getZeroBased());

        RecipeBuilder recipeInList = new RecipeBuilder(lastRecipe);
        Recipe editedRecipe = recipeInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = prepareCommand(indexLastRecipe, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.updateRecipe(lastRecipe, editedRecipe);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = prepareCommand(INDEX_FIRST_RECIPE, new EditRecipeDescriptor());
        Recipe editedRecipe = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        Recipe recipeInFilteredList = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        Recipe editedRecipe = new RecipeBuilder(recipeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe);

        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());
        expectedModel.updateRecipe(model.getFilteredRecipeList().get(0), editedRecipe);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRecipeUnfilteredList_failure() {
        Recipe firstRecipe = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(firstRecipe).build();
        EditCommand editCommand = prepareCommand(INDEX_SECOND_RECIPE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_duplicateRecipeFilteredList_failure() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        // edit recipe in filtered list into a duplicate in recipe book
        Recipe recipeInList = model.getRecipeBook().getRecipeList().get(INDEX_SECOND_RECIPE.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_RECIPE,
                new EditRecipeDescriptorBuilder(recipeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_RECIPE);
    }

    @Test
    public void execute_invalidRecipeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of recipe book
     */
    @Test
    public void execute_invalidRecipeIndexFilteredList_failure() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);
        Index outOfBoundIndex = INDEX_SECOND_RECIPE;
        // ensures that outOfBoundIndex is still in bounds of recipe book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecipeBook().getRecipeList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Recipe editedRecipe = new RecipeBuilder().build();
        Recipe recipeToEdit = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(editedRecipe).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_RECIPE, descriptor);
        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());

        // edit -> first recipe edited
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts recipebook back to previous state and filtered recipe list to show all recipes
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first recipe edited again
        expectedModel.updateRecipe(recipeToEdit, editedRecipe);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecipeList().size() + 1);
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Recipe} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited recipe in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the recipe object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRecipeEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Recipe editedRecipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(editedRecipe).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_RECIPE, descriptor);
        Model expectedModel = new ModelManager(new RecipeBook(model.getRecipeBook()), new UserPrefs());

        showRecipeAtIndex(model, INDEX_SECOND_RECIPE);
        Recipe recipeToEdit = model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased());
        // edit -> edits second recipe in unfiltered recipe list / first recipe in filtered recipe list
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts recipebook back to previous state and filtered recipe list to show all recipes
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateRecipe(recipeToEdit, editedRecipe);
        assertNotEquals(model.getFilteredRecipeList().get(INDEX_FIRST_RECIPE.getZeroBased()), recipeToEdit);
        // redo -> edits same second recipe in unfiltered recipe list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditCommand standardCommand = prepareCommand(INDEX_FIRST_RECIPE, DESC_AMY);

        // same values -> returns true
        EditRecipeDescriptor copyDescriptor = new EditRecipeDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = prepareCommand(INDEX_FIRST_RECIPE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_RECIPE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_RECIPE, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditRecipeDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
