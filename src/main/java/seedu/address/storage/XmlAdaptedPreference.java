package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Preference.
 */
public class XmlAdaptedPreference {
    @XmlValue
    private String preferenceName;

    /**
     * Constructs an XmlAdaptedPreference.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPreference() {}

    /**
     * Constructs a {@code XmlAdaptedPreference} with the given {@code prefName}.
     */
    public XmlAdaptedPreference(String prefName) {
        this.preferenceName = prefName;
    }

    /**
     * Converts a given Preference into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPreference(Preference source) {
        preferenceName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted preference object into the model's Preference object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted preference
     */
    public Preference toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(preferenceName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Preference(preferenceName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedPreference)) {
            return false;
        }
        return preferenceName.equals(((XmlAdaptedPreference) other).preferenceName);
    }
}
