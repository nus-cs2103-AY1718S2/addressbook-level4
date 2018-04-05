//@@author nicholasangcx
package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.recipe.testutil.RecipeBuilder;

public class IngredientContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        IngredientContainsKeywordsPredicate firstPredicate =
                new IngredientContainsKeywordsPredicate(firstPredicateKeywordList);
        IngredientContainsKeywordsPredicate secondPredicate =
                new IngredientContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IngredientContainsKeywordsPredicate firstPredicateCopy =
                new IngredientContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different recipe -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testIngredientContainsKeywordsReturnsTrue() {
        // One keyword
        IngredientContainsKeywordsPredicate predicate =
                new IngredientContainsKeywordsPredicate(Collections.singletonList("chicken"));
        assertTrue(predicate.test(new RecipeBuilder().withIngredient("chicken, rice").build()));

        // Multiple keywords
        predicate = new IngredientContainsKeywordsPredicate(Arrays.asList("chicken", "rice"));
        assertTrue(predicate.test(new RecipeBuilder().withIngredient("chicken, rice").build()));

    }

    @Test
    public void testIngredientDoesNotContainKeywordsReturnsFalse() {
        // Non-matching keyword
        IngredientContainsKeywordsPredicate predicate = new IngredientContainsKeywordsPredicate(Arrays.asList("food"));
        assertFalse(predicate.test(new RecipeBuilder().build()));

        // Only matches one keyword
        predicate = new IngredientContainsKeywordsPredicate(Arrays.asList("chicken", "rice"));
        assertFalse(predicate.test(new RecipeBuilder().withIngredient("chicken").build()));

        // Keywords match phone, email, name and address, but does not match Ingredient
        predicate = new IngredientContainsKeywordsPredicate(Arrays.asList
                ("Food", "12345", "fish", "egg", "Main", "Street"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Food").withPreparationTime("12345")
                .withIngredient("chicken, rice").withInstruction("Main Street").build()));
    }
}
//@@author
