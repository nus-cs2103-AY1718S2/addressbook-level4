//@@author kokonguyen191
package seedu.recipe.ui.parser;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.nodes.Document;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import guitests.guihandles.BrowserPanelHandle;
import seedu.recipe.ui.BrowserPanel;
import seedu.recipe.ui.GuiUnitTest;

public class WebParserHandlerTest extends GuiUnitTest {

    private static final String WIKIA_RECIPE_URL = "http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private WebParserHandler webParserHandler;
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Test
    public void constructor_nullBrowser_throwsException() throws Exception {
        thrown.expect(NullPointerException.class);
        webParserHandler = new WebParserHandler(null);
    }

    @Test
    public void getWebParser_nothingLoaded_returnNull() throws Exception {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(true));
        uiPartRule.setUiPart(browserPanel);
        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
        webParserHandler = new WebParserHandler(browserPanel.getBrowser());
        WebParser actualWebParser = webParserHandler.getWebParser();
        assertEquals(null, actualWebParser);
    }

    @Test
    public void getWebParser_unparsableWebsite_returnNull() throws Exception {
        assertNullWebParser("https://google.com/", "");
    }

    @Test
    public void getWebParser_wikiaLoaded_returnWikiaParser() throws Exception {
        assertWebParser(WIKIA_RECIPE_URL, "<html><div id=\"mw-content-text\">something</div></html>",
                new WikiaParser(new Document("")));
    }

    @Test
    public void getWebParser_mobileWikiaLoaded_returnMobileWikiaParser() throws Exception {
        assertWebParser(WIKIA_RECIPE_URL, "<html></html>",
                new MobileWikiaParser(new Document("")));
    }

    /**
     * Asserts that the created WebParser from the {@code url} matches the {@code expectedWebParser}
     */
    private void assertWebParser(String url, String documentString, WebParser expectedWebParser)
            throws ParserConfigurationException {
        WebParser actualWebParser = WebParserHandler.getWebParser(url, documentString);
        assertEquals(expectedWebParser.getClass(), actualWebParser.getClass());
    }

    /**
     * Asserts that the created WebParser from the {@code url} doesn't exist
     */
    private void assertNullWebParser(String url, String documentString) throws ParserConfigurationException {
        WebParser actualWebParser = WebParserHandler.getWebParser(url, documentString);
        assertEquals(null, actualWebParser);
    }
}
