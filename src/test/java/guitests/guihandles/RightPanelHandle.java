package guitests.guihandles;

import javafx.scene.layout.StackPane;

/**
 * A handler for {@code RightPanel} of the Ui.
 */
public class RightPanelHandle extends NodeHandle<StackPane> {
    public static final String RIGHT_PANEL_ID = "#rightPlaceHolder";

    private OrderListPanelHandle orderListPanelHandle;
    private CalendarEntryListPanelHandle calendarEntryListPanelHandle;

    public RightPanelHandle(StackPane rootNode) {
        super(rootNode);
    }

    /**
     * Sets Up {@code OrderListPanelHandle}.
     * This method should only be invoked only after {@code CenterPanel} adds {@code OrderListPanel} to its root node.
     * Otherwise, orderListPanelHandle will be null.
     */
    public void setUpOrderListPanelHandle() {
        orderListPanelHandle = new OrderListPanelHandle(getChildNode(OrderListPanelHandle.ORDER_LIST_VIEW_ID));
    }

    /**
     * Sets Up {@code CalendarEntryListPanelHandle}.
     * This method should only be invoked only after {@code CenterPanel} adds {@code CalendarEntryListPanel}
     * to its root node.
     * Otherwise, calendarEntryListPanelHandle will be null.
     */
    public void setUpCalendarEntryListPanelHandle() {
        calendarEntryListPanelHandle = new CalendarEntryListPanelHandle(getChildNode(
                CalendarEntryListPanelHandle.CALENDAR_ENTRY_LIST_VIEW_ID));
    }

    public OrderListPanelHandle getOrderListPanelHandle() {
        setUpOrderListPanelHandle();
        return orderListPanelHandle;
    }

    public CalendarEntryListPanelHandle getCalendarEntryListPanelHandle() {
        setUpCalendarEntryListPanelHandle();
        return calendarEntryListPanelHandle;
    }


}
