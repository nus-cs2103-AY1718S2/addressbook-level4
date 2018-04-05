//@@author nicholasangcx
package seedu.recipe.logic.commands;

import java.util.Arrays;

import seedu.recipe.model.recipe.IngredientContainsKeywordsPredicate;

/**
 * Finds and lists all recipes in recipe book whose ingredient contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class IngredientCommand extends Command {

    public static final String COMMAND_WORD = "ingredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all recipes whose ingredients contain ALL of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " chicken";

    private final IngredientContainsKeywordsPredicate predicate;
    private final String[] ingredientKeywords;

    public IngredientCommand(IngredientContainsKeywordsPredicate predicate, String[] ingredientKeywords) {
        this.predicate = predicate;
        this.ingredientKeywords = ingredientKeywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredRecipeList(predicate);
        return new CommandResult(getMessageForIngredientListShownSummary
                (model.getFilteredRecipeList().size(), Arrays.toString(ingredientKeywords)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientCommand // instanceof handles nulls
                && this.predicate.equals(((IngredientCommand) other).predicate)); // state check
    }
}
//@@author
