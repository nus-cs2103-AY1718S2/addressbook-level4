package seedu.recipe.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.RecipeCardHandle;
import guitests.guihandles.RecipeListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.recipe.model.recipe.Recipe;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RecipeCardHandle expectedCard, RecipeCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPreparationTime(), actualCard.getPreparationTime());
        assertEquals(expectedCard.getServings(), actualCard.getServings());
        assertEquals(expectedCard.getCalories(), actualCard.getCalories());
        assertEquals(expectedCard.getIngredient(), actualCard.getIngredient());
        assertEquals(expectedCard.getUrl(), actualCard.getUrl());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRecipe}.
     */
    public static void assertCardDisplaysRecipe(Recipe expectedRecipe, RecipeCardHandle actualCard) {
        assertEquals(expectedRecipe.getName().fullName, actualCard.getName());
        assertEquals(expectedRecipe.getCalories().value,
                actualCard.getCalories());
        assertEquals(expectedRecipe.getPreparationTime().value,
                actualCard.getPreparationTime());
        assertEquals(expectedRecipe.getServings().value,
                actualCard.getServings());
        assertEquals(expectedRecipe.getIngredient().value,
                actualCard.getIngredient());
        assertEquals(expectedRecipe.getUrl().value, actualCard.getUrl());
        assertEquals(expectedRecipe.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code recipeListPanelHandle} displays the details of {@code recipes} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecipeListPanelHandle recipeListPanelHandle, Recipe... recipes) {
        for (int i = 0; i < recipes.length; i++) {
            assertCardDisplaysRecipe(recipes[i], recipeListPanelHandle.getRecipeCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code recipeListPanelHandle} displays the details of {@code recipes} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecipeListPanelHandle recipeListPanelHandle, List<Recipe> recipes) {
        assertListMatching(recipeListPanelHandle, recipes.toArray(new Recipe[0]));
    }

    /**
     * Asserts the size of the list in {@code recipeListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RecipeListPanelHandle recipeListPanelHandle, int size) {
        int numberOfPeople = recipeListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
