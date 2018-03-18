package seedu.recipe.model;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.recipe.commons.core.ComponentManager;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.events.model.RecipeBookChangedEvent;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;

/**
 * Represents the in-memory model of the recipe book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RecipeBook recipeBook;
    private final FilteredList<Recipe> filteredRecipes;

    /**
     * Initializes a ModelManager with the given recipeBook and userPrefs.
     */
    public ModelManager(ReadOnlyRecipeBook recipeBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(recipeBook, userPrefs);

        logger.fine("Initializing with recipe book: " + recipeBook + " and user prefs " + userPrefs);

        this.recipeBook = new RecipeBook(recipeBook);
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
    }

    public ModelManager() {
        this(new RecipeBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyRecipeBook newData) {
        recipeBook.resetData(newData);
        indicateRecipeBookChanged();
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return recipeBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateRecipeBookChanged() {
        raise(new RecipeBookChangedEvent(recipeBook));
    }

    @Override
    public synchronized void deleteRecipe(Recipe target) throws RecipeNotFoundException {
        recipeBook.removeRecipe(target);
        indicateRecipeBookChanged();
    }

    @Override
    public synchronized void addRecipe(Recipe recipe) throws DuplicateRecipeException {
        recipeBook.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        indicateRecipeBookChanged();
    }

    @Override
    public void updateRecipe(Recipe target, Recipe editedRecipe)
            throws DuplicateRecipeException, RecipeNotFoundException {
        requireAllNonNull(target, editedRecipe);

        recipeBook.updateRecipe(target, editedRecipe);
        indicateRecipeBookChanged();
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code recipeBook}
     */
    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return FXCollections.unmodifiableObservableList(filteredRecipes);
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return recipeBook.equals(other.recipeBook)
                && filteredRecipes.equals(other.filteredRecipes);
    }

}
