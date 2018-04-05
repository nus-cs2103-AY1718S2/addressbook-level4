//@@author kokonguyen191
package seedu.recipe.ui.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.ui.GuiUnitTest;

public class MobileWikiaParserTest extends GuiUnitTest {

    private static final String WIKIA_RECIPE_URL_A =
            "http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice?useskin=wikiamobile";
    private static final String WIKIA_RECIPE_URL_B =
            "http://recipes.wikia.com/wiki/Beef_Tenderloin_with_Madeira_Sauce?useskin=wikiamobile";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MobileWikiaParser wikiaParserA;
    private MobileWikiaParser wikiaParserB;

    @Before
    public void setUp() throws IOException {
        wikiaParserA = new MobileWikiaParser(Jsoup.connect(WIKIA_RECIPE_URL_A).get());
        wikiaParserB = new MobileWikiaParser(Jsoup.connect(WIKIA_RECIPE_URL_B).get());
    }

    @Test
    public void constructor_nullArgument_throwsException() {
        thrown.expect(NullPointerException.class);
        new MobileWikiaParser(null);
        new MobileWikiaParser(null, "");
    }

    @Test
    public void equals() {
        String testDocumentString = "<html>Test</html>";
        String testUrl = "127.0.0.1";
        MobileWikiaParser wikiaParserA = new MobileWikiaParser(testDocumentString, testUrl);
        MobileWikiaParser wikiaParserB = new MobileWikiaParser(Jsoup.parse(testDocumentString, testUrl));
        assertEquals(wikiaParserA, wikiaParserB);
    }

    @Test
    public void getName_validRecipes_returnsResult() throws Exception {
        assertEquals(wikiaParserA.getName(), "Hainanese Chicken Rice");
        assertEquals(wikiaParserB.getName(), "Beef Tenderloin with Madeira Sauce");
    }

    @Test
    public void getIngredient_validRecipes_returnsResult() throws Exception {
        assertEquals(wikiaParserA.getIngredient(), "chicken, salt, spring onion, pandan leaves, "
                + "ginger, ginger, garlic, cinnamon, cloves, star anise, chicken broth, pandan leaves, salt,"
                + " light soy sauce, sesame oil, cucumber, tomatoes, coriander, lettuce, pineapple, fresh "
                + "chillies, ginger, garlic, vinegar, fish sauce, sugar, sweet soy sauce");
        assertEquals(wikiaParserB.getIngredient(), "1 cup of garlic, 2 cups of mustard, 3 tbs chopped "
                + "rosemary, 1 cup chopped thyme, 2 tsp garlic, 2 tsp vegtebale oil, 4 tsp salt, 1 tsp pepper, "
                + "3 cups of water, 4 tbs butter, 2 cups of red wine, 1 cup of garlic, 2 1/2 cups of corn, "
                + "4 cup of water, 2 tomatoes, 1 tsp chopped thyme, 1/2 tsp each sea salt and pepper");
    }

    @Test
    public void getInstruction_validRecipes_returnsResult() throws Exception {
        assertEquals(wikiaParserA.getInstruction(),
                "Boil water with spring Onion, ginger and pandan leaves, put in Chicken and cook till done,"
                        + " do not over cook. briefly dip in cold water and set aside to cool. Keep broth heated.\n"
                        + "Wash rice and drain. Finely shred ginger and garlic, fry in oil with cloves, cinammon and "
                        + "star anise till fragrant, add in rice and fry for several minutes. Transfer into rice "
                        + "cooker, add chicken broth, pinch of salt, pandan leaves and start cooking.\n"
                        + "Put all chili sauce ingredient in a mixer and grind till fine.\n"
                        + "Slice and arrange tomatoes and cucumbers on a big plate, cut Chicken into small pieces and p"
                        + "ut on top. Splash some light soy sauce and sesame oil over, throw a bunch of coriander "
                        + "on top.\nNext, Put broth in a bowl with lettuce, get ready chili sauce and sweet "
                        + "soy sauce. #Serve rice on a plate with spoon and folk.");
        assertEquals(wikiaParserB.getInstruction(),
                "Heat oven to 475 degrees and prepare a Large rimmed baking sheet coated with cooking spray.\n"
                        + "Spread beef with mustard.\n"
                        + "Mix herbs, garlic, oil, salt and pepper in a cup, press on mustard.\n"
                        + "Place beef on baking sheet.\n"
                        + "Roast 50 min.\n"
                        + "Remove to cutting board, cover tight with foil and let rest 15 min.]\n"
                        + "Slice beef and arrange on a serving platter, spoon on a little sauce. "
                        + "Serve with remaining sauce.\n"
                        + "Put butter in skillet.\n"
                        + "Add mushrooms, saute 2 min.\n"
                        + "Stir in garlic, boil 5 min.\n"
                        + "Stir corn into broth until blended. Add to skillet.\n"
                        + "Bring to a boil, boil stirring 5 min.\n"
                        + "Pour into gravy boat and serve with the beef.");
    }

    @Test
    public void getImageUrl_validRecipes_returnsResult() throws Exception {
        assertEquals(wikiaParserA.getImageUrl(), "https://vignette.wikia.nocookie.net/recipes/images/d/d3"
                + "/Chickenrice2.jpg/revision/latest/scale-to-width-down/340?cb=20080516004325");
    }

    @Test
    public void getUrl_validRecipes_returnsResult() throws Exception {
        assertEquals(wikiaParserA.getUrl(), WIKIA_RECIPE_URL_A);
        assertEquals(wikiaParserB.getUrl(), WIKIA_RECIPE_URL_B);
    }

    @Test
    public void parseRecipe_validRecipe_returnsValidCommand() throws Exception {
        assertEquals(wikiaParserA.parseRecipe(), "add\n"
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
                + "latest/scale-to-width-down/340?cb=20080516004325\n"
                + "url/http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice?useskin=wikiamobile");
        assertEquals(wikiaParserB.parseRecipe(), "add\n"
                + "name/Beef Tenderloin with Madeira Sauce\n"
                + "ingredient/1 cup of garlic, 2 cups of mustard, 3 tbs chopped rosemary,"
                + " 1 cup chopped thyme, 2 tsp garlic, 2 tsp vegtebale oil, 4 tsp salt, 1"
                + " tsp pepper, 3 cups of water, 4 tbs butter, 2 cups of red wine, 1 cup of"
                + " garlic, 2 1/2 cups of corn, 4 cup of water, 2 tomatoes, 1 tsp chopped thyme,"
                + " 1/2 tsp each sea salt and pepper\n"
                + "instruction/Heat oven to 475 degrees and prepare a Large rimmed baking sheet"
                + " coated with cooking spray.\n"
                + "Spread beef with mustard.\n"
                + "Mix herbs, garlic, oil, salt and pepper in a cup, press on mustard.\n"
                + "Place beef on baking sheet.\n"
                + "Roast 50 min.\n"
                + "Remove to cutting board, cover tight with foil and let rest 15 min.]\n"
                + "Slice beef and arrange on a serving platter, spoon on a little sauce. "
                + "Serve with remaining sauce.\n"
                + "Put butter in skillet.\n"
                + "Add mushrooms, saute 2 min.\n"
                + "Stir in garlic, boil 5 min.\n"
                + "Stir corn into broth until blended. Add to skillet.\n"
                + "Bring to a boil, boil stirring 5 min.\n"
                + "Pour into gravy boat and serve with the beef.\n"
                + "url/http://recipes.wikia.com/wiki/Beef_Tenderloin_with_Madeira_Sauce?useskin=wikiamobile");
    }
}
