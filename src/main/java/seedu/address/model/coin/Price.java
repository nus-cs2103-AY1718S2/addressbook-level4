package seedu.address.model.coin;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Coin's price in the address book.
 */
public class Price {

    private Amount currentPrice;
    private List<Amount> historicalPrices;
    private List<String> historicalTimeStamps;

    /**
     * Constructs a {@code Price}.
     */
    public Price() {
        this.currentPrice = new Amount("1.0");
        this.historicalPrices = new ArrayList<>();
        this.historicalTimeStamps = new ArrayList<>();
    }

    //@@author laichengyu
    /**
     * Constructs a {@code Price} with given value.
     */
    public Price(Price toCopy) {
        this.currentPrice = toCopy.currentPrice;
        this.historicalPrices = toCopy.historicalPrices;
        this.historicalTimeStamps = toCopy.historicalTimeStamps;
    }
    //@@author

    public Amount getCurrent() {
        return currentPrice;
    }

    public void setCurrent(Amount currentPrice) {
        this.currentPrice = currentPrice;
    }

    //@@author ewaldhew
    public void setHistorical(List<Amount> prices, List<String> timestamps) {
        historicalPrices = prices;
        historicalTimeStamps = timestamps;
    }

    public Price getChangeFrom(Price initial) {
        Price result = new Price();
        result.currentPrice = Amount.getDiff(currentPrice, initial.currentPrice);

        return result;
    }

    public List<Amount> getHistoricalPrices() {
        return historicalPrices;
    }

    public List<String> getHistoricalTimeStamps() {
        return historicalTimeStamps;
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
        return "USD " + this.currentPrice.toString();
    }

}
