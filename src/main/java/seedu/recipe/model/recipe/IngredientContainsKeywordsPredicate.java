//@@author nicholasangcx
package seedu.recipe.model.recipe;

import java.util.List;
import java.util.function.Predicate;

import seedu.recipe.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code Ingredient} matches all of the keywords given.
 */
public class IngredientContainsKeywordsPredicate implements Predicate<Recipe> {
    private final List<String> keywords;

    public IngredientContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        int matches = 0;
        String ingredients = recipe.getIngredient().toString().replaceAll(",", " ");
        for (String keyword : keywords) {
            if (StringUtil.containsWordIgnoreCase(ingredients, keyword)) {
                matches++;
            }
        }
        return matches == keywords.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((IngredientContainsKeywordsPredicate) other).keywords)); // state check
    }
}
//@@author
