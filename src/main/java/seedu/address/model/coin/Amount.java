package seedu.address.model.coin;

/**
 * Represents the amount of the coin held in the address book.
 */
public class Amount {

    private Double value;

    /**
     * Constructs an {@code Amount}.
     */
    public Amount() {
        this.value = 0.0;
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(Amount amount) {
        this.value = amount.getValue();
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(double value) {
        this.value = value;
    }

    /**
     * Adds addAmount to the current value.
     *
     * @param addAmount amount to be added.
     */
    public void addValue(Double addAmount) {
        this.value += addAmount;
    }

    /**
     * Subtracts subtractAmount to the current value.
     *
     * @param subtractAmount amount to be subtracted.
     */
    public void subtractValue(Double subtractAmount) {
        this.value -= subtractAmount;
    }

    public Double getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && this.value.equals(((Amount) other).value)); // state check
    }

    public String toString() {
        return this.value.toString();
    }
}
