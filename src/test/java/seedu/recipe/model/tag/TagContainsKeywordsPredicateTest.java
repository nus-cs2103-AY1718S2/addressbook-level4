//@@author nicholasangcx
package seedu.recipe.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.recipe.testutil.RecipeBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different recipe -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testTagContainsKeywordsReturnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new RecipeBuilder().build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "food"));
        assertTrue(predicate.test(new RecipeBuilder().withTags("friends", "food").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("friends", "food"));
        assertTrue(predicate.test(new RecipeBuilder().build()));
    }

    @Test
    public void testTagDoesNotContainKeywordsReturnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecipeBuilder().build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("food"));
        assertFalse(predicate.test(new RecipeBuilder().build()));

        // Keywords match phone, email, name and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList
                            ("Food", "12345", "fish", "egg", "Main", "Street"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Food").withPreparationTime("12345")
                .withIngredient("fish, egg").withInstruction("Main Street").build()));
    }
}
//@@author
