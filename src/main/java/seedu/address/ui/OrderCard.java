//@@author amad-person
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A UI component that displays information of an {@code Order}.
 */
public class OrderCard extends UiPart<Region> {
    private static final String FXML = "OrderListCard.fxml";

    public final Order order;

    @FXML
    private HBox cardPane;

    @FXML
    private Label orderInformation;

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
        priceAndQuantity.setText("S$" + order.getPrice().toString() + " X " + order.getQuantity().toString());
        totalPrice.setText("Total: S$" + getTotalPrice(order.getPrice(), order.getQuantity()));
        deliveryDate.setText("Deliver By: " + order.getDeliveryDate().toString());
    }

    private String getTotalPrice(Price price, Quantity quantity) {
        double priceValue = Double.valueOf(price.toString());
        int quantityValue = Integer.valueOf(quantity.toString());

        return String.valueOf(priceValue * quantityValue);
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
