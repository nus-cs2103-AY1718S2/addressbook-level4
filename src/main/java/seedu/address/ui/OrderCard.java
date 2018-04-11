//@@author amad-person
package seedu.address.ui;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {
    private static final String FXML = "OrderListCard.fxml";
    private static final String ORDER_STATUS_DONE = "DONE";

    public final Order order;

    private final Logger logger = LogsCenter.getLogger(OrderCard.class);

    @FXML
    private HBox cardPane;

    @FXML
    private Label orderInformation;

    @FXML
    private Label orderStatus;

    @FXML
    private Label id;

    @FXML
    private Label priceAndQuantity;

    @FXML
    private Label totalPrice;

    @FXML
    private Label deliveryDate;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        orderInformation.setText(order.getOrderInformation().toString());
        orderStatus.setText(order.getOrderStatus().getCurrentOrderStatus().toUpperCase());
        setPriceAndQuantity(order);
        setTotalPrice(order);
        deliveryDate.setText("Deliver By: " + order.getDeliveryDate().toString());
    }

    /**
     * Returns true if order status equals done.
     */
    public boolean isOrderStatusDone() {
        if (orderStatus.getText().equals(ORDER_STATUS_DONE)) {
            return true;
        }

        return false;
    }

    private void setPriceAndQuantity(Order order) {
        double priceValue = Double.valueOf(order.getPrice().toString());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        priceAndQuantity.setText("S$" + String.valueOf(decimalFormat.format(priceValue))
                + " X " + order.getQuantity().toString());
    }

    private void setTotalPrice(Order order) {
        totalPrice.setText("Total: S$" + getTotalPrice(order.getPrice(), order.getQuantity()));
    }

    private String getTotalPrice(Price price, Quantity quantity) {
        double priceValue = Double.valueOf(price.toString());
        int quantityValue = Integer.valueOf(quantity.toString());

        double totalPrice = priceValue * quantityValue;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        return String.valueOf(decimalFormat.format(totalPrice));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderCard)) {
            return false;
        }

        // state check
        OrderCard card = (OrderCard) other;
        return id.getText().equals(card.id.getText())
                && order.equals(card.order);
    }
}
