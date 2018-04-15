//@@author amad-person
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;

/**
 * JAXB-friendly version of an Order.
 */
public class XmlAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    @XmlElement
    private String orderInformation;
    @XmlElement
    private String orderStatus;
    @XmlElement
    private String price;
    @XmlElement
    private String quantity;
    @XmlElement
    private String deliveryDate;

    /**
     * Constructs an XmlAdaptedOrder.
     */
    public XmlAdaptedOrder() {}

    /**
     * Constructs an {@code XmlAdaptedOrder} with the given order details.
     */
    public XmlAdaptedOrder(String orderInformation, String orderStatus, String price,
                           String quantity, String deliveryDate) {
        this.orderInformation = orderInformation;
        this.orderStatus = orderStatus;
        this.price = price;
        this.quantity = quantity;
        this.deliveryDate = deliveryDate;
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOrder
     */
    public XmlAdaptedOrder(Order source) {
        orderInformation = source.getOrderInformation().toString();
        orderStatus = source.getOrderStatus().getOrderStatusValue();
        price = source.getPrice().toString();
        quantity = source.getQuantity().toString();
        deliveryDate = source.getDeliveryDate().toString();
    }

    /**
     * Converts the jaxb-friendly adapted order object into the model's Order object.
     *
     * @throws IllegalValueException if any data constraints are violated in the adapted order's fields.
     */
    public Order toModelType() throws IllegalValueException {
        // convert order information
        if (this.orderInformation == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OrderInformation.class.getSimpleName()));
        }
        if (!OrderInformation.isValidOrderInformation(this.orderInformation)) {
            throw new IllegalValueException(OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);
        }
        final OrderInformation orderInformation = new OrderInformation(this.orderInformation);

        // convert order status
        if (this.orderStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OrderStatus.class.getSimpleName()));
        }
        if (!OrderStatus.isValidOrderStatus(this.orderStatus)) {
            throw new IllegalValueException(OrderStatus.MESSAGE_ORDER_STATUS_CONSTRAINTS);
        }
        final OrderStatus orderStatus = new OrderStatus(this.orderStatus);

        // convert price
        if (this.price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }

        if (!Price.isValidPrice(this.price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price price = new Price(this.price);

        // convert quantity
        if (this.quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }

        if (!Quantity.isValidQuantity(this.quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity quantity = new Quantity(this.quantity);

        // convert delivery date
        if (this.deliveryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DeliveryDate.class.getSimpleName()));
        }

        if (!DeliveryDate.isValidDeliveryDate(this.deliveryDate)) {
            throw new IllegalValueException(DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        }
        final DeliveryDate deliveryDate = new DeliveryDate(this.deliveryDate);

        return new Order(orderInformation, orderStatus, price, quantity, deliveryDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedOrder)) {
            return false;
        }

        XmlAdaptedOrder otherOrder = (XmlAdaptedOrder) other;
        return Objects.equals(orderInformation, otherOrder.orderInformation)
                && Objects.equals(orderStatus, otherOrder.orderStatus)
                && Objects.equals(price, otherOrder.price)
                && Objects.equals(quantity, otherOrder.quantity)
                && Objects.equals(deliveryDate, otherOrder.deliveryDate);
    }
}
