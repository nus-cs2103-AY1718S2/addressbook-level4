package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CoinBook;
import seedu.address.model.ReadOnlyCoinBook;

/**
 * An Immutable CoinBook that is serializable to XML format
 */
@XmlRootElement(name = "coinbook")
public class XmlSerializableCoinBook {

    @XmlElement
    private List<XmlAdaptedCoin> coins;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCoinBook() {
        coins = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCoinBook(ReadOnlyCoinBook src) {
        this();
        coins.addAll(src.getCoinList().stream().map(XmlAdaptedCoin::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code CoinBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCoin} or {@code XmlAdaptedTag}.
     */
    public CoinBook toModelType() throws IllegalValueException {
        CoinBook addressBook = new CoinBook();
        for (XmlAdaptedTag t : tags) {
            addressBook.addTag(t.toModelType());
        }
        for (XmlAdaptedCoin p : coins) {
            addressBook.addCoin(p.toModelType());
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCoinBook)) {
            return false;
        }

        XmlSerializableCoinBook otherAb = (XmlSerializableCoinBook) other;
        return coins.equals(otherAb.coins) && tags.equals(otherAb.tags);
    }
}
