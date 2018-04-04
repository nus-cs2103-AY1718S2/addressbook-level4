//@@author amad-person
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents Order's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrderStatus(String)}
 */
public class OrderStatus {
    public static final String ORDER_STATUS_ONGOING = "ongoing";
    public static final String ORDER_STATUS_DONE = "done";

    public static final ArrayList<String> VALID_ORDER_STATUS = new ArrayList<>(
            Arrays.asList(ORDER_STATUS_ONGOING, ORDER_STATUS_DONE));

    public static final String MESSAGE_ORDER_STATUS_CONSTRAINTS = "Order status can only be "
            + ORDER_STATUS_ONGOING + " or " + ORDER_STATUS_DONE + ".";

    private String orderStatus;

    /**
     * Constructs a {@code OrderStatus}
     *
     * @param orderStatus a valid order status.
     */
    public OrderStatus(String orderStatus) {
        requireNonNull(orderStatus);
        checkArgument(isValidOrderStatus(orderStatus), MESSAGE_ORDER_STATUS_CONSTRAINTS);
        this.orderStatus = orderStatus;
    }

    /**
     * Returns true if a given string is a valid order status.
     */
    public static boolean isValidOrderStatus(String test) {
        requireNonNull(test);
        return VALID_ORDER_STATUS.contains(test);
    }

    /**
     * Returns the current order status.
     */
    public String getCurrentOrderStatus() {
        return this.orderStatus;
    }

    /**
     * Sets the current order status.
     */
    public void setCurrentOrderStatus(String newOrderStatus) {
        requireNonNull(newOrderStatus);
        if (isValidOrderStatus(newOrderStatus)) {
            this.orderStatus = newOrderStatus;
        }
    }

    @Override
    public String toString() {
        return orderStatus;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderStatus // instanceof handles nulls
                && this.getCurrentOrderStatus().equals(((OrderStatus) other).getCurrentOrderStatus())); // state check
    }

    @Override
    public int hashCode() {
        return orderStatus.hashCode();
    }
}
