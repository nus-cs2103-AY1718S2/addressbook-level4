//@@author kokonguyen191
package systemtests;

import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

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

        assertCommandSuccess("add\n"
                + "name/Hainanese Chicken Rice\n"
                + "ingredient/chicken, salt, spring onion, pandan leaves, ginger, ginger, garlic, cinnamon, "
                + "cloves, star anise, chicken broth, pandan leaves, salt, light soy sauce, sesame oil, "
                + "cucumber, tomatoes, coriander, lettuce, pineapple, fresh chillies, ginger, garlic, "
                + "vinegar, fish sauce, sugar, sweet soy sauce\n"
                + "instruction/Boil water with spring Onion, ginger and pandan leaves, put in Chicken "
                + "and cook till done, do not over cook. briefly dip in cold water and set aside to cool."
                + " Keep broth heated.\n"
                + "Wash rice and drain. Finely shred ginger and garlic, fry in oil with cloves, cinammon"
                + " and star anise till fragrant, add in rice and fry for several minutes. Transfer into "
                + "rice cooker, add chicken broth, pinch of salt, pandan leaves and start cooking.\n"
                + "Put all chili sauce ingredient in a mixer and grind till fine.\n"
                + "Slice and arrange tomatoes and cucumbers on a big plate, cut Chicken into small pieces"
                + " and put on top. Splash some light soy sauce and sesame oil over, throw a bunch of "
                + "coriander on top.\n"
                + "Next, Put broth in a bowl with lettuce, get ready chili sauce and sweet soy sauce. "
                + "#Serve rice on a plate with spoon and folk.\n"
                + "img/https://vignette.wikia.nocookie.net/recipes/images/d/d3/Chickenrice2.jpg/revision/"
                + "latest/scale-to-width-down/180?cb=20080516004325\n"
                + "url/http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice");

        command = SelectCommand.COMMAND_WORD + " " + INDEX_SECOND_RECIPE.getOneBased();
        executeCommand(command);

        assertCommandSuccess("add\n"
                + "name/Asian Chicken Salad\n"
                + "ingredient/chicken, cabbage, mushrooms, carrots, cilantro, cucumber, "
                + "green onions, mandarin orange, tangerine, black pepper\n"
                + "instruction/In a large bowl, combine chicken, cabbage, mushrooms, "
                + "carrots, cilantro, cucumber, and dressing.\n"
                + "Toss well.\n"
                + "Top with green onions and tangerine sections.\n"
                + "Pepper to taste.\n"
                + "img/https://vignette.wikia.nocookie.net/recipes/images/3/35/Chicken"
                + "-salad-ck-1940987-l.jpg/revision/latest/scale-to-width-down/340?cb=20131015235502\n"
                + "url/http://recipes.wikia.com/wiki/Asian_Chicken_Salad?useskin=wikiamobile");
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
