//@@author amad-person
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.order.Order;
import seedu.address.ui.OrderCard;

/**
 * Provides a handle for {@code OrderListPanel} containing the list of {@code OrderCard}.
 */
public class OrderListPanelHandle extends NodeHandle<ListView<OrderCard>> {
    public static final String ORDER_LIST_VIEW_ID = "#orderListView";

    public OrderListPanelHandle(ListView<OrderCard> orderListPanelNode) {
        super(orderListPanelNode);
    }

    /**
     * Navigates the listview to display and select the order.
     */
    public void navigateToCard(Order order) {
        List<OrderCard> orderCards = getRootNode().getItems();
        Optional<OrderCard> matchingCard = orderCards.stream()
                .filter(orderCard -> orderCard.order.equals(order)).findFirst();

        if (!matchingCard.isPresent()) {
            throw  new IllegalArgumentException("Order does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });

        guiRobot.pauseForHuman();
    }

    /**
     * Returns the order card handle of an order associated with the {@code index} in the list.
     */
    public OrderCardHandle getOrderCardHandle(int index) {
        return getOrderCardHandle(getRootNode().getItems().get(index).order);
    }

    /**
     * Returns the order card handle of an order associated with the {@code order} in the list.
     */
    private OrderCardHandle getOrderCardHandle(Order order) {
        Optional<OrderCardHandle> handle = getRootNode().getItems().stream()
                .filter(orderCard -> orderCard.order.equals(order))
                .map(orderCard -> new OrderCardHandle(orderCard.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Order does not exist."));
    }
}
