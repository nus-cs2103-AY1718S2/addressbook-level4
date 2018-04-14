package seedu.progresschecker.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.progresschecker.testutil.EventsUtil.postNow;
import static seedu.progresschecker.testutil.TypicalPersons.ALICE;
import static seedu.progresschecker.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.progresschecker.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.progresschecker.MainApp;
import seedu.progresschecker.commons.events.ui.LoadTaskEvent;
import seedu.progresschecker.commons.events.ui.LoadUrlEvent;
import seedu.progresschecker.commons.events.ui.PersonPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    //@@author EdwardKSG
    private static final String webpage = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "    <!-- <link rel=\"stylesheet\" href=\"DarkTheme.css\"> -->\n"
            + "</head>\n"
            + "\n"
            + "<body class=\"background\">\n"
            + "</body>\n"
            + "</html>";

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private LoadUrlEvent loadUrlEventStub;
    private LoadTaskEvent loadTaskEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        loadTaskEventStub = new LoadTaskEvent(webpage);
        String expectedUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE).toString();

        loadUrlEventStub = new LoadUrlEvent(expectedUrl);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        postNow(loadTaskEventStub);
        String expectedTitle = null;

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedTitle, browserPanelHandle.getLoadedTitle());

        postNow(loadUrlEventStub);
        URL expectedUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);;

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedUrl, browserPanelHandle.getLoadedUrl());
    }
}
