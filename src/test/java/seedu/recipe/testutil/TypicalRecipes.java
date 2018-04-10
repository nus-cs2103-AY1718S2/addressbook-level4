package seedu.recipe.testutil;

import static seedu.recipe.logic.commands.CommandTestUtil.VALID_CALORIES_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_CALORIES_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_COOKING_TIME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_COOKING_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_IMG_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_IMG_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_SERVINGS_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_SERVINGS_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Image;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;

/**
 * A utility class containing a list of {@code Recipe} objects to be used in tests.
 */
public class TypicalRecipes {

    public static final Recipe ALICE = new RecipeBuilder()
            .withName("Alice Pauline")
            .withIngredient("demolishment,bigwig,archer,negative,appearance,afternoon")
            .withInstruction("Fill a tea kettle or 2 quart saucepan with water and bring to a boil."
                    + " Remove excess fat from chilled chicken and place in colander over a large bowl."
                    + " Spread out with a fork. Pour hot water over meat through colander.\n"
                    + "Place chicken in plastic container with tight fitting lid.\n"
                    + "Add onions, chili powder, oregano, garlic powder, cumin, and paprika to chicken.\n"
                    + "Refrigerate chicken overnight in plastic container with tight fitting lid.\n"
                    + "To make tacos, place chicken mixture in a pan and heat slowly or heat in microwave"
                    + " for 2–3 minutes, stirring after 1½ minutes to heat evenly. Combine finely shredded"
                    + " lettuce and cabbage. Mix cheeses together. Place ¼ cup heated chicken mixture in a "
                    + "tortilla and top with cheese and vegetables.\n"
                    + "Add salsa as desired.")
            .withCookingTime("50m")
            .withPreparationTime("85355255")
            .withCalories("5000")
            .withServings("4")
            .withUrl("http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice")
            .withImage("-")
            .withTags("family").build();
    public static final Recipe BENSON = new RecipeBuilder()
            .withName("Benson Meier")
            .withIngredient("guest, barnyard, genuine, salt")
            .withInstruction("311, Clementi Ave 2, #02-25")
            .withCookingTime("50m")
            .withPreparationTime("98765432")
            .withCalories("5000")
            .withServings("4")
            .withUrl("http://recipes.wikia.com/wiki/Ugandan_Chicken_Stew?useskin=wikiamobile")
            .withImage("-")
            .withTags("owesMoney", "family").build();
    public static final Recipe CARL = new RecipeBuilder()
            .withName("Carl Kurz")
            .withIngredient("gadget")
            .withInstruction("wall street")
            .withCookingTime("50m")
            .withPreparationTime("95352563")
            .withCalories("5000")
            .withServings("4")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/casserole")
            .withImage("-")
            .withTags("owesMoney").build();
    public static final Recipe DANIEL = new RecipeBuilder()
            .withName("Daniel Meier")
            .withIngredient("ebony, cold, affliction")
            .withInstruction("10th street")
            .withCookingTime("50m")
            .withPreparationTime("87652533")
            .withCalories("5000")
            .withServings("4")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/curry")
            .withImage("-").build();
    public static final Recipe ELLE = new RecipeBuilder()
            .withName("Elle Meyer")
            .withIngredient("test, aimless")
            .withInstruction("michegan ave")
            .withCookingTime("50m")
            .withPreparationTime("9482224")
            .withCalories("5000")
            .withServings("4")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/fish-pie")
            .withImage("-").build();
    public static final Recipe FIONA = new RecipeBuilder()
            .withName("Fiona Kunz")
            .withIngredient("bluntness, ingredients")
            .withInstruction("little tokyo")
            .withCookingTime("50m")
            .withPreparationTime("9482427")
            .withCalories("5000")
            .withServings("4")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/chicken-salad")
            .withImage("-").build();
    public static final Recipe GEORGE = new RecipeBuilder()
            .withName("George Best")
            .withIngredient("confidence, abandon, brass, model, greed, minipill")
            .withInstruction("4th street")
            .withCookingTime("50m")
            .withPreparationTime("9482442")
            .withCalories("5000")
            .withServings("4")
            .withUrl("https://www.bbcgoodfood.com/recipes/collection/crumble")
            .withImage("-").build();

    // Manually added
    public static final Recipe HOON = new RecipeBuilder()
            .withName("Hoon Meier")
            .withIngredient("test, ingredients")
            .withInstruction("little india")
            .withCookingTime("5555")
            .withPreparationTime("420m")
            .withCalories("15000")
            .withServings("2")
            .withUrl("https://www.google.com")
            .withImage("-").build();
    public static final Recipe IDA = new RecipeBuilder()
            .withName("Ida Mueller")
            .withIngredient("test,ingredients")
            .withInstruction("chicago ave")
            .withCookingTime("5555")
            .withPreparationTime("420m")
            .withCalories("15000")
            .withServings("2")
            .withUrl("https://www.google.com")
            .withImage("-").build();
    public static final Recipe NOURL = new RecipeBuilder()
            .withName("Ida Mueller")
            .withIngredient("just,example")
            .withInstruction("chicago ave")
            .withCookingTime("5555")
            .withPreparationTime("420m")
            .withCalories("15000")
            .withServings("2")
            .withUrl("-")
            .withImage("-").build();
    public static final Recipe HAINANESE_CHICKEN_RICE = new RecipeBuilder()
            .withName("Hainanese Chicken Rice")
            .withIngredient("chicken, salt, spring onion, pandan leaves, ginger, ginger,"
                    + " garlic, cinnamon, cloves, star anise, chicken broth, pandan leaves,"
                    + " salt, light soy sauce, sesame oil, cucumber, tomatoes, coriander,"
                    + " lettuce, pineapple, fresh chillies, ginger, garlic, vinegar,"
                    + " fish sauce, sugar, sweet soy sauce")
            .withInstruction("Boil water with spring Onion, ginger and pandan leaves, "
                    + "put in Chicken and cook till done, do not over cook. briefly dip in "
                    + "cold water and set aside to cool. Keep broth heated.\n"
                    + "Wash rice and drain. Finely shred ginger and garlic, fry in oil with"
                    + " cloves, cinammon and star anise till fragrant, add in rice and fry "
                    + "for several minutes. Transfer into rice cooker, add chicken broth, pinch"
                    + " of salt, pandan leaves and start cooking.\n"
                    + "Put all chili sauce ingredient in a mixer and grind till fine.\n"
                    + "Slice and arrange tomatoes and cucumbers on a big plate, cut Chicken into"
                    + " small pieces and put on top. Splash some light soy sauce and sesame oil"
                    + " over, throw a bunch of coriander on top.\n"
                    + "Next, Put broth in a bowl with lettuce, get ready chili sauce and sweet"
                    + " soy sauce. #Serve rice on a plate with spoon and folk.")
            .withCookingTime("-")
            .withPreparationTime("-")
            .withCalories("-")
            .withServings("-")
            .withUrl("http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice?useskin=wikiamobile")
            .withImage(Image.IMAGE_DIRECTORY + "3932C00C102BA7C2C093FD62FB1AAE2A." + Image.DOWNLOADED_IMAGE_FORMAT)
            .build();

    // Manually added - Recipe's details found in {@code CommandTestUtil}
    public static final Recipe AMY = new RecipeBuilder()
            .withName(VALID_NAME_AMY)
            .withIngredient(VALID_INGREDIENT_AMY)
            .withInstruction(VALID_INSTRUCTION_AMY)
            .withCookingTime(VALID_COOKING_TIME_AMY)
            .withPreparationTime(VALID_PREPARATION_TIME_AMY)
            .withCalories(VALID_CALORIES_AMY)
            .withServings(VALID_SERVINGS_AMY)
            .withUrl(VALID_URL_AMY)
            .withImage(VALID_IMG_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Recipe BOB = new RecipeBuilder()
            .withName(VALID_NAME_BOB)
            .withIngredient(VALID_INGREDIENT_BOB)
            .withInstruction(VALID_INSTRUCTION_BOB)
            .withCookingTime(VALID_COOKING_TIME_BOB)
            .withPreparationTime(VALID_PREPARATION_TIME_BOB)
            .withCalories(VALID_CALORIES_BOB)
            .withServings(VALID_SERVINGS_BOB)
            .withUrl(VALID_URL_BOB)
            .withImage(VALID_IMG_BOB)
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
