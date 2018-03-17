package seedu.address.ui;

//import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.StudentPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private StudentPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new StudentPanelSelectionChangedEvent(new StudentCard(ALICE, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a student
        postNow(selectionChangedEventStub);
        URL expectedStudentUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ALICE.getAddress().urlstyle());

        //waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedStudentUrl, browserPanelHandle.getLoadedUrl());
    }
}
