//@@author nicholasangcx
package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.recipe.logic.commands.IngredientCommand;
import seedu.recipe.model.recipe.IngredientContainsKeywordsPredicate;

public class IngredientCommandParserTest {
    
    private IngredientCommandParser parser = new IngredientCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, IngredientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsIngredientCommand() {
        // no leading and trailing whitespaces
        IngredientCommand expectedIngredientCommand =
                new IngredientCommand(new IngredientContainsKeywordsPredicate(Arrays.asList("chicken", "rice")),
                        new String[] {"chicken", "rice"});
        assertParseSuccess(parser, "chicken rice", expectedIngredientCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n chicken \n \t rice  \t", expectedIngredientCommand);
    }
}
//@@author
