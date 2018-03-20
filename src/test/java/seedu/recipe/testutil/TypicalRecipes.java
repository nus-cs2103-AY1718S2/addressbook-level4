package seedu.recipe.testutil;

import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe ALICE = new RecipeBuilder().withName("Alice Pauline")
            .withInstruction("123, Jurong West Ave 6, #08-111").withIngredient("alice@example.com")
            .withPreparationTime("85355255")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/baked-potato")
            .withTags("family").build();
    public static final Recipe BENSON = new RecipeBuilder().withName("Benson Meier")
            .withInstruction("311, Clementi Ave 2, #02-25")
            .withIngredient("johnd@example.com").withPreparationTime("98765432")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/burger")
            .withTags("owesMoney", "family").build();
    public static final Recipe CARL = new RecipeBuilder().withName("Carl Kurz").withPreparationTime("95352563")
            .withIngredient("heinz@example.com").withInstruction("wall street")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/casserole").withTags("owesMoney").build();
    public static final Recipe DANIEL = new RecipeBuilder().withName("Daniel Meier").withPreparationTime("87652533")
            .withIngredient("cornelia@example.com").withInstruction("10th street")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/curry").build();
    public static final Recipe ELLE = new RecipeBuilder().withName("Elle Meyer").withPreparationTime("9482224")
            .withIngredient("werner@example.com").withInstruction("michegan ave")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/fish-pie").build();
    public static final Recipe FIONA = new RecipeBuilder().withName("Fiona Kunz").withPreparationTime("9482427")
            .withIngredient("lydia@example.com").withInstruction("little tokyo")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/chicken-salad").build();
    public static final Recipe GEORGE = new RecipeBuilder().withName("George Best").withPreparationTime("9482442")
            .withIngredient("anna@example.com").withInstruction("4th street")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/crumble").build();

    // Manually added
    public static final Recipe HOON = new RecipeBuilder().withName("Hoon Meier").withPreparationTime("8482424")
            .withIngredient("stefan@example.com").withInstruction("little india")
            .withUrl("https://www.google.com").build();
    public static final Recipe IDA = new RecipeBuilder().withName("Ida Mueller").withPreparationTime("8482131")
            .withIngredient("hans@example.com").withInstruction("chicago ave")
            .withUrl("https://www.google.com").build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe AMY = new RecipeBuilder().withName(VALID_NAME_AMY).withPreparationTime(
            VALID_PREPARATION_TIME_AMY).withIngredient(VALID_INGREDIENT_AMY).withInstruction(VALID_INSTRUCTION_AMY)
            .withUrl(VALID_URL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Recipe BOB = new RecipeBuilder().withName(VALID_NAME_BOB)
            .withPreparationTime(VALID_PREPARATION_TIME_BOB).withIngredient(VALID_INGREDIENT_BOB)
            .withInstruction(VALID_INSTRUCTION_BOB).withUrl(VALID_URL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecipes() {} // prevents instantiation

    /**
     * Returns an {@code RecipeBook} with all the typical recipes.
     */
    public static RecipeBook getTypicalRecipeBook() {
        RecipeBook ab = new RecipeBook();
        for (Recipe recipe : getTypicalRecipes()) {
            try {
                ab.addRecipe(recipe);
            } catch (DuplicateRecipeException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Recipe> getTypicalRecipes() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
