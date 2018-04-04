//@@author amad-person
package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_ONGOING;

import java.util.Objects;

/**
 * Represents an Order in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    private final OrderInformation orderInformation;
    private final OrderStatus orderStatus;
    private final Price price;
    private final Quantity quantity;
    private final DeliveryDate deliveryDate;

    /**
     * Every field must be present and not null.
     */
    public Order(OrderInformation orderInformation, Price price, Quantity quantity, DeliveryDate deliveryDate) {
        requireAllNonNull(orderInformation, price, quantity, deliveryDate);
        this.orderInformation = orderInformation;
        this.orderStatus = new OrderStatus(ORDER_STATUS_ONGOING); // default value is ongoing
        this.price = price;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
    }

    public OrderInformation getOrderInformation() {
        return orderInformation;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public DeliveryDate getDeliveryDate() {
        return deliveryDate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        // TODO: orders can have the same information (just the person associated with them can be diff)
        Order otherOrder = (Order) other;
        return otherOrder.getOrderInformation().equals(this.getOrderInformation())
                && otherOrder.getOrderStatus().equals(this.getOrderStatus())
                && otherOrder.getPrice().equals(this.getPrice())
                && otherOrder.getQuantity().equals(this.getQuantity())
                && otherOrder.getDeliveryDate().equals(this.getDeliveryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderInformation, orderStatus, price, quantity, deliveryDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getOrderInformation())
                .append(" Status: ")
                .append(getOrderStatus())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Delivery Date: ")
                .append(getDeliveryDate());
        return builder.toString();
    }
}
