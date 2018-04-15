package seedu.address.storage;

import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pair.PairHash;

/**
 * JAXB-friendly adapted version of the PairHash.
 */
public class XmlAdaptedPairHash {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    @XmlValue
    private String pairHashValue;

    /**
     * Constructs an XmlAdaptedPairHash.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPairHash() {}

    /**
     * Constructs a {@code XmlAdaptedPairHash} with the given {@code pairHashValue}.
     */
    public XmlAdaptedPairHash(String pairHashValue) {
        this.pairHashValue = pairHashValue;
    }

    /**
     * Converts a given PairHash into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPairHash(PairHash source) {
        pairHashValue = Integer.toString(source.getValue());
    }

    /**
     * Converts this jaxb-friendly adapted pairHash object into the model's PairHash object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public PairHash toModelType() throws IllegalValueException {
        if (!PairHash.isValidPairHashValue(pairHashValue)) {
            throw new IllegalValueException(PairHash.MESSAGE_PAIRHASH_CONSTRAINTS);
        }
        return new PairHash(Integer.parseInt(pairHashValue));
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPairHash)) {
            return false;
        }

        return pairHashValue.equals(((XmlAdaptedPairHash) other).pairHashValue);
    }
}
