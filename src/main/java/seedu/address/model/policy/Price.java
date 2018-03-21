package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Policy's monthly price.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(Double)}
 */
public class Price {
    public static final String PRICE_CONSTRAINTS =
            "Prices must not be negative.";

    public final Double price;

    public Price(Double price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), PRICE_CONSTRAINTS);
        this.price = price;
    }

    public static boolean isValidPrice(Double price) { return price >= 0; }

    @Override
    public String toString() { return price.toString(); }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Price
                && this.price == ((Price) other).price);
    }

}