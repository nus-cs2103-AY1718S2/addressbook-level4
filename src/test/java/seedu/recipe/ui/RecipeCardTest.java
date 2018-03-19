package seedu.recipe.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.ui.testutil.GuiTestAssert.assertCardDisplaysRecipe;

import org.junit.Test;

import guitests.guihandles.RecipeCardHandle;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.testutil.RecipeBuilder;

public class RecipeCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Recipe recipeWithNoTags = new RecipeBuilder().withTags(new String[0]).build();
        RecipeCard recipeCard = new RecipeCard(recipeWithNoTags, 1);
        uiPartRule.setUiPart(recipeCard);
        assertCardDisplay(recipeCard, recipeWithNoTags, 1);

        // with tags
        Recipe recipeWithTags = new RecipeBuilder().build();
        recipeCard = new RecipeCard(recipeWithTags, 2);
        uiPartRule.setUiPart(recipeCard);
        assertCardDisplay(recipeCard, recipeWithTags, 2);
    }

    @Test
    public void equals() {
        Recipe recipe = new RecipeBuilder().build();
        RecipeCard recipeCard = new RecipeCard(recipe, 0);

        // same recipe, same index -> returns true
        RecipeCard copy = new RecipeCard(recipe, 0);
        assertTrue(recipeCard.equals(copy));

        // same object -> returns true
        assertTrue(recipeCard.equals(recipeCard));

        // null -> returns false
        assertFalse(recipeCard.equals(null));

        // different types -> returns false
        assertFalse(recipeCard.equals(0));

        // different recipe, same index -> returns false
        Recipe differentRecipe = new RecipeBuilder().withName("differentName").build();
        assertFalse(recipeCard.equals(new RecipeCard(differentRecipe, 0)));

        // same recipe, different index -> returns false
        assertFalse(recipeCard.equals(new RecipeCard(recipe, 1)));
    }

    /**
     * Asserts that {@code recipeCard} displays the details of {@code expectedRecipe} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RecipeCard recipeCard, Recipe expectedRecipe, int expectedId) {
        guiRobot.pauseForHuman();

        RecipeCardHandle recipeCardHandle = new RecipeCardHandle(recipeCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", recipeCardHandle.getId());

        // verify recipe details are displayed correctly
        assertCardDisplaysRecipe(expectedRecipe, recipeCardHandle);
    }
}
