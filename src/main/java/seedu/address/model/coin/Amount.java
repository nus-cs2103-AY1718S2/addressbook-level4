//@@author ewaldhew
package seedu.address.model.coin;

import java.math.BigDecimal;
import java.math.RoundingMode;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents the amount of the coin held in the address book.
 */
public class Amount implements Comparable<Amount> {

    private static final String[] MAGNITUDE_CHAR = { "", "K", "M", "B", "T", "Q", "P", "S", "H" };
    private static final String MESSAGE_TOO_BIG = "This value can't be displayed as it is too big, "
            + "total amount far exceeds circulating supply!\n"
            + "Unfortunately, CoinBook cannot yet handle unorthodox usage [Coming in v2.0]";
    private static final String DISPLAY_TOO_BIG = "Err (see log)";

    private BigDecimal value;

    /**
     * Constructs an {@code Amount}.
     */
    public Amount() {
        this.value = new BigDecimal(0);
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(Amount amount) {
        this.value = amount.value;
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(String value) {
        this.value = new BigDecimal(value).setScale(8, RoundingMode.UP);
    }

    /**
     * Constructs an {@code Amount} with given value.
     * For internal use only.
     */
    private Amount(BigDecimal value) {
        this.value = value;
    }


    /**
     * Adds two amounts together and returns a new object.
     * @return the sum of the two arguments
     */
    public static Amount getSum(Amount first, Amount second) {
        return new Amount(first.value.add(second.value));
    }

    /**
     * Subtracts second from first and returns a new object.
     * @return the difference of the two arguments
     */
    public static Amount getDiff(Amount first, Amount second) {
        return new Amount(first.value.subtract(second.value));
    }

    /**
     * Multiplus two amounts together and returns a new object.
     * @return the product of the two arguments
     */
    public static Amount getMult(Amount first, Amount second) {
        return new Amount(first.value.multiply(second.value));
    }

    /**
     * Adds addAmount to the current value.
     *
     * @param addAmount amount to be added.
     */
    public void addValue(Amount addAmount) {
        value = value.add(addAmount.value);
    }

    /**
     * Subtracts subtractAmount to the current value.
     *
     * @param subtractAmount amount to be subtracted.
     */
    public void subtractValue(Amount subtractAmount) {
        value = value.subtract(subtractAmount.value);
    }

    /**
     * Gets the string representation of the full value.
     * Use {@code toString} instead for display purposes.
     * @see Amount#toString
     */
    public String getValue() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && this.value.compareTo(((Amount) other).value) == 0); // state check
    }

    /**
     * Gets the display string of the value. Displays up to 4 d.p.
     * @return
     */
    @Override
    public String toString() {
        // Calculate the magnitude, which is the nearest lower multiple of three, of digits
        final int magnitude = value.compareTo(BigDecimal.ZERO) == 0
                              ? 0
                              : (value.precision() - value.scale()) / 3;

        if (magnitude < MAGNITUDE_CHAR.length) {
            // Shift the decimal point to keep the string printed at 7 digits max
            return value.movePointLeft(magnitude * 3)
                    .setScale(4, RoundingMode.UP)
                    .toPlainString()
                    + MAGNITUDE_CHAR[magnitude];
        } else {
            // We don't handle absurd cases specially for now
            LogsCenter.getLogger(Amount.class).warning(MESSAGE_TOO_BIG);
            return DISPLAY_TOO_BIG;
        }

    }

    @Override
    public int compareTo(Amount other) {
        return value.compareTo(other.value);
    }
}
