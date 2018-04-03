//@@author kokonguyen191
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.SearchCommand.MESSAGE_FAILURE;
import static seedu.recipe.logic.commands.SearchCommand.MESSAGE_SUCCESS;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

public class SearchCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void equals() {
        String firstTest = "chicken rice";
        String secondTest = "boneless pizza";

        SearchCommand searchFirstCommand = new SearchCommand(firstTest);
        SearchCommand searchSecondCommand = new SearchCommand(secondTest);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = new SearchCommand(firstTest);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));
        assertFalse(searchFirstCommand.equals(new HelpCommand()));
        assertFalse(searchFirstCommand.equals("DOGGO"));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_nullInput_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        SearchCommand searchCommand = new SearchCommand(null);
    }

    @Test
    public void execute_inputWithNoResults_noRecipesFound() {
        SearchCommand searchCommandWithNoResult = new SearchCommand("blah");
        assertCommandSuccess(searchCommandWithNoResult, model, MESSAGE_FAILURE, model);
    }

    // THIS TEST MIGHT FAIL IN THE FUTURE! PLEASE UPDATE IF IT FAILS!
    @Test
    public void execute_inputWithFourResults_fourRecipesFound() {
        SearchCommand searchCommandWithFourResult = new SearchCommand("bot");
        assertCommandSuccess(searchCommandWithFourResult, model, String.format(MESSAGE_SUCCESS, 4), model);
    }
}
