package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Price;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Coin objects.
 */
public class CoinBuilder {

    public static final String DEFAULT_NAME = "XTC";
    public static final String DEFAULT_TAGS = "favs";
    public static final Price DEFAULT_PRICE = new Price();
    public static final Amount DEFAULT_AMOUNT_SOLD = new Amount();
    public static final Amount DEFAULT_AMOUNT_BOUGHT = new Amount();

    private Code code;
    private Set<Tag> tags;
    private Price price;
    private Amount amountSold;
    private Amount amountBought;

    public CoinBuilder() {
        code = new Code(DEFAULT_NAME);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        price = DEFAULT_PRICE;
        amountSold = DEFAULT_AMOUNT_SOLD;
        amountBought = DEFAULT_AMOUNT_BOUGHT;
    }

    /**
     * Initializes the CoinBuilder with the data of {@code coinToCopy}.
     */
    public CoinBuilder(Coin coinToCopy) {
        code = coinToCopy.getCode();
        tags = new HashSet<>(coinToCopy.getTags());
        price = coinToCopy.getPrice();
        amountSold = coinToCopy.getTotalAmountSold();
        amountBought = coinToCopy.getTotalAmountBought();
    }

    /**
     * Sets the {@code Name} of the {@code Coin} that we are building.
     */
    public CoinBuilder withName(String name) {
        this.code = new Code(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Coin} that we are building.
     */
    public CoinBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    //@@author Eldon-Chung
    /**
     * Sets the {@code price} of the {@code Coin} that we are building.
     */
    public CoinBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    /**
     * Sets the {@code totalAmountSold} of the {@code Coin} that we are building.
     */
    public CoinBuilder withAmountSold(Amount amountSold) {
        this.amountSold = amountSold;
        return this;
    }

    /**
     * Sets the {@code totalAmountSold} of the {@code Coin} that we are building.
     */
    public CoinBuilder withAmountBought(Amount amountBought) {
        this.amountBought = amountBought;
        return this;
    }

    /**
     * @return a {@Coin} with the set code, tags, and amount sold and bought.
     */
    public Coin build() {
        Coin coin = new Coin(code, tags);
        coin = new Coin(coin, price);
        coin.addTotalAmountBought(amountBought);
        coin.addTotalAmountSold(amountSold);
        return coin;
    }
    //@@author

}
