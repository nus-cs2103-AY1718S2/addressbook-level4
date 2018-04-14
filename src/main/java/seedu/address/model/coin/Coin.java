package seedu.address.model.coin;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Coin in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Coin {

    private final Code code;

    private final Amount totalAmountSold;
    private final Amount totalAmountBought;
    private final Amount totalDollarsSold;
    private final Amount totalDollarsBought;
    private final Price price;

    private final UniqueTagList tags;

    private Coin prevState;

    /**
     * Every field must be present and not null.
     */
    public Coin(Code code, Set<Tag> tags) {
        requireAllNonNull(code, tags);
        this.code = code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.price = new Price();
        this.totalAmountSold = new Amount();
        this.totalAmountBought = new Amount();
        this.totalDollarsSold = new Amount();
        this.totalDollarsBought = new Amount();
    }

    /**
     * Every field must be present and not null.
     */
    public Coin(Code code, Set<Tag> tags, String aBought, String aSold, String dBought, String dSold) {
        requireAllNonNull(code, tags, aBought, aSold, dBought, dSold);
        this.code = code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.price = new Price();
        this.totalAmountSold = new Amount(aSold);
        this.totalAmountBought = new Amount(aBought);
        this.totalDollarsSold = new Amount(dSold);
        this.totalDollarsBought = new Amount(dBought);
    }

    /**
     * Every field must be present and not null.
     * Only used internally to generate diff coin
     */
    private Coin(Code code, Set<Tag> tags, Price price, Amount aBought, Amount aSold, Amount dBought, Amount dSold) {
        requireAllNonNull(code, tags, aBought, aSold, dBought, dSold);
        this.code = code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.price = price;
        this.totalAmountSold = aSold;
        this.totalAmountBought = aBought;
        this.totalDollarsSold = dSold;
        this.totalDollarsBought = dBought;
    }

    /**
     * Copy constructor for coins.
     * Sets previous state to copied coin.
     */
    public Coin(Coin toCopy) {
        requireAllNonNull(toCopy);
        this.code = toCopy.code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(toCopy.getTags());
        this.price = toCopy.price;
        this.totalAmountSold = new Amount(toCopy.getTotalAmountSold());
        this.totalAmountBought = new Amount(toCopy.getTotalAmountBought());
        this.totalDollarsSold = new Amount(toCopy.getTotalDollarsSold());
        this.totalDollarsBought = new Amount(toCopy.getTotalDollarsBought());
        prevState = toCopy;
    }

    //@@author laichengyu
    /**
     * Copy constructor with price update.
     * Sets previous state to copied coin.
     */
    public Coin(Coin toCopy, Price newPrice) {
        requireAllNonNull(toCopy);
        this.code = toCopy.code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(toCopy.getTags());
        this.price = new Price(newPrice);
        this.totalAmountSold = new Amount(toCopy.getTotalAmountSold());
        this.totalAmountBought = new Amount(toCopy.getTotalAmountBought());
        this.totalDollarsSold = new Amount(toCopy.getTotalDollarsSold());
        this.totalDollarsBought = new Amount(toCopy.getTotalDollarsBought());
        prevState = toCopy;
    }
    //@@author

    /**
     * Copy constructor with update.
     * Sets previous state to copied coin.
     */
    public Coin(Coin toCopy, Code code, Set<Tag> tags) {
        requireAllNonNull(toCopy, code, tags);
        this.code = code;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.price = toCopy.price;
        this.totalAmountSold = new Amount(toCopy.getTotalAmountSold());
        this.totalAmountBought = new Amount(toCopy.getTotalAmountBought());
        this.totalDollarsSold = new Amount(toCopy.getTotalDollarsSold());
        this.totalDollarsBought = new Amount(toCopy.getTotalDollarsBought());
        prevState = toCopy;
    }

    /**
     * Gets the previous state of this coin
     */
    public Coin getPrevState() {
        return prevState;
    }

    public Code getCode() {
        return code;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public Amount getTotalAmountSold() {
        return totalAmountSold;
    }

    /**
     * Updates the total amount sold of this coin in units held and return gained
     * @param addAmount
     */
    public void addTotalAmountSold(Amount addAmount) {
        // Bounds check, not to sell more than being held
        if (addAmount.compareTo(getCurrentAmountHeld()) > 0) {
            addAmount = getCurrentAmountHeld();
        }
        this.totalAmountSold.addValue(addAmount);
        this.totalDollarsSold.addValue(Amount.getMult(addAmount, price.getCurrent()));
    }

    public Amount getTotalAmountBought() {
        return totalAmountBought;
    }

    /**
     * Updates the total amount bought of this coin in units held and capital invested
     * @param addAmount
     */
    public void addTotalAmountBought(Amount addAmount) {
        this.totalAmountBought.addValue(addAmount);
        this.totalDollarsBought.addValue(Amount.getMult(addAmount, price.getCurrent()));
    }

    public Amount getCurrentAmountHeld() {
        return Amount.getDiff(totalAmountBought, totalAmountSold);
    }

    public Amount getTotalProfit() {
        return Amount.getDiff(totalDollarsSold, totalDollarsBought);
    }

    public Amount getDollarsWorth() {
        return Amount.getMult(price.getCurrent(), getCurrentAmountHeld());
    }

    public Amount getTotalDollarsSold() {
        return totalDollarsSold;
    }

    public Amount getTotalDollarsBought() {
        return totalDollarsBought;
    }

    //@@author ewaldhew
    /**
     * Gets the difference between two coins and makes a new coin record with that change.
     * @return (final minus initial) as a coin, where the final coin is this
     */
    public Coin getChangeFrom(Coin initialCoin) {
        assert(initialCoin.code.equals(this.code));

        return new Coin(initialCoin.code,
                initialCoin.getTags(),
                price.getChangeFrom(initialCoin.price),
                Amount.getDiff(this.totalAmountBought, initialCoin.totalAmountBought),
                Amount.getDiff(this.totalAmountSold, initialCoin.totalAmountSold),
                Amount.getDiff(this.totalDollarsBought, initialCoin.totalDollarsBought),
                Amount.getDiff(this.totalDollarsSold, initialCoin.totalDollarsSold));
    }

    public Coin getChangeFromPrev() {
        return getChangeFrom(prevState);
    }

    public Coin getChangeToPrev() {
        return prevState.getChangeFrom(this);
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Coin)) {
            return false;
        }

        Coin otherCoin = (Coin) other;
        return otherCoin.getCode().equals(this.getCode());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, tags, getCurrentAmountHeld(), price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCode())
                .append(" Amount: ")
                .append(getCurrentAmountHeld())
                .append(" Price: ")
                .append(getPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
