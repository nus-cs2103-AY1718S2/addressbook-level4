package seedu.recipe.ui.util;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.recipe.ui.BrowserPanel;
import seedu.recipe.ui.GuiUnitTest;

public class WebParserHandlerTest extends GuiUnitTest {

    private static final String WIKIA_RECIPE_URL = "http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice";

    private WebParserHandler webParserHandler;
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(true));
        uiPartRule.setUiPart(browserPanel);
        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void getWebParser_nothingLoaded_returnNull() throws Exception {
        webParserHandler = new WebParserHandler(browserPanel.getBrowser());
        WebParser actualWebParser = webParserHandler.getWebParser();
        assertEquals(null, actualWebParser);
    }

    @Test
    public void getWebParser_unparsableWebsite_returnNull() throws Exception {
        assertNullWebParser("https://google.com/");
    }

    @Test
    public void getWebParser_wikiaLoaded_returnWikiaParser() throws Exception {
        assertWebParser(WIKIA_RECIPE_URL, new WikiaParser(new Document("")));
    }

    /**
     * Asserts that the created WebParser from the {@code url} matches the {@code expectedWebParser}
     */
    private void assertWebParser(String url, WebParser expectedWebParser) {
        browserPanel.loadPage(url);
        waitUntilBrowserLoaded(browserPanelHandle);
        webParserHandler = new WebParserHandler(browserPanel.getBrowser());
        WebParser actualWebParser = webParserHandler.getWebParser();
        assertEquals(expectedWebParser.getClass(), actualWebParser.getClass());
    }

    /**
     * Asserts that the created WebParser from the {@code url} doesn't exist
     */
    private void assertNullWebParser(String url) {
        browserPanel.loadPage(url);
        waitUntilBrowserLoaded(browserPanelHandle);
        webParserHandler = new WebParserHandler(browserPanel.getBrowser());
        WebParser actualWebParser = webParserHandler.getWebParser();
        assertEquals(null, actualWebParser);
    }
}
