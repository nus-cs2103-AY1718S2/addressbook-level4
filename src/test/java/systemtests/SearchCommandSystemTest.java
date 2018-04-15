//@@author kokonguyen191
package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import org.junit.Test;

import seedu.recipe.logic.commands.SearchCommand;
import seedu.recipe.logic.commands.util.WikiaQueryHandler;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;

/**
 * A system test class for the search command, which contains interaction with other UI components.
 */
public class SearchCommandSystemTest extends RecipeBookSystemTest {

    private Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void search() {
        String query = "bot";
        assertCommandSuccess(query);

        query = "chicken rice";
        assertCommandSuccess(query);

        query = "blah";
        assertCommandSuccess(query);
    }

    /**
     * Assert that the {@code BrowserPanel} is currently loaded with given {@code url}
     */
    private void assertBrowserPanel(String url) {
        assertEquals(getBrowserPanel().getLoadedUrl().toExternalForm(), url);
    }

    /**
     * Assert that the {@code SearchCommand} was executed correctly with the given {@code query}.
     */
    private void assertCommandSuccess(String query) {
        executeCommand(SearchCommand.COMMAND_WORD + " " + query);

        WikiaQueryHandler wikiaQueryHandler = new WikiaQueryHandler(query.replaceAll("\\s+", "+"));
        int noOfResults = wikiaQueryHandler.getQueryNumberOfResults();

        if (noOfResults == 0) {
            assertApplicationDisplaysExpected("", SearchCommand.MESSAGE_NO_RESULT, model);
        } else {
            assertApplicationDisplaysExpected("", String.format(SearchCommand.MESSAGE_SUCCESS, noOfResults), model);
            assertBrowserPanel(wikiaQueryHandler.getRecipeQueryUrl());
        }
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
