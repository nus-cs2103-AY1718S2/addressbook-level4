//@@author kokonguyen191
package seedu.recipe.logic.commands;

import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

public class ParseCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

//    @Test
//    public void equals() {
//    }

    @Test
    public void execute_nothing_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        ParseCommand parseCommand = new ParseCommand();
        parseCommand.execute();
    }
}
