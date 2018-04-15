//@@author nicholasangcx
package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.recipe.logic.commands.IngredientCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.recipe.IngredientContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new IngredientCommand object
 */
public class IngredientCommandParser implements Parser<IngredientCommand> {

    private static final String REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the IngredientCommand
     * and returns an IngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public IngredientCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, IngredientCommand.MESSAGE_USAGE));
        }

        String[] ingredientKeywords = trimmedArgs.split(REGEX);

        return new IngredientCommand(new IngredientContainsKeywordsPredicate(Arrays.asList(ingredientKeywords)),
                    ingredientKeywords);
    }
}
//@@author
