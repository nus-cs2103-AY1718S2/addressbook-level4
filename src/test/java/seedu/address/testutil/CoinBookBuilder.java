package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CoinBook;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Coinbook objects.
 * Example usage: <br>
 *     {@code CoinBook ab = new CoinBookBuilder().withCoin("John", "Doe").withTag("Friend").build();}
 */
public class CoinBookBuilder {

    private CoinBook coinBook;

    public CoinBookBuilder() {
        coinBook = new CoinBook();
    }

    public CoinBookBuilder(CoinBook coinBook) {
        this.coinBook = coinBook;
    }

    /**
     * Adds a new {@code Coin} to the {@code CoinBook} that we are building.
     */
    public CoinBookBuilder withCoin(Coin coin) {
        try {
            coinBook.addCoin(coin);
        } catch (DuplicateCoinException dpe) {
            throw new IllegalArgumentException("coin is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code CoinBook} that we are building.
     */
    public CoinBookBuilder withTag(String tagName) {
        try {
            coinBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public CoinBook build() {
        return coinBook;
    }
}
