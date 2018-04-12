package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Coin.
 */
public class XmlAdaptedCoin {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Coin's %s field is missing!";

    @XmlElement(required = true)
    private String code;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement(required = true)
    private String totalAmountSold;

    @XmlElement(required = true)
    private String totalAmountBought;

    @XmlElement(required = true)
    private String totalDollarsSold;

    @XmlElement(required = true)
    private String totalDollarsBought;

    /**
     * Constructs an XmlAdaptedCoin.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCoin() {}

    /**
     * Constructs an {@code XmlAdaptedCoin} with the given coin details.
     */
    public XmlAdaptedCoin(String code, List<XmlAdaptedTag> tagged) {
        this.code = code;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.totalAmountBought = "0.0";
        this.totalAmountSold = "0.0";
        this.totalDollarsBought = "0.0";
        this.totalDollarsSold = "0.0";
    }

    /**
     * Converts a given Coin into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCoin
     */
    public XmlAdaptedCoin(Coin source) {
        code = source.getCode().fullName;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        totalAmountBought = source.getTotalAmountBought().getValue();
        totalAmountSold = source.getTotalAmountSold().getValue();
        totalDollarsBought = source.getTotalDollarsBought().getValue();
        totalDollarsSold = source.getTotalDollarsSold().getValue();
    }

    /**
     * Converts this jaxb-friendly adapted coin object into the model's Coin object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted coin
     */
    public Coin toModelType() throws IllegalValueException {
        final List<Tag> coinTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            coinTags.add(tag.toModelType());
        }

        if (this.code == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName()));
        }
        if (!Code.isValidName(this.code)) {
            throw new IllegalValueException(Code.MESSAGE_NAME_CONSTRAINTS);
        }
        final Code code = new Code(this.code);

        final Set<Tag> tags = new HashSet<>(coinTags);
        if (CollectionUtil.isAnyNull(totalAmountBought, totalAmountSold, totalDollarsBought, totalDollarsSold)) {
            return new Coin(code, tags);
        } else {
            return new Coin(code, tags, totalAmountBought, totalAmountSold, totalDollarsBought, totalDollarsSold);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCoin)) {
            return false;
        }

        XmlAdaptedCoin otherCoin = (XmlAdaptedCoin) other;
        return Objects.equals(code, otherCoin.code)
                && tagged.equals(otherCoin.tagged);
    }
}
