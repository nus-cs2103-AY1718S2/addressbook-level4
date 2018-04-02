//@@author kokonguyen191
package seedu.recipe.logic.commands.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.util.WikiaQueryHandler.QUERY_URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WikiaQueryHandlerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullQuery_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new WikiaQueryHandler(null);
    }

    @Test
    public void constructor_invalidQuery_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new WikiaQueryHandler("");
    }

    @Test
    public void getRecipeQueryUrl_normalString_success() throws Exception {
        WikiaQueryHandler testWikiaQueryHandler = new WikiaQueryHandler("chickens soup");
        assertEquals(testWikiaQueryHandler.getRecipeQueryUrl(), QUERY_URL + "chickens soup");
    }

    @Test
    public void getQueryNumberOfResults_zeroResults_success() throws Exception {
        WikiaQueryHandler wikiaQueryHandlerWithZeroResults = new WikiaQueryHandler("blah");
        assertEquals(wikiaQueryHandlerWithZeroResults.getQueryNumberOfResults(), 0);
    }

    // It is very hard to give a concrete number for this test as recipes are added everyday
    @Test
    public void getQueryNumberOfResults_bigResults_success() throws Exception {
        WikiaQueryHandler wikiaQueryHandlerWithZeroResults = new WikiaQueryHandler("chicken");
        assertTrue(wikiaQueryHandlerWithZeroResults.getQueryNumberOfResults() > 5000);
    }

    // THIS TEST MIGHT FAIL IN THE FUTURE! PLEASE UPDATE IF IT FAILS
    @Test
    public void getQueryNumberOfResults_fourResults_success() throws Exception {
        WikiaQueryHandler wikiaQueryHandlerWithFourResults = new WikiaQueryHandler("bot");
        assertEquals(wikiaQueryHandlerWithFourResults.getQueryNumberOfResults(), 4);
    }
}
