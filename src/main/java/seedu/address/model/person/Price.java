package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {
    public static final String MESSAGE_PRICE_CONSTRAINTS = "Price should be a positive integer\n";

    public final String value;


    public Price(String price) {
        requireNonNull(price);
        if (!price.equals("")) {
            checkArgument(isValidPrice(price), MESSAGE_PRICE_CONSTRAINTS);
        }
        this.value = price;
    }

    /**
     * Returns if a given string is a valid status description.
     */
    public static boolean isValidPrice(String test) {
        return test.equals("") || (Integer.parseInt(test) > 0);
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object

                || (other instanceof Price // instanceof handles nulls
                && this.value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
