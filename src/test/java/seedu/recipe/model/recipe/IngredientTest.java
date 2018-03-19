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

        // missing parts
        assertFalse(Ingredient.isValidIngredient("@example.com")); // missing local part
        assertFalse(Ingredient.isValidIngredient("peterjackexample.com")); // missing '@' symbol
        assertFalse(Ingredient.isValidIngredient("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Ingredient.isValidIngredient("peterjack@-")); // invalid domain name
        assertFalse(Ingredient.isValidIngredient("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Ingredient.isValidIngredient("peter jack@example.com")); // spaces in local part
        assertFalse(Ingredient.isValidIngredient("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Ingredient.isValidIngredient(" peterjack@example.com")); // leading space
        assertFalse(Ingredient.isValidIngredient("peterjack@example.com ")); // trailing space
        assertFalse(Ingredient.isValidIngredient("peterjack@@example.com")); // double '@' symbol
        assertFalse(Ingredient.isValidIngredient("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Ingredient.isValidIngredient("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Ingredient.isValidIngredient("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Ingredient.isValidIngredient("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Ingredient.isValidIngredient("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Ingredient.isValidIngredient("peterjack@example.com-")); // domain name ends with a hyphen

        // valid ingredient
        assertTrue(Ingredient.isValidIngredient("PeterJack_1190@example.com"));
        assertTrue(Ingredient.isValidIngredient("a@bc"));  // minimal
        assertTrue(Ingredient.isValidIngredient("test@localhost"));   // alphabets only
        assertTrue(Ingredient.isValidIngredient("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Ingredient.isValidIngredient("123@145"));  // numeric local part and domain name
        // mixture of alphanumeric and special characters
        assertTrue(Ingredient.isValidIngredient("a1+be!@example1.com"));
        assertTrue(Ingredient.isValidIngredient("peter_jack@very-very-very-long-example.com"));   // long domain name
        assertTrue(Ingredient.isValidIngredient("if.you.dream.it_you.can.do.it@example.com"));    // long local part
    }
}
