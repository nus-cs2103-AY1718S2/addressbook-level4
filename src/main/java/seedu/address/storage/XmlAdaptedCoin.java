package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
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
        return new Coin(code, tags);
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
