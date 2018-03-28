package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CoinBook;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CoinBook ab = new AddressBookBuilder().withCoin("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private CoinBook addressBook;

    public AddressBookBuilder() {
        addressBook = new CoinBook();
    }

    public AddressBookBuilder(CoinBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Coin} to the {@code CoinBook} that we are building.
     */
    public AddressBookBuilder withCoin(Coin coin) {
        try {
            addressBook.addCoin(coin);
        } catch (DuplicateCoinException dpe) {
            throw new IllegalArgumentException("coin is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code CoinBook} that we are building.
     */
    public AddressBookBuilder withTag(String tagName) {
        try {
            addressBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public CoinBook build() {
        return addressBook;
    }
}
