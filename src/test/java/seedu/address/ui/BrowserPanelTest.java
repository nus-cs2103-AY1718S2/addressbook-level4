package seedu.address.ui;

import static seedu.address.testutil.TypicalCinemas.ALJUNIED;

import org.junit.Before;

import seedu.address.commons.events.ui.CinemaPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private CinemaPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;

    @Before
    public void setUp() {
        selectionChangedEventStub = new CinemaPanelSelectionChangedEvent(new CinemaCard(ALJUNIED, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);
    }
}
