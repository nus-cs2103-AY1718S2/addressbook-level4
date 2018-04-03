//@@author nicholasangcx
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
// import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.recipe.logic.commands.UploadCommand.MESSAGE_UPLOAD;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

public class UploadCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void equals() {
        String firstTest = "RecipeBook1";
        String secondTest = "Recipe_Book_1";

        UploadCommand uploadFirstCommand = new UploadCommand(firstTest);
        UploadCommand uploadSecondCommand = new UploadCommand(secondTest);

        // same object -> returns true
        assertTrue(uploadFirstCommand.equals(uploadFirstCommand));

        // same values -> returns true
        UploadCommand uploadFirstCommandCopy = new UploadCommand(firstTest);
        assertTrue(uploadFirstCommandCopy.equals(uploadFirstCommand));

        // different types -> returns false
        assertFalse(uploadFirstCommand.equals(1));
        assertFalse(uploadFirstCommand.equals(new HelpCommand()));
        assertFalse(uploadFirstCommand.equals("anything"));

        // null -> returns false
        assertFalse(uploadFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(uploadFirstCommand.equals(uploadSecondCommand));
    }

    /*
    @Test
    public void execute_inputWithValidArgs_noAccessToken() {
        UploadCommand uploadCommand = new UploadCommand("recipebook.xml");
        assertCommandSuccess(uploadCommand, model, MESSAGE_UPLOAD, model);
    }
    */
}
//@@author
