package seedu.recipe.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Recipe> PREDICATE_SHOW_ALL_RECIPES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyRecipeBook newData);

    /** Returns the RecipeBook */
    ReadOnlyRecipeBook getRecipeBook();

    /** Deletes the given recipe. */
    void deleteRecipe(Recipe target) throws RecipeNotFoundException;

    /** Adds the given recipe */
    void addRecipe(Recipe recipe) throws DuplicateRecipeException;

    /**
     * Replaces the given recipe {@code target} with {@code editedRecipe}.
     *
     * @throws DuplicateRecipeException if updating the recipe's details causes the recipe to be equivalent to
     *      another existing recipe in the list.
     * @throws RecipeNotFoundException if {@code target} could not be found in the list.
     */
    void updateRecipe(Recipe target, Recipe editedRecipe)
            throws DuplicateRecipeException, RecipeNotFoundException;

    /** Returns an unmodifiable view of the filtered recipe list */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecipeList(Predicate<Recipe> predicate);

}
