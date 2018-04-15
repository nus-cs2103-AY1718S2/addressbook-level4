//@@author amad-person
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Order's quantity in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity {
    public static final String MESSAGE_QUANTITY_CONSTRAINTS =
            "Quantity should only contain numeric characters, and it should not be blank.\n"
                    + "Maximum value allowed for quantity is 1000000.";

    // Only positive integers are allowed
    public static final String QUANTITY_VALIDATION_REGEX = "^[0-9]*[1-9][0-9]*$";
    public static final int MAX_PERMISSIBLE_QUANTITY = 1000000;

    private final String quantity;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_QUANTITY_CONSTRAINTS);
        this.quantity = quantity;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidQuantity(String test) {
        requireNonNull(test);

        if (!test.matches(QUANTITY_VALIDATION_REGEX) || Integer.valueOf(test) > MAX_PERMISSIBLE_QUANTITY) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return quantity;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && this.quantity.equals(((Quantity) other).quantity)); // state check
    }

    @Override
    public int hashCode() {
        return quantity.hashCode();
    }
}

