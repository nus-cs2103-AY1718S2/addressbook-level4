package seedu.recipe.model.tag;

import java.util.List;
import java.util.function.Predicate;

import seedu.recipe.model.recipe.Recipe;

/**
 * Tests that a {@code Recipe}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Recipe> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        /*figure out why cannot work
           return keywords.stream()
                    .anyMatch(keyword -> Recipe.getTags().contains(keyword));

         */
        return keywords.stream()
                    .anyMatch(keyword -> recipe.getTags().stream()
                        .anyMatch(tag -> tag.tagName.equals(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
