package seedu.recipe.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.recipe.logic.commands.CommandTestUtil.deleteFirstRecipe;
import static seedu.recipe.logic.commands.CommandTestUtil.showRecipeAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Test;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstRecipe(expectedModel);
        assertEquals(expectedModel, model);

        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        // undo() should cause the model's filtered list to show all recipes
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showRecipeAtIndex(model, INDEX_FIRST_RECIPE);

        // redo() should cause the model's filtered list to show all recipes
        dummyCommand.redo();
        deleteFirstRecipe(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first recipe in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Recipe recipeToDelete = model.getFilteredRecipeList().get(0);
            try {
                model.deleteRecipe(recipeToDelete);
            } catch (RecipeNotFoundException pnfe) {
                fail("Impossible: recipeToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
