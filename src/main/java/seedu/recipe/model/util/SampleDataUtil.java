package seedu.recipe.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Url;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.tag.Tag;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Alex Yeoh"), new PreparationTime("87438807"),
                    new Ingredient("alexyeoh@example.com"),
                    new Instruction("Blk 30 Geylang Street 29, #06-40"),
                    new Url("https://www.allrecipes.com/recipe/73634/colleens-slow-cooker-jambalaya/"),
                    getTagSet("friends")),
            new Recipe(new Name("Bernice Yu"), new PreparationTime("99272758"),
                    new Ingredient("berniceyu@example.com"),
                    new Instruction("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Url("https://www.allrecipes.com/recipe/8722/mexican-chicken-i/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%203"),
                    getTagSet("colleagues", "friends")),
            new Recipe(new Name("Charlotte Oliveiro"), new PreparationTime("93210283"),
                    new Ingredient("charlotte@example.com"),
                    new Instruction("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Url("https://www.allrecipes.com/recipe/11901/to-die-for-fettuccini-alfredo/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%206"),
                    getTagSet("neighbours")),
            new Recipe(new Name("David Li"), new PreparationTime("91031282"),
                    new Ingredient("lidavid@example.com"),
                    new Instruction("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Url("https://www.allrecipes.com/recipe/229110/savory-beef-stir-fry/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%2014"),
                    getTagSet("family")),
            new Recipe(new Name("Irfan Ibrahim"), new PreparationTime("92492021"),
                    new Ingredient("irfan@example.com"),
                    new Instruction("Blk 47 Tampines Street 20, #17-35"),
                    new Url("https://www.allrecipes.com/recipe/222615/scrambled-egg-brunch-bread/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%2020"),
                    getTagSet("classmates")),
            new Recipe(new Name("Roy Balakrishnan"), new PreparationTime("92624417"),
                    new Ingredient("royb@example.com"),
                    new Instruction("Blk 45 Aljunied Street 85, #11-31"),
                    new Url("https://www.allrecipes.com/recipe/15917/fudge-truffle-cheesecake/?"
                            + "internalSource=popular&referringContentType=home%20page&clickId=cardslot%2022"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        try {
            RecipeBook sampleAb = new RecipeBook();
            for (Recipe sampleRecipe : getSampleRecipes()) {
                sampleAb.addRecipe(sampleRecipe);
            }
            return sampleAb;
        } catch (DuplicateRecipeException e) {
            throw new AssertionError("sample data cannot contain duplicate recipes", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
