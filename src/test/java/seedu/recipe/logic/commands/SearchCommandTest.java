//@@Author kokonguyen191
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

public class SearchCommandTest {
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
    public void execute() {
        SearchCommand searchCommand = new SearchCommand("chick");
        assertCommandFailure(searchCommand, model, "DUH");
    }
}
