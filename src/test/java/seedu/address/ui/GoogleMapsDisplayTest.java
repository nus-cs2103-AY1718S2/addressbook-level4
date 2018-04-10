package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_ADDRESS_1;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.GoogleMapsDisplay.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GoogleMapsDisplayHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.GoogleMapsEvent;

//@@author jingyinno
public class GoogleMapsDisplayTest extends GuiUnitTest {

    private GoogleMapsDisplay googleMapsDisplay;
    private GoogleMapsDisplayHandle googleMapsDisplayHandle;
    private GoogleMapsEvent googleMapsChangedStub;

    @Before
    public void setUp() {
        guiRobot.interact(() -> googleMapsDisplay = new GoogleMapsDisplay());
        uiPartRule.setUiPart(googleMapsDisplay);
        googleMapsChangedStub = new GoogleMapsEvent(VALID_LOCATION_ADDRESS_1, true);

        googleMapsDisplayHandle = new GoogleMapsDisplayHandle(googleMapsDisplay.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, googleMapsDisplayHandle.getLoadedUrl());

        postNow(googleMapsChangedStub);
        URL expectedMapsUrl = new URL(GoogleMapsDisplay.MAP_SEARCH_URL_PREFIX
                + VALID_LOCATION_ADDRESS_1.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(googleMapsDisplayHandle);
        assertNotEquals(expectedMapsUrl, googleMapsDisplayHandle.getLoadedUrl());
    }
}
