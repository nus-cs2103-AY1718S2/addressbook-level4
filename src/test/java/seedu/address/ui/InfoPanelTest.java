package seedu.address.ui;

import static guitests.guihandles.InfoPanelUtil.waitUntilInfoPanelLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class InfoPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InfoPanel infoPanel;
    private InfoPanelHandle infoPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> infoPanel = new InfoPanel());
        uiPartRule.setUiPart(infoPanel);

        infoPanelHandle = new InfoPanelHandle(infoPanel.getRoot());
    }

    @Test
    public void display() {
        // Default selected person is null
        assertNull(infoPanelHandle.getLoadedPerson());

        // Post changed event
        postNow(selectionChangedEventStub);
        waitUntilInfoPanelLoaded(infoPanelHandle);
        assertEquals(ALICE, infoPanelHandle.getLoadedPerson());
    }

    @Test
    public void responsive() {
        // Select someone first
        postNow(selectionChangedEventStub);
        waitUntilInfoPanelLoaded(infoPanelHandle);
        assertEquals(ALICE, infoPanelHandle.getLoadedPerson());

        // Test responsiveness
        infoPanelHandle.setWidthAndWait(InfoPanel.SPLIT_MIN_WIDTH - 100);
        guiRobot.pauseForHuman();
        assertTrue(infoPanelHandle.isResponsiveSingle());

        infoPanelHandle.setWidthAndWait(InfoPanel.SPLIT_MIN_WIDTH + 100);
        guiRobot.pauseForHuman();
        assertTrue(infoPanelHandle.isResponsiveSplit());
    }
}
