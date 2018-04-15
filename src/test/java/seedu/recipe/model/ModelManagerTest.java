package seedu.recipe.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;
import static seedu.recipe.testutil.TypicalRecipes.ALICE;
import static seedu.recipe.testutil.TypicalRecipes.BENSON;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.model.recipe.NameContainsKeywordsPredicate;
import seedu.recipe.testutil.RecipeBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRecipeList().remove(0);
    }

    @Test
    public void equals() {
        RecipeBook recipeBook = new RecipeBookBuilder().withRecipe(ALICE).withRecipe(BENSON).build();
        RecipeBook differentRecipeBook = new RecipeBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(recipeBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(recipeBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different recipeBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRecipeBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredRecipeList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(recipeBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRecipeBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(recipeBook, differentUserPrefs)));
    }
}
