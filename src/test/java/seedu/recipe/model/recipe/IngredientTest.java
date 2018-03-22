package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class IngredientTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Ingredient(null));
    }

    @Test
    public void constructor_invalidIngredient_throwsIllegalArgumentException() {
        String invalidIngredient = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Ingredient(invalidIngredient));
    }

    @Test
    public void isValidIngredient() {
        // null ingredient
        Assert.assertThrows(NullPointerException.class, () -> Ingredient.isValidIngredient(null));

        // blank ingredient
        assertFalse(Ingredient.isValidIngredient("")); // empty string
        assertFalse(Ingredient.isValidIngredient(" ")); // spaces only

        // invalid delimiter
        assertFalse(Ingredient.isValidIngredient("test. ingredient")); // wrong delimiter
        assertFalse(Ingredient.isValidIngredient("test|test")); // wrong delimiter

        // blank ingredients
        assertFalse(Ingredient.isValidIngredient(",,,,,,,")); // no ingredients
        assertFalse(Ingredient.isValidIngredient(", , , , , , , ")); // no ingredients

        // valid ingredient
        assertTrue(Ingredient.isValidIngredient("chicken, here"));
        assertTrue(Ingredient.isValidIngredient("more chicken here"));
        assertTrue(Ingredient.isValidIngredient("fish"));   // 1 ingredient
        assertTrue(Ingredient.isValidIngredient("pizza"));  // 1 ingredient
        assertTrue(Ingredient.isValidIngredient("sugar, sugar, sugar, sugar"));
    }
}
