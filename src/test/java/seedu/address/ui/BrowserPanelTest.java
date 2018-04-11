package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    //@@author Articho28
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(BrowserPanel.ATM_SEARCH_PAGE_URL);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(BrowserPanel.ADDRESS_SEARCH_PAGE_URL
                +  BOB.getAddress().value.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
