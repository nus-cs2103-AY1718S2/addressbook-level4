//@@author amad-person
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalOrders.getTypicalOrders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOrder;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.OrderCardHandle;
import guitests.guihandles.OrderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.Order;

public class OrderListPanelTest extends GuiUnitTest {
    private static final ObservableList<Order> TYPICAL_ORDERS =
            FXCollections.observableList(getTypicalOrders());

    private OrderListPanelHandle orderListPanelHandle;

    @Before
    public void setUp() {
        OrderListPanel orderListPanel = new OrderListPanel(TYPICAL_ORDERS);
        uiPartRule.setUiPart(orderListPanel);

        orderListPanelHandle = new OrderListPanelHandle(getChildNode(orderListPanel.getRoot(),
                OrderListPanelHandle.ORDER_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_ORDERS.size(); i++) {
            orderListPanelHandle.navigateToCard(TYPICAL_ORDERS.get(i));
            Order expectedOrder = TYPICAL_ORDERS.get(i);
            OrderCardHandle actualCard = orderListPanelHandle.getOrderCardHandle(i);

            assertCardDisplaysOrder(expectedOrder, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
