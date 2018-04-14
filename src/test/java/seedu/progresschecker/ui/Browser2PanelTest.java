package seedu.progresschecker.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowser2Loaded;
import static org.junit.Assert.assertEquals;
import static seedu.progresschecker.testutil.EventsUtil.postNow;
import static seedu.progresschecker.ui.Browser2Panel.DEFAULT_PAGE;
import static seedu.progresschecker.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.Browser2PanelHandle;
import seedu.progresschecker.MainApp;
import seedu.progresschecker.commons.events.ui.LoadBarEvent;

//@@author EdwardKSG
public class Browser2PanelTest extends GuiUnitTest {
    private static final String webpage = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "    <!-- <link rel=\"stylesheet\" href=\"DarkTheme.css\"> -->\n"
            + "</head>\n"
            + "\n"
            + "<body class=\"background\">\n"
            + "</body>\n"
            + "</html>";

    private Browser2Panel browser2Panel;
    private Browser2PanelHandle browser2PanelHandle;

    private LoadBarEvent loadBarEventStub;

    @Before
    public void setUp() {
        loadBarEventStub = new LoadBarEvent(webpage);

        guiRobot.interact(() -> browser2Panel = new Browser2Panel());
        uiPartRule.setUiPart(browser2Panel);

        browser2PanelHandle = new Browser2PanelHandle(browser2Panel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browser2PanelHandle.getLoadedUrl());

        postNow(loadBarEventStub);
        String expectedTitle = null;

        waitUntilBrowser2Loaded(browser2PanelHandle);
        assertEquals(expectedTitle, browser2PanelHandle.getLoadedTitle());
    }
}
