package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recipe.commons.util.CollectionUtil;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;

/**
 * A list of recipes that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Recipe#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueRecipeList implements Iterable<Recipe> {

    private final ObservableList<Recipe> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent recipe as the given argument.
     */
    public boolean contains(Recipe toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a recipe to the list.
     *
     * @throws DuplicateRecipeException if the recipe to add is a duplicate of an existing recipe in the list.
     */
    public void add(Recipe toAdd) throws DuplicateRecipeException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecipeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the recipe {@code target} in the list with {@code editedRecipe}.
     *
     * @throws DuplicateRecipeException if the replacement is equivalent to another existing recipe in the list.
     * @throws RecipeNotFoundException if {@code target} could not be found in the list.
     */
    public void setRecipe(Recipe target, Recipe editedRecipe)
            throws DuplicateRecipeException, RecipeNotFoundException {
        requireNonNull(editedRecipe);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RecipeNotFoundException();
        }

        if (!target.equals(editedRecipe) && internalList.contains(editedRecipe)) {
            throw new DuplicateRecipeException();
        }

        internalList.set(index, editedRecipe);
    }

    /**
     * Removes the equivalent recipe from the list.
     *
     * @throws RecipeNotFoundException if no such recipe could be found in the list.
     */
    public boolean remove(Recipe toRemove) throws RecipeNotFoundException {
        requireNonNull(toRemove);
        final boolean recipeFoundAndDeleted = internalList.remove(toRemove);
        if (!recipeFoundAndDeleted) {
            throw new RecipeNotFoundException();
        }
        return recipeFoundAndDeleted;
    }

    public void setRecipes(UniqueRecipeList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setRecipes(List<Recipe> recipes) throws DuplicateRecipeException {
        requireAllNonNull(recipes);
        final UniqueRecipeList replacement = new UniqueRecipeList();
        for (final Recipe recipe : recipes) {
            replacement.add(recipe);
        }
        setRecipes(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Recipe> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Recipe> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRecipeList // instanceof handles nulls
                        && this.internalList.equals(((UniqueRecipeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
