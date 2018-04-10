//@@author amad-person
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ChangeOrderStatusEvent;
import seedu.address.commons.events.ui.OrderPanelSelectionChangedEvent;
import seedu.address.model.order.Order;

/**
 * Panel containing orders to be managed by the salesperson.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    @FXML
    private ListView<OrderCard> orderListView;

    public OrderListPanel(ObservableList<Order> orderList) {
        super(FXML);
        setConnections(orderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Order> orderList) {
        ObservableList<OrderCard> mappedList = EasyBind.map(
                orderList, (order) -> new OrderCard(order, orderList.indexOf(order) + 1));
        orderListView.setItems(mappedList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        orderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in order list panel changed to : '" + newValue + "'");
                        raise(new OrderPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code OrderCard} at {@code index} and selects it.
     * @param index index of order card to be scrolled to.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            orderListView.scrollTo(index);
            orderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @FXML
    private void handleChangeOrderStatus(ChangeOrderStatusEvent event) {
        // TODO: change background of listcell based on order status change
    }

    @Subscribe
    private void handleChangeOrderStatusEvent(ChangeOrderStatusEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeOrderStatus(event);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<OrderCard> {

        @Override
        protected void updateItem(OrderCard order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(order.getRoot());
            }
        }
    }
}
