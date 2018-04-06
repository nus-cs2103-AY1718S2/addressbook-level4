//@@author nicholasangcx
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.commons.core.Messages.MESSAGE_RECIPES_WITH_INGREDIENTS_LISTED_OVERVIEW;
import static seedu.recipe.testutil.TypicalRecipes.BENSON;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.logic.CommandHistory;
import seedu.recipe.logic.UndoRedoStack;
import seedu.recipe.logic.parser.IngredientCommandParser;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.recipe.IngredientContainsKeywordsPredicate;
import seedu.recipe.model.recipe.Recipe;

/**
 * Contains integration tests (interaction with the Model) for {@code IngredientCommand}.
 */
public class IngredientCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void equals() {
        IngredientContainsKeywordsPredicate firstPredicate =
                new IngredientContainsKeywordsPredicate(Collections.singletonList("first"));
        String[] firstArray = {"first"};
        IngredientContainsKeywordsPredicate secondPredicate =
                new IngredientContainsKeywordsPredicate(Collections.singletonList("second"));
        String[] secondArray = {"second"};

        IngredientCommand ingredientFirstCommand = new IngredientCommand(firstPredicate, firstArray);
        IngredientCommand ingredientSecondCommand = new IngredientCommand(secondPredicate, secondArray);

        // same object -> returns true
        assertTrue(ingredientFirstCommand.equals(ingredientFirstCommand));

        // same values -> returns true
        IngredientCommand ingredientFirstCommandCopy = new IngredientCommand(firstPredicate, firstArray);
        assertTrue(ingredientFirstCommand.equals(ingredientFirstCommandCopy));

        // different types -> returns false
        assertFalse(ingredientFirstCommand.equals(1));

        // null -> returns false
        assertFalse(ingredientFirstCommand == null);

        // different recipe -> returns false
        assertFalse(ingredientFirstCommand.equals(ingredientSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoRecipeFound() throws ParseException {
        thrown.expect(ParseException.class);
        String userInput = " ";
        IngredientCommand command = prepareCommand(userInput);
    }

    @Test
    public void executeMultipleKeywordsRecipesFound() throws ParseException {
        String userInput = "genuine salt";
        String[] keywords = userInput.split("\\s+");
        String expectedMessage = String.format(MESSAGE_RECIPES_WITH_INGREDIENTS_LISTED_OVERVIEW, 1,
                Arrays.toString(keywords));
        IngredientCommand command = prepareCommand(userInput);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code IngredientCommand}.
     */
    private IngredientCommand prepareCommand(String userInput) throws ParseException {

        IngredientCommand command = new IngredientCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Recipe>} is equal to {@code expectedList}<br>
     *     - the {@code RecipeBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(IngredientCommand command, String expectedMessage, List<Recipe> expectedList) {
        RecipeBook expectedRecipeBook = new RecipeBook(model.getRecipeBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredRecipeList());
        assertEquals(expectedRecipeBook, model.getRecipeBook());
    }
}
//@@author
