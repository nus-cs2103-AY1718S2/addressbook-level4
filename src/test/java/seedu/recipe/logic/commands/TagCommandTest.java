package seedu.recipe.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.commons.core.Messages.MESSAGE_RECIPES_WITH_TAGS_LISTED_OVERVIEW;
import static seedu.recipe.testutil.TypicalRecipes.ALICE;
import static seedu.recipe.testutil.TypicalRecipes.BENSON;
import static seedu.recipe.testutil.TypicalRecipes.CARL;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.recipe.logic.CommandHistory;
import seedu.recipe.logic.UndoRedoStack;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.tag.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code TagCommand}.
 */

public class TagCommandTest {
    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        String[] firstArray = {"first"};
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));
        String[] secondArray = {"second"};

        TagCommand tagFirstCommand = new TagCommand(firstPredicate, firstArray);
        TagCommand tagSecondCommand = new TagCommand(secondPredicate, secondArray);

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(firstPredicate, firstArray);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagFirstCommand == null);

        // different recipe -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoRecipeFound() {
        String userInput = " ";
        String[] keywords = userInput.split("\\s+");
        String expectedMessage = String.format(MESSAGE_RECIPES_WITH_TAGS_LISTED_OVERVIEW, 0,
                                                    Arrays.toString(keywords));
        TagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeMultipleKeywordsMultipleRecipesFound() {
        String userInput = "family owesMoney";
        String[] keywords = userInput.split("\\s+");
        String expectedMessage = String.format(MESSAGE_RECIPES_WITH_TAGS_LISTED_OVERVIEW, 3,
                                                    Arrays.toString(keywords));
        TagCommand command = prepareCommand(userInput);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL));
    }

    /**
     * Parses {@code userInput} into a {@code TagCommand}.
     */
    private TagCommand prepareCommand(String userInput) {

        TagCommand command =
                new TagCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))),
                                        userInput.split("\\s+"));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Recipe>} is equal to {@code expectedList}<br>
     *     - the {@code RecipeBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(TagCommand command, String expectedMessage, List<Recipe> expectedList) {
        RecipeBook expectedRecipeBook = new RecipeBook(model.getRecipeBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredRecipeList());
        assertEquals(expectedRecipeBook, model.getRecipeBook());
    }
}
