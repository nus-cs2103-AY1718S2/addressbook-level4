//@@author amad-person
package seedu.address.testutil;

import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {

    public static final String DEFAULT_ORDER_INFORMATION = "Books";
    public static final String DEFAULT_PRICE = "15.00";
    public static final String DEFAULT_QUANTITY = "5";
    public static final String DEFAULT_DELIVERY_DATE = "10-05-2018";

    private OrderInformation orderInformation;
    private Price price;
    private Quantity quantity;
    private DeliveryDate deliveryDate;

    public OrderBuilder() {
        orderInformation = new OrderInformation(DEFAULT_ORDER_INFORMATION);
        price = new Price(DEFAULT_PRICE);
        quantity = new Quantity(DEFAULT_QUANTITY);
        deliveryDate = new DeliveryDate(DEFAULT_DELIVERY_DATE);
    }

    /**
     * Initializes the OrderBuilder with the data of {@code orderToCopy}.
     */
    public OrderBuilder(Order orderToCopy) {
        orderInformation = orderToCopy.getOrderInformation();
        price = orderToCopy.getPrice();
        quantity = orderToCopy.getQuantity();
        deliveryDate = orderToCopy.getDeliveryDate();
    }

    /**
     * Sets the {@code OrderInformation} of the {@code Order} that we are building.
     */
    public OrderBuilder withOrderInformation(String orderInformation) {
        this.orderInformation = new OrderInformation(orderInformation);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Order} that we are building.
     */
    public OrderBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Order} that we are building.
     */
    public OrderBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code DeliveryDate} of the {@code Order} that we are building.
     */
    public OrderBuilder withDeliveryDate(String deliveryDate) {
        this.deliveryDate = new DeliveryDate(deliveryDate);
        return this;
    }

    public Order build() {
        return new Order(orderInformation, price, quantity, deliveryDate);
    }
}
