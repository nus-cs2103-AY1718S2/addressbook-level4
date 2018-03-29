//@@Author kokonguyen191
package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void execute() {
        SearchCommand searchCommand = new SearchCommand();
        assertCommandFailure(searchCommand, model, "DUH");
    }
}
