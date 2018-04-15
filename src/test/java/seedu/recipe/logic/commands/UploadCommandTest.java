//@@author nicholasangcx
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.UploadCommand.MESSAGE_ACCESS_TOKEN;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.ui.util.CloudStorageUtil;

public class UploadCommandTest {

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

    @Test
    public void execute_inputWithValidArgs_noAccessToken() {
        UploadCommand uploadCommand = new UploadCommand("recipebook.xml");
        CloudStorageUtil.setAccessToken(null);
        assertCommandSuccess(uploadCommand, model, MESSAGE_ACCESS_TOKEN, model);
    }
}
//@@author
