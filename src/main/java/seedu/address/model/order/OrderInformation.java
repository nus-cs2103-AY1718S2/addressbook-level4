//@@author amad-person
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Order's information in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrderInformation(String)}
 */
public class OrderInformation {
    public static final String MESSAGE_ORDER_INFORMATION_CONSTRAINTS =
            "Order information should only contain alphanumeric characters and spaces, and it should not be blank.";

    public static final String ORDER_INFORMATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String orderInformation;

    /**
     * Constructs {@code OrderInformation}.
     *
     * @param orderInfo Valid order information.
     */
    public OrderInformation(String orderInfo) {
        requireNonNull(orderInfo);
        checkArgument(isValidOrderInformation(orderInfo), MESSAGE_ORDER_INFORMATION_CONSTRAINTS);
        this.orderInformation = orderInfo;
    }

    /**
     * Returns true if a given string is valid order information.
     */
    public static boolean isValidOrderInformation(String test) {
        requireNonNull(test);
        return test.matches(ORDER_INFORMATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return orderInformation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderInformation // instanceof handles nulls
                && this.orderInformation.equals(((OrderInformation) other).orderInformation)); // state check
    }

    @Override
    public int hashCode() {
        return orderInformation.hashCode();
    }
}
