package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Recipe> PREDICATE_MATCHING_NO_RECIPES = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Recipe> toDisplay) {
        Optional<Predicate<Recipe>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredRecipeList(predicate.orElse(PREDICATE_MATCHING_NO_RECIPES));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Recipe... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Recipe} equals to {@code other}.
     */
    private static Predicate<Recipe> getPredicateMatching(Recipe other) {
        return recipe -> recipe.equals(other);
    }
}
