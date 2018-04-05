//@@author kokonguyen191
package systemtests;

import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;
import static seedu.recipe.testutil.WikiaRecipes.MOBILE_WIKIA_UGANDAN_ADD_COMMAND;
import static seedu.recipe.testutil.WikiaRecipes.WIKIA_CHICKEN_ADD_COMMAND;

import org.junit.Test;

import seedu.recipe.logic.commands.ParseCommand;
import seedu.recipe.logic.commands.SelectCommand;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

/**
 * A system test class for the search command, which contains interaction with other UI components.
 */
public class ParseCommandSystemTest extends RecipeBookSystemTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void parse() {
        String command = SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RECIPE.getOneBased();
        executeCommand(command);

        assertCommandSuccess(WIKIA_CHICKEN_ADD_COMMAND);

        command = SelectCommand.COMMAND_WORD + " " + INDEX_SECOND_RECIPE.getOneBased();
        executeCommand(command);

        assertCommandSuccess(MOBILE_WIKIA_UGANDAN_ADD_COMMAND);
    }

    /**
     * Assert that the {@code SearchCommand} was executed correctly
     * and the current content of the CommandBox is {@code content}
     */
    private void assertCommandSuccess(String content) {

        executeCommand(ParseCommand.COMMAND_WORD);
        assertStatusBarUnchanged();
        assertCommandBoxContent(content);
    }
}
