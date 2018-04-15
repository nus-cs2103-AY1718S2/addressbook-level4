//@@author amad-person
package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
        import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Order's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {
    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Price should only contain numeric characters, one decimal "
                    + "and at most two numeric characters after the decimal, "
                    + "and it should not be blank.\n"
                    + "Maximum value allowed for price is 1000000.00";

    public static final String PRICE_VALIDATION_REGEX = "[0-9]+([.][0-9]{1,2})?";
    public static final double MAX_PERMISSIBLE_PRICE = 1000000.00;

    private final String price;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        this.price = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        requireNonNull(test);

        if (!test.matches(PRICE_VALIDATION_REGEX) || Double.valueOf(test) > MAX_PERMISSIBLE_PRICE) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && this.price.equals(((Price) other).price)); // state check
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
