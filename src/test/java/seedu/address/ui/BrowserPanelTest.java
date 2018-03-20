package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertPanelDisplaysPerson;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.PersonCardHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonCard selectionChangedPersonCardStub;
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedPersonCardStub = new PersonCard(ALICE, 0);
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(selectionChangedPersonCardStub);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person & person card detail
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL("https://calendar.google.com/calendar/embed?src="
                + "8nfr293d26bcmd9oubia86re4k%40group.calendar.google.com&ctz=Asia%2FSingapore");

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
        assertPanelDisplay(new PersonCardHandle(selectionChangedPersonCardStub.getRoot()), browserPanelHandle);
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertPanelDisplay(PersonCardHandle expectedPersonCard, BrowserPanelHandle browserPanelHandle) {
        guiRobot.pauseForHuman();
        assertPanelDisplaysPerson(expectedPersonCard, browserPanelHandle);
    }
}
