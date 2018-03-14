package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.DetailsPanelHandle;
import seedu.address.commons.events.ui.SwitchTabRequestEvent;

public class DetailsPanelTest extends GuiUnitTest {

    private DetailsPanelHandle detailsPanelHandle;

    @Before
    public void setUp() {
        DetailsPanel detailsPanel = new DetailsPanel();
        uiPartRule.setUiPart(detailsPanel);

        detailsPanelHandle = new DetailsPanelHandle(getChildNode(detailsPanel.getRoot(), "#tabPane"));
    }

    @Test
    public void display() {
        // default tab
        assertEquals(0, detailsPanelHandle.getCurrentTab());

        postNow(new SwitchTabRequestEvent(1));
        assertEquals(1, detailsPanelHandle.getCurrentTab());
        postNow(new SwitchTabRequestEvent(0));
        assertEquals(0, detailsPanelHandle.getCurrentTab());
        postNow(new SwitchTabRequestEvent(0));
        assertEquals(0, detailsPanelHandle.getCurrentTab());
    }
}
