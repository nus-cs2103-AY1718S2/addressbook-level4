package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrder(String)}
 */
public class Order {

    public static final String MESSAGE_ORDER_CONSTRAINTS =
            "Invalid order";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ORDER_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullOrder;

    /**
     * Constructs a {@code Order}.
     *
     * @param order A valid order.
     */
    public Order(String order) {
        requireNonNull(order);
        checkArgument(isValidOrder(order), MESSAGE_ORDER_CONSTRAINTS);
        this.fullOrder = order;
    }

    /**
     * Returns true if a given string is a valid person order.
     */
    public static boolean isValidOrder(String test) {
        return test.matches(ORDER_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullOrder;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Order // instanceof handles nulls
                && this.fullOrder.equals(((Order) other).fullOrder)); // state check
    }

    @Override
    public int hashCode() {
        return fullOrder.hashCode();
    }

}
