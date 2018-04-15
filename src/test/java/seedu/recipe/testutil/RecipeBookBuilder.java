package seedu.recipe.testutil;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.tag.Tag;

/**
 * A utility class to help with building RecipeBook objects.
 * Example usage: <br>
 *     {@code RecipeBook ab = new RecipeBookBuilder().withRecipe("John", "Doe").withTag("Friend").build();}
 */
public class RecipeBookBuilder {

    private RecipeBook recipeBook;

    public RecipeBookBuilder() {
        recipeBook = new RecipeBook();
    }

    public RecipeBookBuilder(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    /**
     * Adds a new {@code Recipe} to the {@code RecipeBook} that we are building.
     */
    public RecipeBookBuilder withRecipe(Recipe recipe) {
        try {
            recipeBook.addRecipe(recipe);
        } catch (DuplicateRecipeException dpe) {
            throw new IllegalArgumentException("recipe is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code RecipeBook} that we are building.
     */
    public RecipeBookBuilder withTag(String tagName) {
        try {
            recipeBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public RecipeBook build() {
        return recipeBook;
    }
}
