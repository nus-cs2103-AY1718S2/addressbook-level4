//@@author amad-person
package seedu.address.ui;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    private static final double ICON_WIDTH = 25;
    private static final double ICON_HEIGHT = 25;
    private static final String ORDER_STATUS_DONE = "DONE";

    public final Order order;

    private final Logger logger = LogsCenter.getLogger(OrderCard.class);

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label orderInformation;

    @FXML
    private Label orderStatus;

    @FXML
    private Label priceAndQuantity;

    @FXML
    private Label totalPrice;

    @FXML
    private Label deliveryDate;

    @FXML
    private ImageView orderStatusIcon;

    @FXML
    private ImageView priceAndQuantityIcon;

    @FXML
    private ImageView totalPriceIcon;

    @FXML
    private ImageView deliveryDateIcon;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        orderInformation.setText(order.getOrderInformation().toString());
        orderStatus.setText(order.getOrderStatus().getOrderStatusValue().toUpperCase());
        setPriceAndQuantity(order);
        setTotalPrice(order);
        deliveryDate.setText("Deliver By: " + order.getDeliveryDate().toString());
        setImageSizeForAllImages();
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

    /**
     * Sets text for price and quantity label.
     */
    private void setPriceAndQuantity(Order order) {
        double priceValue = Double.valueOf(order.getPrice().toString());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        priceAndQuantity.setText("S$" + String.valueOf(decimalFormat.format(priceValue))
                + " X " + order.getQuantity().toString());
    }

    /**
     * Sets text for total price label.
     */
    private void setTotalPrice(Order order) {
        totalPrice.setText("Total: S$" + getTotalPrice(order.getPrice(), order.getQuantity()));
    }

    /**
     * Calculates total price of order.
     */
    private String getTotalPrice(Price price, Quantity quantity) {
        double priceValue = Double.valueOf(price.toString());
        int quantityValue = Integer.valueOf(quantity.toString());

        double totalPrice = priceValue * quantityValue;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        return String.valueOf(decimalFormat.format(totalPrice));
    }

    /**
     * Sets image sizes for all icons used in order card.
     */
    private void setImageSizeForAllImages() {
        orderStatusIcon.setFitWidth(ICON_WIDTH);
        orderStatusIcon.setFitHeight(ICON_HEIGHT);

        priceAndQuantityIcon.setFitWidth(ICON_WIDTH);
        priceAndQuantityIcon.setFitHeight(ICON_HEIGHT);

        totalPriceIcon.setFitWidth(ICON_WIDTH);
        totalPriceIcon.setFitHeight(ICON_HEIGHT);

        deliveryDateIcon.setFitWidth(ICON_WIDTH);
        deliveryDateIcon.setFitHeight(ICON_HEIGHT);
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
