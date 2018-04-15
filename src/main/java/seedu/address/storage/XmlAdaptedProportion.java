// @@author anh2111
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.report.Proportion;

/**
 * JAXB-friendly adapted version of the Proportion.
 */
public class XmlAdaptedProportion {

    @XmlElement
    private String proportionName;
    @XmlElement
    private String value;
    @XmlElement
    private String totalPersonsInProportion;

    /**
     * Constructs an XmlAdaptedProportion.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedProportion() {}

    /**
     * Constructs a {@code XmlAdaptedProportion} with the given {@code proportionName}.
     */
    public XmlAdaptedProportion(String proportionName, int value, int totalPersons) {
        this.proportionName = proportionName;
        this.value = Integer.toString(value);
        this.totalPersonsInProportion = Integer.toString(totalPersons);
    }

    /**
     * Converts a given Proportion into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedProportion(Proportion source) {
        proportionName = source.tagName;
        value = Integer.toString(source.value);
        totalPersonsInProportion = Integer.toString(source.totalPersons);
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Proportion toModelType() throws IllegalValueException {
        return new Proportion(proportionName, Integer.parseInt(value), Integer.parseInt(totalPersonsInProportion));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedProportion)) {
            return false;
        }
        return proportionName.equals(((XmlAdaptedProportion) other).proportionName)
            && value.equals(((XmlAdaptedProportion) other).value)
            && totalPersonsInProportion.equals(((XmlAdaptedProportion) other).totalPersonsInProportion);
    }
}
// @@author
