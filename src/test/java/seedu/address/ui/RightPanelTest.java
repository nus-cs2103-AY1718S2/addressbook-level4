package seedu.address.ui;

import static junit.framework.TestCase.assertNotNull;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarEntryListPanelHandle;
import guitests.guihandles.OrderListPanelHandle;
import guitests.guihandles.RightPanelHandle;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.commons.events.ui.DisplayOrderListEvent;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RightPanelTest extends GuiUnitTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendarManagerWithEntries(),
            new UserPrefs());

    private OrderListPanelHandle orderListPanelHandle;
    private CalendarEntryListPanelHandle calendarEntryListPanelHandle;
    private RightPanelHandle rightPanelHandle;

    @Before
    public void setUp() {
        RightPanel rightPanel = new RightPanel(model.getFilteredOrderList(), model.getFilteredCalendarEntryList());
        uiPartRule.setUiPart(rightPanel);

        rightPanelHandle = new RightPanelHandle(getChildNode(rightPanel.getRoot(), RightPanelHandle.RIGHT_PANEL_ID));
    }

    @Test
    public void handleDisplayCalendarEntryListEvent() {
        postNow(new DisplayCalendarEntryListEvent());
        guiRobot.pauseForHuman();

        calendarEntryListPanelHandle = rightPanelHandle.getCalendarEntryListPanelHandle();
        assertCalendarEntryListDisplayed(calendarEntryListPanelHandle);

    }

    @Test
    public void handleDisplayOrderListEvent() {

        // OrderListPanel is displayed on initialising RightPanel, thus an event is posted to switch displayed panel
        // in RightPanel to CalendarEntryListPanel, before posting {@code DisplayOrderListEvent}.
        postNow(new DisplayCalendarEntryListEvent());
        guiRobot.pauseForHuman();
        postNow(new DisplayOrderListEvent());
        guiRobot.pauseForHuman();

        orderListPanelHandle = rightPanelHandle.getOrderListPanelHandle();
        assertOrderListDisplayed(orderListPanelHandle);

    }

    /**
     * Asserts that {@code RightPanel} is displaying {@code CalendarEntryListPanel} by
     * checking that {@code calendarEntryListPanelHandle} is not null.
     * {@code calendarEntryListPanelHandle} will be null if {@code rightPanelHandler}'s root node does not contain
     * {@code calendarEntryListPanelHandle}'s root node.
     */
    private void assertCalendarEntryListDisplayed(CalendarEntryListPanelHandle calendarEntryListPanelHandle) {
        assertNotNull(calendarEntryListPanelHandle);
    }

    /**
     * Asserts that {@code RightPanel} is displaying {@code OrderListPanel} by
     * checking that {@code orderListPanelHandle} is not null.
     * {@code orderListPanelHandle} will be null if {@code rightPanelHandler}'s root node does not contain
     * {@code orderListPanelHandle}'s root node.
     */
    private void assertOrderListDisplayed(OrderListPanelHandle orderListPanelHandle) {
        assertNotNull(orderListPanelHandle);
    }
}
