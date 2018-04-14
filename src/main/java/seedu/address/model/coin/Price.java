package seedu.address.model.coin;

/**
 * Represents a Coin's price in the address book.
 */
public class Price {

    private Amount currentPrice;

    /**
     * Constructs a {@code Price}.
     */
    public Price() {
        this.currentPrice = new Amount("1.0");
    }

    //@@author laichengyu
    /**
     * Constructs a {@code Price} with given value.
     */
    public Price(Price toCopy) {
        this.currentPrice = toCopy.currentPrice;
    }
    //@@author

    public Amount getCurrent() {
        return currentPrice;
    }

    public void setCurrent(Amount currentPrice) {
        this.currentPrice = currentPrice;
    }

    //@@author ewaldhew
    public Price getChangeFrom(Price initial) {
        Price result = new Price();
        result.currentPrice = Amount.getDiff(currentPrice, initial.currentPrice);

        return result;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && this.currentPrice.equals(((Price) other).currentPrice)); // state check
    }

    @Override
    public String toString() {
        return this.currentPrice.toString();
    }
}
