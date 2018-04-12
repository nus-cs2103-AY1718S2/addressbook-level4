//@@author ZhangYijiong
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.OrderPanelSelectionChangedEvent;
import seedu.address.model.task.Task;

/**
 * Implementation follows {@code PersonListPanel}
 * Panel containing the queue of orders.
 */
public class OrderQueuePanel extends UiPart<Region> {
    private static final String FXML = "OrderQueuePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderQueuePanel.class);

    @FXML
    private ListView<OrderCard> orderListView;

    public OrderQueuePanel(ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Task> taskList) {
        ObservableList<OrderCard> mappedList = EasyBind.map(
                taskList, (task) -> new OrderCard(task, taskList.indexOf(task) + 1));
        orderListView.setItems(mappedList);
        orderListView.setCellFactory(listView -> new OrderQueueViewCell());
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
     * Scrolls to the {@code OrderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            orderListView.scrollTo(index);
            orderListView.getSelectionModel().clearAndSelect(index);
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code OrderCard}.
     */
    class OrderQueueViewCell extends ListCell<OrderCard> {

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
