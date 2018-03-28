package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to an order card in the order list panel.
 */
public class OrderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ORDER_INFORMATION_ID = "#orderInformation";
    private static final String PRICE_AND_QUANTITY_ID = "#priceAndQuantity";
    private static final String TOTAL_PRICE_ID = "#totalPrice";
    private static final String DELIVERY_DATE_ID = "#deliveryDate";

    private final Label idLabel;
    private final Label orderInformationLabel;
    private final Label priceAndQuantityLabel;
    private final Label totalPriceLabel;
    private final Label deliveryDateLabel;

    public OrderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.orderInformationLabel = getChildNode(ORDER_INFORMATION_ID);
        this.priceAndQuantityLabel = getChildNode(PRICE_AND_QUANTITY_ID);
        this.totalPriceLabel = getChildNode(TOTAL_PRICE_ID);
        this.deliveryDateLabel = getChildNode(DELIVERY_DATE_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getOrderInformation() {
        return orderInformationLabel.getText();
    }

    public String getPriceAndQuantity() {
        return priceAndQuantityLabel.getText();
    }

    public String getTotalPrice() {
        return totalPriceLabel.getText();
    }

    public String getDeliveryDate() {
        return deliveryDateLabel.getText();
    }
}
