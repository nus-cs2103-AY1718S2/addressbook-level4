package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayCalendarEntryListEvent;
import seedu.address.commons.events.ui.DisplayOrderListEvent;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.order.Order;

/**
 * The Right Panel of the App that can switch between Order List Panel and CalendarEntry List Panel
 */
public class RightPanel extends UiPart<Region> {
    private static final String FXML = "RightPanel.fxml";

    private OrderListPanel orderListPanel;
    private CalendarEntryListPanel calendarEntryListPanel;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private StackPane rightPlaceholder;

    public RightPanel(ObservableList<Order> orderList, ObservableList<CalendarEntry> calendarEntryList) {
        super(FXML);

        orderListPanel = new OrderListPanel(orderList);
        calendarEntryListPanel = new CalendarEntryListPanel(calendarEntryList);

        // Displays Order List by default.
        displayOrderListPanel();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays the OrderList Panel.
     */
    private void displayOrderListPanel() {
        if (!rightPlaceholder.getChildren().contains(orderListPanel.getRoot())) {
            rightPlaceholder.getChildren().clear();
            rightPlaceholder.getChildren().add(orderListPanel.getRoot());
        }
    }

    /**
     * Displays the CalendarEntryList Panel.
     */
    private void displayCalendarEntryListPanel() {
        if (!rightPlaceholder.getChildren().contains(calendarEntryListPanel.getRoot())) {
            rightPlaceholder.getChildren().clear();
            rightPlaceholder.getChildren().add(calendarEntryListPanel.getRoot());
        }
    }

    @Subscribe
    public void handleDisplayOrderListEvent(DisplayOrderListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayOrderListPanel();
    }

    @Subscribe
    public void handleDisplayCalendarEntryListEvent(DisplayCalendarEntryListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayCalendarEntryListPanel();
    }
}
