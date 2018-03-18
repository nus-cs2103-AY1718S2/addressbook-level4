package seedu.recipe.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Phone;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.tag.Tag;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Alex Yeoh"), new Phone("87438807"), new Ingredient("alexyeoh@example.com"),
                new Instruction("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Recipe(new Name("Bernice Yu"), new Phone("99272758"), new Ingredient("berniceyu@example.com"),
                new Instruction("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Recipe(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Ingredient("charlotte@example.com"),
                new Instruction("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Recipe(new Name("David Li"), new Phone("91031282"), new Ingredient("lidavid@example.com"),
                new Instruction("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Recipe(new Name("Irfan Ibrahim"), new Phone("92492021"), new Ingredient("irfan@example.com"),
                new Instruction("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Recipe(new Name("Roy Balakrishnan"), new Phone("92624417"), new Ingredient("royb@example.com"),
                new Instruction("Blk 45 Aljunied Street 85, #11-31"),
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
